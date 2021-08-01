package org.xtuml.bp.io.xmi.translate.processors.sql.classes.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInstanceReferenceDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class InstanceReferenceDataTypeProcessorSQL extends AbstractInstanceReferenceDataTypeProcessor {
    private String id;

    public InstanceReferenceDataTypeProcessorSQL(String id) {
        this.id = id;
    }

    @Override
    public String getDT_ID() {
        return SQLUtils.idValue(this.id);
    }

    @Override
    public String getisSet() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getDT_ID(), getisSet(), getObj_ID());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
