package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractAttributeProcessor;

public class AttributeProcessorSQL extends AbstractAttributeProcessor {
    static ModelElement lastValue = null;

    @Override
    public String createSupportingElements() {
        BaseAttributeProcessorSQL battrProcessor = new BaseAttributeProcessorSQL();
        NewBaseAttributeProcessorSQL nbattrProcessor = new NewBaseAttributeProcessorSQL();
        battrProcessor.setKeyLetters("O_BATTR");
        battrProcessor.setModelElement(getModelElement());
        nbattrProcessor.setKeyLetters("O_NBATTR");
        nbattrProcessor.setModelElement(getModelElement());
        return SQLUtils.getInsertStatement(battrProcessor, getModelElement()) + "\n"
                + SQLUtils.getInsertStatement(nbattrProcessor, getModelElement());
    }

    @Override
    public String getAttr_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getPAttr_ID() {
        String prevId = IdProcessor.NULL_ID;
        if (lastValue != null && lastValue.getOwner() == getModelElement().getOwner()) {
            prevId = lastValue.getPlainAttribute("id");
            lastValue = getModelElement();
        } else {
            lastValue = getModelElement();
        }
        return SQLUtils.idValue(prevId);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName().equals("") ? getModelElement().getOwner().getName()
                : getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element " + getModelElement().getFullName());
    }

    @Override
    public String getPrefix() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getRoot_Nam() {
        return SQLUtils.stringValue(getModelElement().getName().equals("") ? getModelElement().getOwner().getName()
                : getModelElement().getName());
    }

    @Override
    public String getPfx_Mode() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getDT_ID() {
        ModelElement refAttribute = getModelElement().getRefAttribute("propertytype");
        String refId = IdProcessor.NULL_ID;
        if (refAttribute != null) {
            refId = refAttribute.getPlainAttribute("id");
        } else {
            // figure out core type mapping
            String global = IdProcessor.getIdForType("integer");
            if (global != null) {
                refId = global;
            }
        }
        return SQLUtils.idValue(refId);
    }

    @Override
    public String getDimensions() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getDefaultValue() {
        return SQLUtils.stringValue("");
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getAttr_ID(), getObj_ID(), getPAttr_ID(), getName(), getDescrip(), getPrefix(), getRoot_Nam(),
                getPfx_Mode(), getDT_ID(), getDimensions(), getDefaultValue());
    }
}
