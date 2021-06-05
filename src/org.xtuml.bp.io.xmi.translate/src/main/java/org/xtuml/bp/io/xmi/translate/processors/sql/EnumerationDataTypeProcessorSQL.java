package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractEnumerationDataTypeProcessor;

public class EnumerationDataTypeProcessorSQL extends AbstractEnumerationDataTypeProcessor {
    @Override
    public String getDT_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String createSupportingElements() {
        DataTypeProcessorSQL dtProcessor = new DataTypeProcessorSQL();
        dtProcessor.setKeyLetters("S_DT");
        dtProcessor.setModelElement(getModelElement().getOwner());
        return SQLUtils.getInsertStatement(dtProcessor, getModelElement());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getDT_ID());
    }
}
