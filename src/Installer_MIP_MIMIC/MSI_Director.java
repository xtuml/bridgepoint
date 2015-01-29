import java.util.HashMap;
import java.io.*;

// NOTE: The below 2 imports should be the ONLY imports allowed from com.mentor.install.*
//       You should ONLY need "toolkitDialogsEngine.jar" under TK_Director/{*DirectorName*}/ directory.
import com.mentor.install.toolkit.MSITK_DirectorProxy;
import com.mentor.install.toolkit.customdialogs.DirectorHelper;


public class MSI_Director {
    private String prodCnt = "0";
    private int mglsLoc;
    private String releaseName = null;
    private String selectedProduct = null;
    private String targetLocation = null;
    private boolean mglsSel = true;
    private String eclipseLocation = "";

    private String eclipseVersion = "3.7.2";
    private String productVersion = "4.2.1";
    private String productName = "BridgePoint";

    public MSI_Director(String pathToSelf) {
        initWizard(pathToSelf);
        runWizard();
        installProduct();
    }

    private void initWizard(String pathToSelf) {
        if (!MSITK_DirectorProxy.getInterface().isVersionSupported(2, 6)) {
            System.out.println("An incompatable version of the Mentor Graphics Installer exists on this system.");
            System.out.println("Please re-execute this installer with the \"-force\" command line argument.");
            System.out.flush();
            System.exit(1);
        }

        MSITK_DirectorProxy.getInterface26().createInstallSet(pathToSelf);
        MSITK_DirectorProxy.getInterface26().installSetParseMIBFile(pathToSelf);

        int relSize = MSITK_DirectorProxy.getInterface26().installSetGetReleaseCount();
        if (relSize < 1) {
            System.out.println("Error reading install media");
            System.out.flush();
            System.exit(1);
        }

        MSITK_DirectorProxy.getInterface26().installSetGetRelease(0);

        int prodCount = MSITK_DirectorProxy.getInterface26().installSetGetProductCountForRelease();
        if (prodCount < 1) {
            System.out.println("Error reading product information");
            System.out.flush();
            System.exit(1);
        }
        releaseName = MSITK_DirectorProxy.getInterface26().installSetGetReleaseName(); //rls.getRlsName();
        prodCnt = Integer.toString(prodCount);

        DirectorHelper.init(true);
        DirectorHelper.clearProperties();
        DirectorHelper.setProperties(new HashMap() {
            {
                put(DirectorHelper.PROPERTY_TITLE, "BridgePoint Install");
            }
        });

        DirectorHelper.setProperty(DirectorHelper.PROPERTY_WIZARD, "true");
        DirectorHelper.setProperty(DirectorHelper.PROPERTY_UNRESIZABLE, "true");
        DirectorHelper.setProperty(DirectorHelper.PROPERTY_FORCE_PAGE_REPACKING, "false");
        DirectorHelper.setProperty(DirectorHelper.PROPERTY_PREFERRED_WIDTH, "740");
        DirectorHelper.setProperty(DirectorHelper.PROPERTY_PREFERRED_HEIGHT, "580");
    }

    private void runWizard() {
        int wizardStep = 0;
        while (wizardStep < 5) {
            switch (wizardStep) {
            case 0: //show EULA
                if (showEULA() != DirectorHelper.BTN_AGREE) {
                    System.exit(0);
                }
                wizardStep++;
                break;
            case 1: //show product selection
                wizardStep = nextWizardStep(wizardStep, showProducts());
                break;
            case 2: //ask for target location
                wizardStep = nextWizardStep(wizardStep, showTargetSelection());
                if (eclipseLocation.equals("")) {
                    // If the user is installing BP with Eclipse, note that in our eclipse location variable
                    eclipseLocation = targetLocation + "/eclipse";
                }
                // Write a file to the "extras/" dir in the target.  The file will be used in the post-install to 
                // determine where the link and launcher files should be moved (eclipse target).
                try {
                    String strDirectoy = targetLocation + "/extras";
                    boolean success = (new File(strDirectoy)).mkdir();
                    FileWriter fstream = new FileWriter(targetLocation + "/extras/eclipsedir.txt");
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write(eclipseLocation + ";");
                    out.close();
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
                break;
            case 3:
                // Write a file to the "extras/" dir in the target.  The file will be used in the post-install to 
                // determine whether or not to display the release notes.
                if (showReleaseNotesQuery() == DirectorHelper.BTN_YES) {
                    try {
                        String strDirectoy = targetLocation + "/extras";
                        boolean success = (new File(strDirectoy)).mkdir();
                        FileWriter fstream = new FileWriter(targetLocation + "/extras/rnflag.txt");
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write("Yes");
                        out.close();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }
                wizardStep++;
                break;
            case 4:
                // Write a file to the "extras/" dir in the target.  The file 
                // will be used in the post-install to determine whether or not 
                // to create a desktop shortcut.
                if (showCreateShortcutQuery() == DirectorHelper.BTN_YES) {
                    try {
                        String strDirectoy = targetLocation + "/extras";
                        boolean success = (new File(strDirectoy)).mkdir();
                        FileWriter fstream = new FileWriter(targetLocation + "/extras/csflag.txt");
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write("Yes");
                        out.close();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }
                wizardStep++;
                break;
            default: //should not get here!
                System.out.println("Wizard error...");
                System.exit(1);
            }
        }
    }

    private static int nextWizardStep(int wizardStep, int dismissBtn) {
        switch (dismissBtn) {
        case DirectorHelper.BTN_BACK:
            return wizardStep - 1;
        case DirectorHelper.BTN_NO:
            return wizardStep;
        case DirectorHelper.BTN_CANCEL:
            System.exit(0);
        default:
            return wizardStep + 1;
        }
    }

    private int showEULA() {
        /*int val = DirectorHelper.showDialog("basic_templates/EULADialog.html",
                new HashMap() {
                    {
                        //put(DirectorHelper.PROPERTY_HEADER, "Mentor Graphics");
                    }
                });
        return val;*/
        return DirectorHelper.BTN_AGREE;
    }

    private int showProducts() {
        int result = DirectorHelper.showDialog(
                "BridgePoint/bp_prodSelDialog.html", new HashMap() {
                    {
                        put(DirectorHelper.PROPERTY_TITLE, String.format("%s %s Installation", productName, productVersion));
                        put("messagePrompt", "Please select the product you would like to install:");
                        // show the BridgePoint logo in the dialog
                        put("messageIcon", "BridgePoint/bp.png");
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_NEXT | DirectorHelper.BTN_BACK | DirectorHelper.BTN_CANCEL));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_NEXT));

                        put("radiogroup.count", prodCnt);

                        int prodCount = MSITK_DirectorProxy.getInterface26().installSetGetProductCountForRelease();
                        int c = 0;
                        for (int i = prodCount-1; i >= 0; i--) {
                            MSITK_DirectorProxy.getInterface26().installSetGetProduct(i);
                            String prodName = MSITK_DirectorProxy.getInterface26().installSetGetProductName();

                            if (prodName.indexOf("Eclipse") > 0) {
                                put(new String("radiogroup.#" + (++c) + ".title"), String.format("<font face=arial,helvetica><font size=+1 color=#118211>%s</font> with <font size=+1 color=#524f80>Eclipse</font></font>", productName));
                                put(new String("radiogroup.#" + c + ".instructions"), String.format("<em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Eclipse version %s software is included as a convenience for %s %s customers and is licensed by the Eclipse Foundation under their EPL License.</em><br><br><br><br><br>", eclipseVersion, productName, productVersion));
                            } else {
                                put(new String("radiogroup.#" + (++c) + ".title"), String.format("<font face=arial,helvetica size=+1 color=#118211>%s</font>", productName));
                                put(new String("radiogroup.#" + c + ".instructions"), String.format("<em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select this option to use %s with an existing Eclipse installation.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</em>", productName));
                            }
                        }
                        DirectorHelper.setProperty("selection", "0"); // This will set the BridgePoint_With_Eclipse as the default selection.
                    }
                });

        String sel = DirectorHelper.getProperty("selection");
        int selLoc = Integer.parseInt(sel);

        // Ideally, this is how we would get the product, but since we inverted the display list in the for loop earlier (starting  
        // at the end of the products and working backwards, the location doesn't correspond correctly to the product
        // list.  Therefore, we just hardcode in the following if-block.
        //MSITK_DirectorProxy.getInterface26().installSetGetProduct(selLoc);
        //selectedProduct = MSITK_DirectorProxy.getInterface26().installSetGetProductName();

        if (selLoc == 1) {
            selectedProduct = "BridgePoint";
            int rtn = showExistingEclipseSelection();
            if (rtn == DirectorHelper.BTN_CANCEL) {
                System.exit(0);
            } else if (rtn == DirectorHelper.BTN_BACK) {
                result = showProducts();
            }
            // If the result (rtn) is BTN_NEXT, just fall through and keep going
        } else {
            selectedProduct = "BridgePoint_With_Eclipse";
        }

        return result;
    }

    private int showTargetSelection() {
        eclipseLocation = "";
        int rtn = DirectorHelper.showDialog("basic_templates/inputDialog.html",
                new HashMap() {
                    {
                        //put(DirectorHelper.PROPERTY_HEADER, "Target Location");
                        put(DirectorHelper.PROPERTY_TITLE, String.format("%s %s Installation", productName, productVersion));
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_NEXT | DirectorHelper.BTN_BACK | DirectorHelper.BTN_CANCEL));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_NEXT));
                        put("messageIcon", "Question.gif");
                        put("messagePrompt", String.format("Please specify the target directory for the installation.<br><br>This is the top-level folder where the application files will be stored.<br><br>For example: C:\\MentorGraphics\\%s", productName));
                        put("fileBrowser:visible", "true");
                        put("inputText", String.format("C:\\MentorGraphics\\%s", productName));
                    }
                });

        targetLocation = DirectorHelper.getProperty("inputText");
        if (rtn == DirectorHelper.BTN_NEXT) {
            if (targetLocation == null || targetLocation.length() < 1 || targetLocation.indexOf(" ") != -1) {
                showTargetError();
                rtn = showTargetSelection();
            } else { 
                File tar = new File(targetLocation);
                if (!tar.exists()) {
                    tar.mkdirs();
                    return DirectorHelper.BTN_NEXT;
                } else {
                    int rtn2 = showOverwriteExistingQuery();
                    if (rtn2 == DirectorHelper.BTN_NO) {
                        rtn = showTargetSelection();
                    } else {
                        deleteDirectory(tar);
                        tar.mkdirs();
                        rtn = DirectorHelper.BTN_NEXT;
                    }
                }
            }
        }
        return rtn;
    }

    private void showTargetError() {
        DirectorHelper.showDialog("basic_templates/outputDialog.html",
                new HashMap() {
                    {
                        put(DirectorHelper.PROPERTY_HEADER, "Target Error");
                        put(DirectorHelper.PROPERTY_TITLE, "Invalid Target Specification");
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_BACK | DirectorHelper.BTN_CANCEL));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_BACK));
                        put("messagePrompt", "Invalid target specified.<br> <br>Please provide a valid target location (that does not contain spaces) for the installation of your software.");
                    }
                });
    }

    private int showReleaseNotesQuery() {
        return DirectorHelper.BTN_YES;
      /* Re-enable this dialog, if we want to query whether to do this or not
                int rtn = DirectorHelper.showDialog("basic_templates/outputDialog.html",
                new HashMap() {
                    {
                        put(DirectorHelper.PROPERTY_HEADER, "Show Release Notes");
                        put(DirectorHelper.PROPERTY_TITLE, "Display Release Notes After Installation?");
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_YES | DirectorHelper.BTN_NO));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_YES));
                        put("messagePrompt", "Do you want to view the Release Notes after the installation is complete?");
                    }
                });
                return rtn;*/
    }
    
    private int showCreateShortcutQuery() {
        int rtn = DirectorHelper.showDialog("basic_templates/outputDialog.html",
        new HashMap() {
            {
                put(DirectorHelper.PROPERTY_TITLE, "Create Desktop Shortcut?");
                put(DirectorHelper.PROPERTY_HEADER, "Create Desktop Shortcut?");
                put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_YES | DirectorHelper.BTN_NO));
                put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_YES));
                put("messagePrompt", String.format("Do you want to create a desktop shortcut for %s?<br><br>If you answer \"Yes\", the existing desktop shortcut named \"%s\" will be overwritten.  Rename it now if you want to keep it.", productName, productName));
            }
        });
        return rtn;
    }

    private int showOverwriteExistingQuery() {
        int rtn = DirectorHelper.showDialog("basic_templates/outputDialog.html",
        new HashMap() {
            {
                put(DirectorHelper.PROPERTY_TITLE, String.format("Overwrite Existing %s Confirmation", productName));
                put(DirectorHelper.PROPERTY_HEADER, String.format("Overwrite Existing %s?", productName));
                put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_YES | DirectorHelper.BTN_NO));
                put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_YES));
                put("messagePrompt", String.format("Are you sure you want to overwrite the existing %s application directory?<br><br>If you answer \"Yes\", the existing %s folder will be removed, including any customizations (additional link files, INI changes, etc) you have stored there.<br><br>If you do not want to do this, answer \"No\" to go back and choose a different install location.", productName, productName));
            }
        });
        return rtn;
    }

    private void showInstallComplete() {
        DirectorHelper.showDialog("basic_templates/outputDialog.html",
                new HashMap() {
                    {
                        put(DirectorHelper.PROPERTY_HEADER, "Installation Complete");
                        put(DirectorHelper.PROPERTY_TITLE, "Installation Has Completed");
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_OK));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_OK));
                        put("messagePrompt", String.format("The %s installation has completed.<br><br>You can now use the desktop shortcut or %s/Launcher.bat to start the application.", productName, eclipseLocation));
                    }
                });
    }

    private int showExistingEclipseSelection() {
        eclipseLocation = "";
        boolean error = false;
        int rtn = DirectorHelper.showDialog("basic_templates/inputDialog.html",
                new HashMap() {
                    {
                        //put(DirectorHelper.PROPERTY_HEADER, "Mentor Graphics");
                        put(DirectorHelper.PROPERTY_TITLE, String.format("%s %s Installation", productName, productVersion));
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_NEXT | DirectorHelper.BTN_BACK | DirectorHelper.BTN_CANCEL));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_NEXT));
                        put("messageIcon", "Question.gif");
                        put("messagePrompt", String.format("Please specify the existing Eclipse %s or later installation to use. For example: C:\\eclipse<br><br>Additional non-eclipse application files will be installed elsewhere.  You will select the location to install those files later.", eclipseVersion) + 
                                "<br><br><em>Please note that BridgePoint relies on additional eclipse functionality (EMF, GMF, etc). You should only choose this option if you are confident your existing eclipse is sufficently enabled or you are comfortable enough with eclipse configurations to work through issues with the configuration.</em>");
                        put("fileBrowser:visible", "true");
                        put("inputText", "C:\\eclipse");

                    }
                });

        String eclipseTargetLocation = DirectorHelper.getProperty("inputText");
        if (!error && (rtn == DirectorHelper.BTN_NEXT)) {
            if (eclipseTargetLocation == null || eclipseTargetLocation.length() < 1) {
                error = true;
            } else {
                File tar = new File(eclipseTargetLocation);
                if (!tar.exists()) {
                    error = true;
                }

                if (!error && (rtn == DirectorHelper.BTN_NEXT)) {
                    String ver = getEclipseVersion(eclipseTargetLocation);
                    float minVer = 3.4f;
                    try {
                        String eVer = ver.substring(0, 3);
                        float eVer_f = Float.valueOf(eVer.trim()).floatValue();
                        if (eVer_f < minVer) {
                            error = true;
                        }
                    } catch (IndexOutOfBoundsException iobe) {
                        System.out.println("Index out of bounds exception when trying to get base eclipse version.");
                        error = true;
                    } catch (NumberFormatException nfe) {
                        System.out.println("Number format exception converting eclipse version from string to float.");
                        error = true;
                    }
                }
            }
        }
        
        if (error) {
            rtn = handleBadEclipseTarget();
        } else {
            if (rtn == DirectorHelper.BTN_NEXT) {
                System.out.println("Using existing eclipse at: " + eclipseTargetLocation);
                eclipseLocation = eclipseTargetLocation;
            }
        }
        
        return rtn;
    }

    private int handleBadEclipseTarget() {
        int rtn = 0;
        int rtn2 = showEclipseTargetError();
        if (rtn2 == DirectorHelper.BTN_BACK) {
            rtn = showExistingEclipseSelection();
        } else {
            System.exit(0);
        }
        return rtn;
    }

    private int showEclipseTargetError() {
        int rtn = DirectorHelper.showDialog("basic_templates/outputDialog.html", 
                new HashMap() {
                    {
                        put(DirectorHelper.PROPERTY_HEADER, "Eclipse Target Error");
                        put(DirectorHelper.PROPERTY_TITLE, "Invalid Eclipse Target Specification");
                        put(DirectorHelper.PROPERTY_BTN_SET, Integer.toString(DirectorHelper.BTN_BACK | DirectorHelper.BTN_CANCEL));
                        put(DirectorHelper.PROPERTY_BTN_SET_FOCUS, Integer.toString(DirectorHelper.BTN_BACK));
                        put("messagePrompt", String.format("Invalid Eclipse target specified.<br> <br>Please provide a valid Eclipse %s or later target location to use.", eclipseVersion));
                    }
                });
        return rtn;
    }

    public String getEclipseVersion(String eclipsePath) {
        File defpath = new File(eclipsePath);
        File inputFile = new File(".");
        File tempfile = new File(".");
        String path = new String();
        String version = new String();

        path = defpath.getAbsolutePath();
        tempfile = new File(path + File.separator + "readme" + File.separator + "readme_eclipse.html");
        if (tempfile.isFile()) {
            inputFile = new File(tempfile.getAbsolutePath());
            String lineIn = new String();
            String releaseInfo = new String("<p>Release ");
            BufferedReader in = null;

            try {
                in = new BufferedReader(new FileReader(inputFile));
                lineIn = in.readLine();

                while (lineIn != null) {
                    if (lineIn.indexOf(releaseInfo) != -1) {
                        version = lineIn.substring(releaseInfo.length());
                        int loc = version.indexOf("<br>");
                        if (loc != -1) {
                            version = version.substring(0, loc);
                            System.out.println("Target Eclipse version is: " + version);
                        }
                        return version;
                    }
                    lineIn = in.readLine();
                }

            } catch (FileNotFoundException fnfe) {
                System.out.println("File " + inputFile + " not found.");
            } catch (IndexOutOfBoundsException ioobe) {
                System.out.println("IndexOutOfBoundsException caught.");
            } catch (IOException ioe) {
                System.out.println("IOException caught.");
            }

            return version;
        }

        return "";
    }

    private boolean deleteDirectory(File path) {
        if( path.exists() ) {
          File[] files = path.listFiles();
          for(int i=0; i<files.length; i++) {
             if(files[i].isDirectory()) {
               deleteDirectory(files[i]);
             }
             else {
               files[i].delete();
             }
          }
        }
        return( path.delete() );
      }

    private void installProduct() {
        String origSource = MSITK_DirectorProxy.getInterface26().installSetGetSource();
        String origVCO = MSITK_DirectorProxy.getInterface26().installSetGetVCO();
        MSITK_DirectorProxy.getInterface26().installSetGetProduct(mglsLoc);
        String mglsName = MSITK_DirectorProxy.getInterface26().installSetGetProductName();

        MSITK_DirectorProxy.getInterface26().createInstallSet(origSource,targetLocation, origVCO, false, false, true);
        MSITK_DirectorProxy.getInterface26().installSetAddRelease(releaseName);
        MSITK_DirectorProxy.getInterface26().installSetAddProduct(selectedProduct);

        DirectorHelper.dismiss();

        MSITK_DirectorProxy.getInterface26().createAndStartInstall();

        if (MSITK_DirectorProxy.getInterface26().isInstallComplete()) {
            // Install completed
            showInstallComplete();
        }
    }

    public static void main(String[] args) {
        boolean srcFound = false;
        String pathToSource = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareToIgnoreCase("-src") == 0) {
                pathToSource = args[++i];
                srcFound = true;
            }
        }

        if (srcFound) {
            new MSI_Director(pathToSource);
        } else {
            System.out.println("Error initializing installer");
            System.out.flush();
        }

        System.exit(0);
    }
}
