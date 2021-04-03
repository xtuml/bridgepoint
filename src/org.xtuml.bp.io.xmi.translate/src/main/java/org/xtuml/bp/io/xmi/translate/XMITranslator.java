package org.xtuml.bp.io.xmi.translate;

import java.io.File;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xtuml.bp.io.xmi.translate.arguments.XMITranslateOptions;
import org.xtuml.bp.io.xmi.translate.logger.XMITranslateLogger;

/**
 * Translate UML2 compatible XMI to xtUML
 */
public class XMITranslator {

	public static XMITranslateLogger logger;

	public static void main(String[] args) {
		// create the parser
		Options options = XMITranslateOptions.options();
		CommandLineParser parser = new DefaultParser();
		String xmiFile = "";
		String metamodel = "";
		String transformer = "";
		String output = "";
		boolean verbose = false;
		try {
			CommandLine cmdLine = parser.parse(options, args);
			if (cmdLine.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("XMITranslate", options);
				System.exit(1);
			}
			if (cmdLine.hasOption("x")) {
				xmiFile = cmdLine.getParsedOptionValue("x").toString();
			}
			if (cmdLine.hasOption("mm")) {
				metamodel = cmdLine.getParsedOptionValue("mm").toString();
			}
			if (cmdLine.hasOption("t")) {
				transformer = cmdLine.getParsedOptionValue("t").toString();
			}
			if (cmdLine.hasOption("out")) {
				output = cmdLine.getParsedOptionValue("out").toString();
				boolean overwrite = cmdLine.hasOption("overwrite");
				// ask about overwriting, unless already specified
				File tmp = new File(output);
				if (!overwrite && tmp.exists()) {
					Scanner input = new Scanner(System.in);
					System.out.println("Specified output file exists, would you like to overwrite? (y/n)");
					String response = input.next();
					while (!response.equals("y") && !response.equals("n")) {
						System.out.println("Enter y or n.");
						response = input.next();
					}
					input.close();
					if (response.equals("n")) {
						System.out.println("Run again with a different output");
						System.exit(0);
					}
				}
			}
			if (cmdLine.hasOption("v")) {
				verbose = true;
			}
		} catch (MissingOptionException e) {
			System.out.println(e.getMessage() + "\n");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("XMITranslate", options);
			System.exit(1);
		} catch (ParseException e) {
			System.err.println("Unable to parse command line options.\n" + e.getMessage());
			System.exit(2);
		}

		logger = new XMITranslateLogger(verbose);

		XMITranslate xmiTranslate = new XMITranslate();
		try {
			xmiTranslate.loadXMI(metamodel, transformer, xmiFile, output);
		} catch (Exception e) {
			logger.logError("Unable to translate model.", e);
		}
	}

	public static void log(String msg) {
		logger.log(msg);
	}

	public static void logError(String msg, Exception e) {
		logger.logError(msg, e);
	}

	public static void logNoProcessor(String string) {
		logger.logNoProcessor(string);
	}

	public static void logNoMapping(String string) {
		logger.logNoMapping(string);
	}

	public static void printReport(String fileOut) {
		logger.printOutput(fileOut);
	}

}
