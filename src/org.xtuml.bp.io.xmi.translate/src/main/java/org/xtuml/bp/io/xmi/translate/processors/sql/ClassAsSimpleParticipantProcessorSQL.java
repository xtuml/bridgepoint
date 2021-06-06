package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassAsSimpleParticipantProcessor;

public class ClassAsSimpleParticipantProcessorSQL extends AbstractClassAsSimpleParticipantProcessor {
    private String classId = IdProcessor.NULL_ID;
    private String assocId = IdProcessor.NULL_ID;
    private String classInAssocId = IdProcessor.NULL_ID;
    private Integer multiplicity = 0;
    private Integer conditionality = 0;
    private String textPhrase = "";

    public ClassAsSimpleParticipantProcessorSQL(String classId, String assocId, String classInAssocId,
            Integer multiplicity, Integer conditionality, String textPhrase) {
        this.classId = classId;
        this.assocId = assocId;
        this.classInAssocId = classInAssocId;
        this.multiplicity = multiplicity;
        this.conditionality = conditionality;
        this.textPhrase = textPhrase;
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(classId);
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public String getOIR_ID() {
        return SQLUtils.idValue(classInAssocId);
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(multiplicity);
    }

    @Override
    public String getCond() {
        return SQLUtils.numberValue(conditionality);
    }

    @Override
    public String getTxt_Phrs() {
        return SQLUtils.stringValue(textPhrase);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID(), getMult(), getCond(), getTxt_Phrs())
                .collect(Collectors.toList());
    }
}
