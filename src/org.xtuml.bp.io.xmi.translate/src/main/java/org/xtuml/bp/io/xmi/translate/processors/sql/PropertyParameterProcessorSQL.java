package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPropertyParameterProcessor;

public class PropertyParameterProcessorSQL extends AbstractPropertyParameterProcessor {

    static ModelElement lastElement = null;
    static int count = 1;
    static ModelElement owner;

    @Override
    public String getPP_Id() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSignal_Id() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getDT_ID() {
        ModelElement paramtypeElement = getModelElement().getRefAttribute("parametertype");
        // TODO: look into null param type (likely need a different way of traversing)
        if (paramtypeElement == null) {
            return SQLUtils.idValue(IdProcessor.NULL_ID);
        }
        return SQLUtils.idValue(paramtypeElement.getPlainAttribute("id"));
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Object: " + getModelElement().getFullName());
    }

    @Override
    public String getBy_Ref() {
        // TODO: determine whether we can support by ref/by val, default to by ref
        return SQLUtils.numberValue(1);
    }

    @Override
    public String getDimensions() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getPrevious_PP_Id() {
        String prevId = IdProcessor.NULL_ID;
        if (lastElement != null && lastElement.getOwner() == getModelElement().getOwner()) {
            prevId = lastElement.getPlainAttribute("id");
            lastElement = getModelElement();
        } else {
            lastElement = getModelElement();
        }
        return SQLUtils.idValue(prevId);
    }

    @Override
    public String getProcessorOutput() {
        if (getModelElement().getName().equals("return")) {
            // EA adds a paramter for the return type, do not output
            return "";
        }
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPP_Id(), getSignal_Id(), getDT_ID(), getName(), getDescrip(), getBy_Ref(), getDimensions(),
                getPrevious_PP_Id()).collect(Collectors.toList());
    }
}
