package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractBaseAttributeProcessor;

public class BaseAttributeProcessorSQL extends AbstractBaseAttributeProcessor {
    @Override
    public String getAttr_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getAttr_ID(), getObj_ID());
    }
}
