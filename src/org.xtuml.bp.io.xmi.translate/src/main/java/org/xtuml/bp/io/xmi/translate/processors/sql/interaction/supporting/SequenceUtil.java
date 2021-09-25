package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.LifespanProcessorSQL;

public class SequenceUtil {
    // lifelines are reused through references
    private Map<String, ModelElement> createdLifelines = new HashMap<>();

    public String createSequence(ModelElement modelElement) {
        // traverse through this package to access all sequence
        // elements
        StringJoiner sequence = new StringJoiner("");
        sequence.add(createLifelines(modelElement.getPlainAttribute("id"), modelElement));
        try {
            Files.write(Path.of("output/", modelElement.getName().replaceAll(" ", "_")),
                    sequence.toString().getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sequence.toString();
    }

    private String createLifelines(String packageId, ModelElement modelElement) {
        StringJoiner lifelines = new StringJoiner("");
        if (modelElement.getType().hasAttribute("lifelines")) {
            modelElement.getSetAttribute("lifelines").stream().forEach(l -> {
                ModelElement lifeLine = (ModelElement) l;
                if (!createdLifelines.containsKey(lifeLine.getPlainAttribute("id"))) {
                    lifelines.add(createLifeSpan(packageId, null, lifeLine));
                }
            });
        }
        if (modelElement.getType().hasAttribute("fragments")) {
            modelElement.getSetAttribute("fragments").stream().forEach(f -> {
                ModelElement fragment = (ModelElement) f;
                if (fragment.getType().getName().equals("occurrencespec")) {
                    ModelElement covered = fragment.getRefAttribute("covered");
                    handleCovered(lifelines, packageId, covered, fragment);
                }
                if (fragment.getType().isSetAttribute("covered")) {
                    fragment.getSetAttribute("covered").forEach(c -> {
                        ModelElement covered = (ModelElement) c;
                        handleCovered(lifelines, packageId, covered, covered);
                    });
                    lifelines.add(createLifelines(packageId, fragment));
                }
            });
        }
        if (modelElement.getType().hasAttribute("operands")) {
            modelElement.getSetAttribute("operands").forEach(op -> {
                lifelines.add(createLifelines(packageId, (ModelElement) op));
            });
        }
        return lifelines.toString();
    }

    private void handleCovered(StringJoiner lifelines, String packageId, ModelElement covered, ModelElement fragment) {
        // if not covered, skip the fragment representing any already created lifespan
        if (covered != null && !createdLifelines.containsKey(covered.getPlainAttribute("id"))) {
            lifelines.add(createLifeSpan(packageId, covered, fragment));
        }
    }

    private String createLifeSpan(String packageId, ModelElement covered, ModelElement lifeLine) {
        LifespanProcessorSQL lifespanProcessorSQL = new LifespanProcessorSQL(packageId, covered);
        lifespanProcessorSQL.setModelElement(lifeLine);
        lifespanProcessorSQL.setKeyLetters("SQ_LS");
        createdLifelines.put(covered == null ? lifeLine.getPlainAttribute("id") : covered.getPlainAttribute("id"),
                lifeLine);
        return lifespanProcessorSQL.getProcessorOutput();
    }
}
