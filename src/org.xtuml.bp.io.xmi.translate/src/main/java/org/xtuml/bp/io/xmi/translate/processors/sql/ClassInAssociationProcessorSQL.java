package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassInAssociationProcessor;

public class ClassInAssociationProcessorSQL extends AbstractClassInAssociationProcessor {

    private String classId;
    private String assocId;
    private String Id;

    public ClassInAssociationProcessorSQL(String classId, String assocId) {
        this.classId = classId;
        this.assocId = assocId;
        this.Id = IdProcessor.UUID().toString();
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
        // generate an id
        return SQLUtils.idValue(Id);
    }

    @Override
    public String getIObj_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    public String getId() {
        return Id;
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID(), getIObj_ID()).collect(Collectors.toList());
    }
}
