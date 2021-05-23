package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.XtumlTypeProcessor;

public class SQLUtils {

    public static String getHeader() {
        return "-- root-types-contained: Package_c\n"
                + "-- BP 7.1 content: StreamData syschar: 3 persistence-version: 7.1.6\n";
    }

    public static boolean requiresPackageableElement(XtumlTypeProcessor processor, ModelElement element) {
        if (element == null) {
            return false;
        }
        if (processor.isGraphical()) {
            return false;
        }
        ModelElement owner = element.getOwner();
        if (owner == null) {
            // root package
            return true;
        }
        return owner != null && (owner.getType().getName().equals("model")
                || owner.getType().getName().equals("package") || owner.getType().getName().equals("component"));
    }

    public static String createPackageableElement(ModelElement element) {
        PackageableElementProcessorSQL processor = new PackageableElementProcessorSQL();
        processor.setKeyLetters("PE_PE");
        processor.setModelElement(element);
        return getInsertStatement(processor, element);
    }

    public static String getInsertStatement(XtumlTypeProcessor processor, ModelElement element) {
        List<String> values = processor.getValues(element);
        StringBuilder insert = new StringBuilder();
        values.forEach(v -> {
            insert.append(v);
        });
        StringBuilder adjustedInsertStart = insert.replace(0, 1, "");
        String adjustedInsert = adjustedInsertStart.substring(0, adjustedInsertStart.length() - 2);
        return "INSERT INTO " + processor.getKeyLetters() + "\n\tVALUES (" + adjustedInsert + ");\n";
    }

    public static String stringValue(String value) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        builder.append("'" + encodeString(value) + "'");
        builder.append(",");
        builder.append("\n");
        return builder.toString();
    }

    private static String encodeString(String value) {
        return value.replaceAll("'", "''");
    }

    public static String idValue(String value) {
        return idValue(value, null);
    }

    public static String idValue(String value, String keyLetters) {
        String id = IdProcessor.process(value, keyLetters);
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        builder.append('"' + id + '"');
        builder.append(",");
        builder.append("\n");
        return builder.toString();
    }

    public static String preprocessedIdValue(String value) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        builder.append('"' + value + '"');
        builder.append(",");
        builder.append("\n");
        return builder.toString();
    }

    public static String numberValue(Number value) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        builder.append(value);
        builder.append(",");
        builder.append("\n");
        return builder.toString();
    }

    public static String convertToPascalCase(String value) {
        return Arrays.stream(value.split(" ")).map(
                word -> word.isEmpty() ? word : Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(""));
    }

    public static String getProcessorOutput(AbstractXtumlTypeProcessor processor) {
        StringBuilder inserts = new StringBuilder();
        ModelElement element = processor.getModelElement();
        inserts.append(SQLUtils.getInsertStatement(processor, element));
        if (!processor.handlesPackageableElement() && SQLUtils.requiresPackageableElement(processor, element)) {
            inserts.append(SQLUtils.createPackageableElement(element));
        }
        inserts.append(processor.createSupportingElements());
        return inserts.toString();
    }

    public static String booleanValue(boolean val) {
        return val ? numberValue(1) : numberValue(0);
    }
}
