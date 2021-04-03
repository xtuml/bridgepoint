package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPortProcessor;

public class PortProcessorSQL extends AbstractPortProcessor {

    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getComponent_Id() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getDoNotShowPortOnCanvas() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getKey_Lett() {
        return SQLUtils.stringValue("PO_1");
    }

    @Override
    public List<String> getValues(ModelElement modelElement) {
        return List.of(getId(), getComponent_Id(), getMult(), getDoNotShowPortOnCanvas(), getKey_Lett());
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }
}
