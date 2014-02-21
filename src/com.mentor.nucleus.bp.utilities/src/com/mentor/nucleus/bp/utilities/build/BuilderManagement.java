//========================================================================
//
// File: BuilderManagement.java
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//

package com.mentor.nucleus.bp.utilities.build;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mentor.nucleus.bp.core.CorePlugin;

public class BuilderManagement {

    private static int REPLACE = 0;
    private static int PREPEND = 1;
    private static int APPEND = 2;

    public static final String XML_KEY = "key"; //$NON-NLS-1$
    public static final String XML_VALUE = "value"; //$NON-NLS-1$
    private static final String ENV_ATTR_NAME = "org.eclipse.debug.core.environmentVariables"; //$NON-NLS-1$

    public static final String CUST_BUILDER_ID = "org.eclipse.ui.externaltools.ExternalToolBuilder"; //$NON-NLS-1$
    public static final String CDT_BUILDER_ID = "org.eclipse.cdt.managedbuilder.core.genmakebuilder"; //$NON-NLS-1$
    public static final String CDT_SCANNER_BUILDER_ID = "org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder"; //$NON-NLS-1$

    /**
     * @return 0-based position of the found builder in the array returned by
     *         IProjectDescription.getBuildSpec(). -1 if not found.
     */
    public static int hasBuilder(IProject project, String name) throws CoreException {
        int position = -1;

        if (project.isOpen()) {
            // Get project description and then the associated build commands
            IProjectDescription desc = project.getDescription();
            ICommand[] commands = desc.getBuildSpec();

            for (int i = 0; i < commands.length; ++i) {
                // Check for builder in enabled state
                if (commands[i].getBuilderName().equals(name)) {
                    position = i;
                    break;
                }
                // Check for builder in disabled state
                Map<?, ?> args = commands[i].getArguments();
                if (args != null) {
                    String value = (String) args.get("LaunchConfigHandle");
                    if (value != null && value.contains(name)) {
                        position = i;
                        break;
                    }
                }
            }
        }
        return position;
    }


    public static void makeBuilderLast(IProject project, final String builderId) {
        int position = -1;
        try {
            position = hasBuilder(project, builderId);
            if (position != -1) {
                IProjectDescription description = project.getDescription();
                ICommand[] commands = description.getBuildSpec();
                ICommand[] newCommands = new ICommand[commands.length];
                int curIndex = 0;
                int newIndex = 0;
                for (; curIndex < commands.length; ++curIndex) {
                    if (curIndex != position) {
                        newCommands[newIndex] = commands[curIndex];
                        newIndex++;
                    }
                }
                newCommands[newIndex] = commands[position];
                description.setBuildSpec(newCommands);
                project.setDescription(description, null);
            }
        } catch (CoreException e) {
            CorePlugin.logError("Error moving the builder \"" + builderId
                    + "\" to be last for the " + project.getName()
                    + " project.", e);
        }
    }
    
    public static int findBuilder(IProject project, final String builderNameRegex) {
        int position = -1;
        
        try {
            if (project.isOpen()) {
                // Get project description and then the associated build commands
                IProjectDescription desc = project.getDescription();
                ICommand[] commands = desc.getBuildSpec();

                for (int i = 0; i < commands.length; ++i) {
                    // Check for builder in enabled state
                    if (commands[i].getBuilderName().matches(builderNameRegex)) {
                        position = i;
                        break;
                    }
                    // Check for builder in disabled state
                    Map<?, ?> args = commands[i].getArguments();
                    if (args != null) {
                        String value = (String) args.get("LaunchConfigHandle");
                        if (value != null && value.matches(builderNameRegex)) {
                            position = i;
                            break;
                        }
                    }
                }
            }
        } catch (CoreException e) {
            CorePlugin.logError("Error running findBuilder for "
                    + " project.", e);
        }
        return position;
    }

    public static void findAndRemoveBuilder(IProject project, final String builderNameRegex) {
        int position = -1;
        position = findBuilder(project, builderNameRegex);
        removeBuilder(project, position);
        return;
    }

    public static int removeBuilder(IProject project, final String builderId) {
        int position = -1;
        try {
            position = hasBuilder(project, builderId);
            removeBuilder(project, position);
        } catch (CoreException e) {
            CorePlugin.logError("Error removing the builder \"" + builderId
                    + "\" from the " + project.getName() + " project.", e);
        }
        return position;
    }
    
    public static int removeBuilder(IProject project, int position) {
        try {
            if (position != -1) {
                IProjectDescription description = project.getDescription();
                ICommand[] commands = description.getBuildSpec();
                ICommand[] newCommands = new ICommand[commands.length - 1];
                int curIndex = 0;
                int newIndex = 0;
                for (; curIndex < commands.length; ++curIndex) {
                    if (curIndex != position) {
                        newCommands[newIndex] = commands[curIndex];
                        newIndex++;
                    }
                }
                description.setBuildSpec(newCommands);
                project.setDescription(description, null);
            }
        } catch (CoreException e) {
            CorePlugin.logError("Error removing the builder at position \"" + position
                    + "\" from the " + project.getName() + " project.", e);
        }
        return position;
    }

    public static String replaceBuilderInfo(String tgtFilePath, String attr,
            String data) {
        return updateBuilder(tgtFilePath, attr, data, REPLACE);
    }

    public static String prependBuilderInfo(String tgtFilePath, String attr,
            String data) {
        return updateBuilder(tgtFilePath, attr, data, PREPEND);
    }

    public static String appendBuilderInfo(String tgtFilePath, String attr, String data) {
        return updateBuilder(tgtFilePath, attr, data, APPEND);
    }

    private static String rVal = "";
    
    public static String updateBuilder(final String tgtFilePath, final String attr, final String data,
            final int action) {
        try {
            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                
                @Override
                public void run(IProgressMonitor monitor) throws CoreException {
                    rVal = data;
                    boolean saveFile = false;
                    boolean foundAsStringAttr = false;
                    try {
                        FileInputStream file = new FileInputStream(tgtFilePath);
                        DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder();
                        Document document = parser.parse(file);

                        file.close();

                        document.getDocumentElement().normalize();

                        // Make sure the launch config contains the environment variables
                        // map
                        boolean containsEnvMap = false;
                        NodeList nodes = document.getElementsByTagName("mapAttribute"); //$NON-NLS-1$

                        for (int s = 0; s < nodes.getLength(); s++) {
                            Node firstNode = nodes.item(s);
                            if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element firstNodeElement = (Element) firstNode;
                                String key = firstNodeElement.getAttribute(XML_KEY);
                                if (key.equals(ENV_ATTR_NAME)) {
                                    containsEnvMap = true;
                                }
                            }
                        }

                        // If the launch config doesn't contain the environment vars map,
                        // add it
                        if (!containsEnvMap) {
                            Node launchConfig = document.getFirstChild();
                            Element envMap = document.createElement("mapAttribute");
                            envMap.setAttribute(XML_KEY, ENV_ATTR_NAME);
                            Element mapEntry = document.createElement("mapEntry");
                            mapEntry.setAttribute(XML_VALUE, "Console");
                            envMap.appendChild(mapEntry);
                            launchConfig.appendChild(envMap);
                        }

                        // Set the attribute if it is a stringAttribute
                        nodes = document.getElementsByTagName("stringAttribute"); //$NON-NLS-1$

                        for (int s = 0; s < nodes.getLength(); s++) {
                            Node firstNode = nodes.item(s);
                            if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element firstNodeElement = (Element) firstNode;
                                String key = firstNodeElement.getAttribute(XML_KEY);
                                if (key.equals(attr)) {
                                    String value = firstNodeElement.getAttribute(XML_VALUE);
                                    foundAsStringAttr = true;
                                    if (!value.equals(data)) {
                                        if (action == PREPEND) {
                                            rVal = data + value;
                                        } else if (action == APPEND) {
                                            rVal = value + data;
                                        } else {
                                            rVal = data;
                                        }
                                        firstNodeElement.setAttribute(XML_VALUE, rVal);
                                        saveFile = true;
                                    }
                                }
                            }
                        }

                        // Otherwise possibly set the attribute as a mapEntry
                        if (!foundAsStringAttr) {
                            nodes = document.getElementsByTagName("mapEntry"); //$NON-NLS-1$

                            for (int s = 0; s < nodes.getLength(); s++) {
                                Node firstNode = nodes.item(s);
                                if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

                                    Element firstNodeElement = (Element) firstNode;
                                    String key = firstNodeElement.getAttribute(XML_KEY);
                                    if (key.equals(attr)) {
                                        String value = firstNodeElement
                                                .getAttribute(XML_VALUE);
                                        foundAsStringAttr = true;
                                        if (!value.equals(data)) {
                                            if (action == PREPEND) {
                                                rVal = data + value;
                                            } else if (action == APPEND) {
                                                rVal = value + data;
                                            } else {
                                                rVal = data;
                                            }
                                            firstNodeElement.setAttribute(XML_VALUE, rVal);
                                            saveFile = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (saveFile) {
                            TransformerFactory tFactory = TransformerFactory.newInstance();

                            Transformer transformer = tFactory.newTransformer();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            DOMSource source = new DOMSource(document);
                            StreamResult result = new StreamResult(stream);
                            transformer.transform(source, result);

                            // Save the document
                            FileOutputStream fileOut = new FileOutputStream(tgtFilePath);
                            fileOut.write(stream.toByteArray());
                            fileOut.close();
                        }

                    } catch (TransformerConfigurationException e) {
                    } catch (TransformerException e) {
                    } catch (FileNotFoundException e) {
                    } catch (ParserConfigurationException e) {
                    } catch (SAXException e) {
                    } catch (IOException e) {
                    }                   
                }
            }, new NullProgressMonitor());
        } catch (CoreException e) {
            CorePlugin.logError("Unable to update builder.", e);
        }
        return rVal;
    }

}
