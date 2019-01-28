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

    public static final int KILL_TIMEOUT = 2000;

    public static final String XTUMLMC_BUILD_EXE = "xtumlmc_build";
    public static final String X2M_EXE = "x2m";
    public static final String MASL_EXE = "masl";
    public static final String CODE_GEN_FOLDER = "gen/code_generation";
    public static final String CLEANSE_OUTPUT = "z.xtuml";
    public static final String X2M_INPUT = "a.xtuml";
    public static final String X2M_OUTPUT = "x2m_output.txt";
    public static final String MASL_OUTPUT = "masl_output.txt";

    private String projectLocation;
    private String outDir;
    private String name;
    private boolean isDomain;
    private boolean validate;
    private boolean eclipse;
    private boolean skipFormat;
    private boolean skipActionLanguage;

    public Xtuml2Masl() {
        validate = false;
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
        System.out.println("Initializing MASL export...");

        // create the output directory if it does not exist
        File outDirFile = new File(outDir);
        if (!outDirFile.exists()) {
            outDirFile.mkdirs();
        }

        // run pre-builder if executing outside eclipse
        if (!eclipse) {
            // TODO execute pre-build
        }

        // cleanse pre-builder output
        Path projectPath = new File(projectLocation).toPath();
        String projectName = projectPath.getName(projectPath.getNameCount() - 1).toString();
        File sqlFile = new File(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + projectName + ".sql");
        File cleanseFile = new File(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + CLEANSE_OUTPUT);
        Files.copy(sqlFile.toPath(), cleanseFile.toPath());
        List<String> cleanseCmd = new ArrayList<>();
        cleanseCmd.add(toolsFolder() + File.separator + XTUMLMC_BUILD_EXE);
        cleanseCmd.add("xtumlmc_cleanse_for_BridgePoint");
        cleanseCmd.add(projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + CLEANSE_OUTPUT);
        cleanseCmd.add(projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + X2M_INPUT);
        cleanseCmd.add("GD_");
        cleanseCmd.add("DIM_");
        System.out.println(cleanseCmd);
        Process cleanseProcess = new ProcessBuilder().command(cleanseCmd).start();
        waitForProcess(cleanseProcess, "cleanse");

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
        FileInputStream inputFile = new FileInputStream(
                projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + X2M_INPUT);
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
            for (String child : outDirFile.list()) {
                File childFile = new File(outDirFile, child);
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
        }

        // clean up temporary files
        new File(projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + CLEANSE_OUTPUT).delete();
        new File(projectLocation + File.separator + CODE_GEN_FOLDER + File.separator + X2M_INPUT).delete();

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
        return System.getProperty("eclipse.home.location").replaceFirst("file:", "") + "/tools/mc/bin";
    }

    public static void main(String[] args) {
        // TODO handle command line arguments to run export
    }

}
