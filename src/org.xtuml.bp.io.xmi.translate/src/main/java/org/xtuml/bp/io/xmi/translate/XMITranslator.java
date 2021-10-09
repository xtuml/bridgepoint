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
		System.setProperty("file.encoding", "UTF-16");
		// create the parser
		Options options = XMITranslateOptions.options();
		CommandLineParser parser = new DefaultParser();
		String[] xmiFiles = new String[0];
		String metamodel = "";
		String[] transformers = new String[0];
		String output = "";
		boolean verbose = false;
		boolean todo = false;
		try {
			CommandLine cmdLine = parser.parse(options, args);
			if (cmdLine.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("XMITranslate", options);
				System.exit(1);
			}
			if (cmdLine.hasOption("x")) {
				xmiFiles = cmdLine.getOptionValues("x");
			}
			if (cmdLine.hasOption("mm")) {
				metamodel = cmdLine.getParsedOptionValue("mm").toString();
			}
			if (cmdLine.hasOption("t")) {
				transformers = cmdLine.getOptionValues("t");
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
			if (cmdLine.hasOption("todo")) {
				todo = true;
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

		logger = new XMITranslateLogger(verbose, todo);

		XMITranslate xmiTranslate = new XMITranslate(verbose || todo ? logger : (msg) -> {});
		for(String xmiFile: xmiFiles) {
			try {
				String xtuml = xmiTranslate.loadXMI(metamodel, transformers, xmiFile, output);
				// output the xtuml model unless todo, verbose are enabled or an output file is given
				// in the case of todo we are less worried about the output
				if(!todo && !verbose && output.equals("")) {
					System.out.print(xtuml);
				}
			} catch (Exception e) {
				logger.logError("Unable to translate model.", e);
			}
		}
	}

}
