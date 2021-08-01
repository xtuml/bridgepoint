package org.xtuml.bp.io.xmi.translate;

public interface ILogger {
	void log(String value);

	default void logSkipped(String string) {
		log(string);
	}

	default void printReport(String output) {
		log(output);
	}

	default void logTodo(String string) {
		log(string);
	};

	default void logNoMapping(String string) {
		log(string);
	}

	default void logNoProcessor(String string) {
		log(string);
	}
}
