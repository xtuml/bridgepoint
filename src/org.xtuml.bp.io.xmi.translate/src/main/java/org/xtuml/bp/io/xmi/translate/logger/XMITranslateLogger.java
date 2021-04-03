package org.xtuml.bp.io.xmi.translate.logger;

import java.util.ArrayList;
import java.util.List;

public class XMITranslateLogger {

    private boolean verbose;
    private List<String> standardLog = new ArrayList<>();
    private List<String> processorsNotImplemented = new ArrayList<>();
    private List<String> noMapping = new ArrayList<>();

    public XMITranslateLogger(boolean verbose) {
        this.verbose = verbose;
    }

    public void logError(String msg, Exception e) {
        System.err.println(msg);
        if (e != null) {
            System.err.println(e.getMessage());
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
        if (verbose) {
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
        System.out.println("\nTranslation complete.");
    }
}
