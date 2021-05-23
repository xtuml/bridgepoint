package org.xtuml.bp.io.xmi.translate.logger;

import java.util.ArrayList;
import java.util.List;

public class XMITranslateLogger {

    private boolean verbose;
    private List<String> standardLog = new ArrayList<>();
    private List<String> processorsNotImplemented = new ArrayList<>();
    private List<String> noMapping = new ArrayList<>();
    private List<String> skipped = new ArrayList<>();
    private boolean todo;

    public XMITranslateLogger(boolean verbose, boolean todo) {
        this.verbose = verbose;
        this.todo = todo;
    }

    public void logError(String msg, Exception e) {
        System.err.println(msg);
        if (e != null) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void log(String msg) {
        standardLog.add(msg);
    }

    public void logNoMapping(String msg) {
        noMapping.add(msg);
    }

    public void logNoProcessor(String msg) {
        processorsNotImplemented.add(msg);
    }

    public void printOutput(String fileOut) {
        // if todo is enabled return that only
        if (todo) {
            System.out.println(" \n");
            System.out.println("Elements found without a mapping:\n");
            noMapping.stream().distinct().forEach(m -> System.out.println("  " + m));
            System.out.println(" \n");
            System.out.println("Mappings found without an implemented processor:\n");
            processorsNotImplemented.stream().distinct().forEach(p -> System.out.println("  " + p));
            System.out.println("\n");
            System.out.println("Elements marked as skipping translation:\n");
            skipped.stream().distinct().forEach(p -> System.out.println("  " + p));
            System.out.println("\n");
            System.out.println("\nTranslation complete.\n");
            return;
        }
        if (verbose || fileOut.equals("")) {
            System.out.println("Translation outcome below.\n\n");
            System.out.println("Elements found without a mapping:\n");
            noMapping.forEach(System.out::print);
            System.out.println("Mappings found without an implemented processor:\n");
            processorsNotImplemented.forEach(System.out::print);
            System.out.println("Translated elements:\n");
            standardLog.forEach(System.out::print);
            if (!fileOut.equals("")) {
                System.out.println("\n\nTranslation written to " + fileOut + "\n");
            }
        }
        System.out.println("\nTranslation complete.\n");
    }

    public void logSkipped(String string) {
        skipped.add(string);
    }
}
