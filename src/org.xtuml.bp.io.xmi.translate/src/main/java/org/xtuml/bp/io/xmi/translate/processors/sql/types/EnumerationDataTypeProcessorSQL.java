package org.xtuml.bp.io.xmi.translate.processors.sql.types;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractEnumerationDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class EnumerationDataTypeProcessorSQL extends AbstractEnumerationDataTypeProcessor {
    @Override
    public String getDT_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String createSupportingElements() {
        DataTypeProcessorSQL dtProcessor = new DataTypeProcessorSQL();
        dtProcessor.setKeyLetters("S_DT");
        dtProcessor.setModelElement(getModelElement());
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
