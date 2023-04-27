package org.xtuml.bp.x2m;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
public class Xtuml2Masl {

    private static final int KILL_TIMEOUT = 2000;

    public static final String CLI_EXE = System.getProperty("os.name").toLowerCase().contains("win") ? "CLI.bat" : "CLI.sh";
    public static final String X2M_EXE = "x2m";
    public static final String MASL_EXE = "masl";
    public static final String CODE_GEN_FOLDER = "gen/code_generation";
    public static final String X2M_OUTPUT = "x2m_output.txt";
    public static final String MASL_OUTPUT = "masl_output.txt";
    public static final String GLOBALS_XTUML = "../schema/Globals.xtuml";

    private String projectLocation;
    private String outDir;
    private String architecture;
    private String name;
    private boolean isDomain;
    private boolean validate;
    private boolean coverage;
    private PrebuildType prebuild;
    private boolean skipFormat;
    private boolean skipActionLanguage;
    private boolean cleanBuild;
    
    public enum PrebuildType {
    	NO_PREBUILD,
    	PREBUILD,
    	DEFER_PREBUILD,
    }

    public Xtuml2Masl() {
        validate = false;
        coverage = false;
        prebuild = PrebuildType.PREBUILD;
        outDir = ".";
        architecture = "MASL";
        skipFormat = false;
        skipActionLanguage = false;
        cleanBuild = true;
        projectLocation = "";
        name = "";
        isDomain = true;
    }

    // run a xtUML to MASL export
    public void xtuml2masl() throws IOException, RuntimeException {
        System.out.println("\n=====================================================================================================");
        System.out.println("Exporting " + architecture +" for " + name + "...");

        // create the output directory if it does not exist
        File outDirFile = new File(outDir);
        if (!outDirFile.exists()) {
            outDirFile.mkdirs();
        }
        // if clean is enabled, delete the contents first
        if (cleanBuild) {
            Path outputPath = Path.of(outDirFile.getPath(), name);
            if (Files.exists(outputPath)) {
                Files.walkFileTree(Path.of(outDirFile.getPath(), name), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                        if (e == null) {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        } else {
                            // directory iteration failed
                            throw e;
                        }
                    }
                });
            }
        }
        // create the gen/code_generation directory if it does not exist
        File genDirFile = new File(projectLocation + File.separator + CODE_GEN_FOLDER);
        if (!genDirFile.exists()) {
            genDirFile.mkdirs();
        }

        // run pre-builder if executing outside eclipse on a project project
        if (prebuild == PrebuildType.PREBUILD) {
            // build prebuild process
            List<String> prebuildCmd = new ArrayList<>();
            prebuildCmd.add(toolsFolder() + File.separator + CLI_EXE);
            prebuildCmd.add("Build");
            prebuildCmd.add("-prebuildOnly");
            prebuildCmd.add("-doNotParse");
            prebuildCmd.add("-project");
            Path projectPath = new File(projectLocation).getCanonicalFile().toPath();
            prebuildCmd.add(projectPath.getName(projectPath.getNameCount() - 1).toString());
            System.out.println(prebuildCmd);
            Process prebuildProcess = new ProcessBuilder().command(prebuildCmd).start();
            connectStreams(false, prebuildProcess.getInputStream(), System.out);
            connectStreams(false, prebuildProcess.getErrorStream(), System.out);
            prebuildProcess.getErrorStream().close();
            prebuildProcess.getInputStream().close();
            waitForProcess(prebuildProcess, "prebuild", false);
        }

        // build x2m process
        List<String> x2mCmd = new ArrayList<>();
        x2mCmd.add(toolsFolder() + File.separator + X2M_EXE);
        if (skipActionLanguage) {
            x2mCmd.add("-s");
        }
        x2mCmd.add("-i");
        x2mCmd.add(projectLocation);
        if (isDomain) {
            x2mCmd.add("-d");
        } else {
            x2mCmd.add("-p");
        }
        x2mCmd.add(name);
        if (prebuild == PrebuildType.PREBUILD || prebuild == PrebuildType.DEFER_PREBUILD) {
            x2mCmd.add("-P");
        }
        System.out.println(x2mCmd);
        Process x2mProcess = new ProcessBuilder().command(x2mCmd).start();

        // build masl process
        List<String> maslCmd = new ArrayList<>();
        maslCmd.add(toolsFolder() + File.separator + MASL_EXE);
        if (validate) {
            maslCmd.add("-v");
        }
        if (coverage) {
            maslCmd.add("-c");
        }
        if (skipActionLanguage) {
            maslCmd.add("-s");
        }
        maslCmd.add("-i");
        maslCmd.add(projectLocation);
        if (isDomain) {
            maslCmd.add("-d");
        } else {
            maslCmd.add("-p");
        }
        maslCmd.add(name);
        maslCmd.add("-o");
        maslCmd.add(outDir);
        maslCmd.add("-a");
        maslCmd.add(architecture);
        System.out.println(maslCmd);
        Process maslProcess = new ProcessBuilder().command(maslCmd).start();

        // pipe inputs and outputs together
        Path projectPath = new File(projectLocation).getCanonicalFile().toPath();
        String projectName = projectPath.getName(projectPath.getNameCount() - 1).toString();
        FileInputStream inputFile;
        if (prebuild == PrebuildType.PREBUILD || prebuild == PrebuildType.DEFER_PREBUILD) {
            inputFile = new FileInputStream(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + projectName + ".sql");
        } else {
            // if no prebuilder, load the native types
            inputFile = new FileInputStream(toolsFolder() + File.separator + GLOBALS_XTUML);
        }
        connectStreams(true, inputFile, x2mProcess.getOutputStream());
        FileOutputStream x2mOutputFile = new FileOutputStream(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + X2M_OUTPUT);
        connectStreams(true, x2mProcess.getInputStream(), x2mOutputFile, maslProcess.getOutputStream());
        connectStreams(false, x2mProcess.getErrorStream(), System.out);
        x2mProcess.getErrorStream().close();
        FileOutputStream maslOutputFile = new FileOutputStream(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + MASL_OUTPUT);
        connectStreams(false, maslProcess.getInputStream(), maslOutputFile, System.out);
        connectStreams(false, maslProcess.getErrorStream(), System.out);
        maslProcess.getErrorStream().close();
        maslProcess.getInputStream().close();
        maslOutputFile.close();

        // wait for processes to complete
        waitForProcess(x2mProcess, "x2m");
        waitForProcess(maslProcess, "masl");

        // format output MASL
        if (!skipFormat) {
            final String formatClasspath = Stream.of(new File(toolsFolder()).list())
                    .filter((fileName) -> fileName.endsWith("jar"))
                    .map((fileName) -> toolsFolder() + File.separator + fileName).collect(Collectors.joining(":"));
            File childFile = new File(outDirFile, name);
            if (childFile.exists() && childFile.isDirectory()) {

                // create a temporary location for formatted MASL
                File tempLocation = new File(childFile.toString() + ".formatted");
                if (!tempLocation.exists()) {
                    tempLocation.mkdirs();
                }

                // build format process
                List<String> formatCmd = new ArrayList<>();
                formatCmd.add(System.getProperty("java.home") + "/bin/java");
                formatCmd.add("-cp");
                formatCmd.add(formatClasspath);
                formatCmd.add("MaslFormatter");
                formatCmd.add("-r");
                formatCmd.add("-i");
                formatCmd.add(childFile.getAbsolutePath());
                formatCmd.add("-o");
                formatCmd.add(tempLocation.getAbsolutePath());
                System.out.println(formatCmd);
                Process formatProcess = new ProcessBuilder().command(formatCmd).start();
                connectStreams(false, formatProcess.getInputStream(), System.out);
                connectStreams(false, formatProcess.getErrorStream(), System.out);
                formatProcess.getErrorStream().close();
                formatProcess.getInputStream().close();
                int exitValue = waitForProcess(formatProcess, "format", false);
                if (0 == exitValue) {
                    // delete original location
                    Files.walk(childFile.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile)
                            .forEach(File::delete);
                    // move temp location to original location
                    Files.move(tempLocation.toPath(), childFile.toPath());
                } else {
                    // delete temp location
                    Files.walk(tempLocation.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile)
                            .forEach(File::delete);
                }

            }
        }

        System.out.println("Done.");
    }

    // chaining setters
    public Xtuml2Masl setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
        return this;
    }

    public Xtuml2Masl setOutputDirectory(String outDir) {
        this.outDir = outDir;
        return this;
    }

    public Xtuml2Masl setArchitecture(String architecture) {
        this.architecture = architecture;
        return this;
    }

    public Xtuml2Masl setName(String name) {
        this.name = name;
        return this;
    }

    public Xtuml2Masl setIsDomain(boolean isDomain) {
        this.isDomain = isDomain;
        return this;
    }

    public Xtuml2Masl setValidate(boolean validate) {
        this.validate = validate;
        return this;
    }

    public Xtuml2Masl setCoverage(boolean coverage) {
        this.coverage = coverage;
        return this;
    }

    public Xtuml2Masl setPrebuild(PrebuildType prebuild) {
        this.prebuild = prebuild;
        return this;
    }

    public Xtuml2Masl setSkipFormat(boolean skipFormat) {
        this.skipFormat = skipFormat;
        return this;
    }

    public Xtuml2Masl setSkipActionLanguage(boolean skipActionLanguage) {
        this.skipActionLanguage = skipActionLanguage;
        return this;
    }

    public Xtuml2Masl setCleanBuild(boolean cleanBuild) {
        this.cleanBuild = cleanBuild;
        return this;
    }

    private static void connectStreams(boolean close, InputStream in, OutputStream... outs) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        List<BufferedWriter> bufferedWriters = new ArrayList<>();
        for (OutputStream out : outs) {
            bufferedWriters.add(new BufferedWriter(new OutputStreamWriter(out)));
        }
        String lineToPipe;
        try {
            while ((lineToPipe = bufferedReader.readLine()) != null) {
                for (BufferedWriter bufferedWriter : bufferedWriters) {
                    bufferedWriter.write(lineToPipe + '\n');
                    bufferedWriter.flush();
                }
            }
            if (close) {
                in.close();
                for (OutputStream out : outs) {
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int waitForProcess(Process process, String name) throws RuntimeException {
        return waitForProcess(process, name, true);
    }

    private static int waitForProcess(Process process, String name, boolean throwError) throws RuntimeException {
        boolean finished = false;
        try {
            finished = process.waitFor(KILL_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            /* do nothing */}
        if (!finished) {
            throw new RuntimeException(name + " subprocess terminated.");
        } else if (process.exitValue() != 0) {
            if (throwError) {
                throw new RuntimeException(name + " subprocess failed: " + process.exitValue());
            }
        }
        return process.exitValue();
    }

    private static String toolsFolder() {
        String toolsFolder = "";
        if (null != System.getProperty("eclipse.home.location")) {
            toolsFolder = System.getProperty("eclipse.home.location").replaceFirst("file:", "") + "/tools/mc/bin";
        }
        if (new File(toolsFolder).exists()) {
            return toolsFolder;
        }
        else {
            return System.getenv("MASL_BIN_DIR");
        }
    }

	private static void printUsage() {
		System.err.println("Usage:\n"
        + "\txtuml2masl [-v | -V] [-c] [-P] [-xf] [-xl] [-a <architecture>] -i <eclipse project location> -d <domain component> [ -o <output directory> ]\n"
        + "\txtuml2masl [-v | -V] [-c] [-P] [-xf] [-xl] [-a <architecture>] -i <eclipse project location> -p <project/deployment package> [ -o <output directory> ]\n"
        + "\n"
        + "\t| Parameter                             | Description                                                                                                  |\n"
        + "\t|---------------------------------------|--------------------------------------------------------------------------------------------------------------|\n"
        + "\t| -v -V                                 | Optionally print help message                                                                                |\n"
        + "\t| -c                                    | Optionally used to enable the production of coverage statistics                                              |\n"
        + "\t| -P                                    | Optionally used to cause BridgePoint prebuilder to be used to export model data                              |\n"
        + "\t| -xf                                   | Optionally skip the formatting step                                                                          |\n"
        + "\t| -xl                                   | Optionally exclude action langauge from export                                                               |\n"
        + "\t| -a &lt;architecture&gt;               | Specify the output architecture. \"MASL\" and \"WASL\" are valid options. \"MASL\" is the default.           |\n"
        + "\t| -i &lt;eclipse project location&gt;   | Specify an absolute or relative path to the root directory of the Eclipse project containing the input model |\n"
        + "\t| -d &lt;domain component&gt;           | Specify xtUML component that will be exported as a MASL domain                                               |\n"
        + "\t| -p &lt;project/deployment package&gt; | Specify xtUML package that will be exported as a MASL project/deployment                                     |\n"
        + "\t| -o &lt;output directory&gt;           | Optionally specify the target folder to write to                                                             |");
	}

    private static class BuildElement {

        public boolean isDomain;
        public String name;

        public BuildElement(boolean isDomain, String name) {
            this.isDomain = isDomain;
            this.name = name;
        }
    }

    public static void main(String[] args) {

        boolean validate = false;
        boolean coverage = false;
        boolean prebuild = false;
        boolean skipFormatter = false;
        boolean skipActionLanguage = false;
        boolean cleanBuild = true;
        String outDir = "";
        String architecture = "MASL"; // default to MASL
        List<String> inputs = new ArrayList<>();
        List<BuildElement> buildElements = new ArrayList<>();

        // parse arguments
        String directive = "";
        for (String arg : args) {
            if (("-v".equals(arg) || "-V".equals(arg)) && !validate) { // if we encounter a validate flag, set the
                                                                       // validation
                validate = true;
                directive = ""; // encountering a validation flag resets the directive because
            } else if ("-c".equals(arg) && !coverage) { // produce coverage output
                coverage = true;
                directive = "";
            } else if ("-P".equals(arg) && !prebuild) { // Run the prebuilder.
                prebuild = true;
                directive = "";
            } else if ("-xf".equals(arg) && !skipFormatter) { // if we encounter flag indicating skip MASL formatting
                skipFormatter = true;
                directive = "";
            } else if ("-p".equals(arg) || "-d".equals(arg) || "-i".equals(arg) || "-o".equals(arg) || "-a".equals(arg)) { // set the
                                                                                                       // directive
                directive = arg;
            } else if ("-xl".equals(arg) && !skipActionLanguage) { // if we encounter flag indicating skip output of
                                                                   // MASL activities
                skipActionLanguage = true;
                directive = "";
            } else if ("-xc".equals(arg) && cleanBuild) { // if we encounter flag indicating to skip clean
                cleanBuild = false;
                directive = "";
            } else if ("-i".equals(directive)) { // add an input
                inputs.add(arg);
            } else if ("-p".equals(directive)) { // add a project name
                buildElements.add(new BuildElement(false, arg));
            } else if ("-d".equals(directive)) { // add a domain name
                buildElements.add(new BuildElement(true, arg));
            } else if ("-o".equals(directive) && "".equals(outDir)) { // only can set the output directory once
                outDir = arg;
            } else if ("-a".equals(directive)) {
                architecture = arg;
            } else {
                printUsage();
                System.exit(1);
            }
        }

        Xtuml2Masl exporter = new Xtuml2Masl().setValidate(validate).setCoverage(coverage).setPrebuild(prebuild ? PrebuildType.PREBUILD : PrebuildType.NO_PREBUILD).setSkipFormat(skipFormatter)
                .setSkipActionLanguage(skipActionLanguage).setCleanBuild(cleanBuild).setArchitecture(architecture).setOutputDirectory("".equals(outDir) ? "." : outDir);
        for (int i = 0; i < inputs.size(); i++) {
            exporter = exporter.setProjectLocation(inputs.get(i)).setName(buildElements.get(i).name)
                    .setIsDomain(buildElements.get(i).isDomain);
            try {
                exporter.xtuml2masl();
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }

}
