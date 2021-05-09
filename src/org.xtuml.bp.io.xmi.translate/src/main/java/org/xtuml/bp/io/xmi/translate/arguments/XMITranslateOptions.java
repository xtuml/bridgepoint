package org.xtuml.bp.io.xmi.translate.arguments;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class XMITranslateOptions {
	public static Options options() {
		Options options = new Options();
		Option xmiOption = Option.builder("x").longOpt("xmi").argName("file path")
				.desc("The path to a UML 2 compatible XMI document.").hasArg().required().build();
		options.addOption(xmiOption);
		Option metaOption = Option.builder("mm").longOpt("metamodel").argName("metamodel path")
				.desc("The path to a UML 2 based metamodel.").hasArg().required().build();
		options.addOption(metaOption);
		Option transformerOption = Option.builder("t").longOpt("transformer").argName("transformer path")
				.desc("The path to a SDMetric transformer.").hasArgs().required().build();
		options.addOption(transformerOption);
		Option outputOption = Option.builder("out").longOpt("output").argName("output file")
				.desc("The path to the desired output xtuml file").hasArg().build();
		options.addOption(outputOption);
		Option overwriteOption = Option.builder("overwrite").argName("overwrite output file")
				.desc("Overwrite the path to the desired output xtuml file").build();
		options.addOption(overwriteOption);
		Option verboseOption = Option.builder("v").longOpt("verbose").argName("enable verbose output")
				.desc("Enable all output messages").build();
		options.addOption(verboseOption);
		Option showTodo = Option.builder("todo").argName("show unimplemented processors")
				.desc("Show untranslated areas.").build();
		options.addOption(showTodo);
		return options;
	}
}
