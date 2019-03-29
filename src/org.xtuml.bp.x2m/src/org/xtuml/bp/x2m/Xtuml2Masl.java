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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Xtuml2Masl {

    private static final int KILL_TIMEOUT = 2000;

    public static final String CLI_EXE = "CLI.sh";
    public static final String X2M_EXE = "x2m";
    public static final String MASL_EXE = "masl";
    public static final String CODE_GEN_FOLDER = "gen/code_generation";
    public static final String X2M_OUTPUT = "x2m_output.txt";
    public static final String MASL_OUTPUT = "masl_output.txt";
    public static final String GLOBALS_XTUML = "../schema/Globals.xtuml";

    private String projectLocation;
    private String outDir;
    private String name;
    private boolean isDomain;
    private boolean validate;
    private boolean coverage;
    private boolean prebuild;
    private boolean eclipse;
    private boolean skipFormat;
    private boolean skipActionLanguage;

    public Xtuml2Masl() {
        validate = false;
        coverage = false;
        prebuild = false;
        eclipse = false;
        outDir = ".";
        skipFormat = false;
        skipActionLanguage = false;
        projectLocation = "";
        name = "";
        isDomain = true;
    }

    // run a xtUML to MASL export
    public void xtuml2masl() throws IOException, RuntimeException {
        System.out.println("\n=====================================================================================================");
        System.out.println("Exporting MASL for " + name + "...");

        // create the output directory if it does not exist
        File outDirFile = new File(outDir);
        if (!outDirFile.exists()) {
            outDirFile.mkdirs();
        }

        // run pre-builder if executing outside eclipse on a project project
        if (!eclipse && prebuild) {
            // build prebuild process
            List<String> prebuildCmd = new ArrayList<>();
            prebuildCmd.add(toolsFolder() + File.separator + CLI_EXE);
            prebuildCmd.add("Build");
            prebuildCmd.add("-prebuildOnly");
            prebuildCmd.add("-doNotParse");
            prebuildCmd.add("-project");
            Path projectPath = new File(projectLocation).toPath();
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
        System.out.println(maslCmd);
        Process maslProcess = new ProcessBuilder().command(maslCmd).start();

        // pipe inputs and outputs together
        Path projectPath = new File(projectLocation).toPath();
        String projectName = projectPath.getName(projectPath.getNameCount() - 1).toString();
        FileInputStream inputFile;
        if (prebuild) {
            inputFile = new FileInputStream(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + projectName + ".sql");
        } else {
            inputFile = new FileInputStream(toolsFolder() + GLOBALS_XTUML);
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

    public Xtuml2Masl setPrebuild(boolean prebuild) {
        this.prebuild = prebuild;
        return this;
    }

    public Xtuml2Masl setEclipseBuild(boolean eclipse) {
        this.eclipse = eclipse;
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
        System.err.println(
                "Usage:\n\txtuml2masl [-v | -V] [-xf] [-xl] -i <eclipse project> -d <domain component> [-o <output directory>]\n\txtuml2masl [-v | -V] [-xf] [-xl] -i <eclipse project> -p <project package> [-o <output directory>]");
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
        String outDir = "";
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
            } else if ("-p".equals(arg) || "-d".equals(arg) || "-i".equals(arg) || "-o".equals(arg)) { // set the
                                                                                                       // directive
                directive = arg;
            } else if ("-xl".equals(arg) && !skipActionLanguage) { // if we encounter flag indicating skip output of
                                                                   // MASL activities
                skipActionLanguage = true;
                directive = "";
            } else if ("-i".equals(directive)) { // add an input
                inputs.add(arg);
            } else if ("-p".equals(directive)) { // add a project name
                buildElements.add(new BuildElement(false, arg));
            } else if ("-d".equals(directive)) { // add a domain name
                buildElements.add(new BuildElement(true, arg));
            } else if ("-o".equals(directive) && "".equals(outDir)) { // only can set the output directory once
                outDir = arg;
            } else {
                printUsage();
                System.exit(1);
            }
        }

        Xtuml2Masl exporter = new Xtuml2Masl().setValidate(validate).setCoverage(coverage).setPrebuild(prebuild).setEclipseBuild(false).setSkipFormat(skipFormatter)
                .setSkipActionLanguage(skipActionLanguage).setOutputDirectory("".equals(outDir) ? "." : outDir);
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
