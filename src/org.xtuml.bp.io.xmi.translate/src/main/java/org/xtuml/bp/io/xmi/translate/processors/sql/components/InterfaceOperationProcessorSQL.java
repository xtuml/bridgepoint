package org.xtuml.bp.io.xmi.translate.processors.sql.components;

import java.util.Collection;
import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInterfaceOperationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.components.supporting.ExecutablePropertyProcessorSQL;

public class InterfaceOperationProcessorSQL extends AbstractInterfaceOperationProcessor {
    static ModelElement lastElement = null;

    @Override
    public String createSupportingElements() {
        ExecutablePropertyProcessorSQL epProcessor = new ExecutablePropertyProcessorSQL();
        epProcessor.setKeyLetters("C_EP");
        epProcessor.setModelElement(getModelElement());
        return SQLUtils.getInsertStatement(epProcessor, getModelElement());
    }

    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getDT_ID() {
        /** UML2 treats return type as an owned parameter */
        String refId = IdProcessor.NULL_ID;
        Collection<ModelElement> parameters = getModelElement().getOwnedElements();
        for (ModelElement parameter : parameters) {
            if (parameter.getName().equals("return")) {
                ModelElement refAttribute = parameter.getRefAttribute("parametertype");
                if (refAttribute != null) {
                    refId = refAttribute.getPlainAttribute("id");
                    break;
                } else {
                    // figure out core type mapping
                    String global = IdProcessor.getIdForType("void");
                    if (global != null) {
                        refId = global;
                        break;
                    }
                }
            }
        }
        return SQLUtils.idValue(refId);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getDirection() {
        return SQLUtils.numberValue(1);
    }

    @Override
    public String getReturn_Dimensions() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getPrevious_Id() {
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
    public List<String> getValues(ModelElement element) {
        return List.of(getId(), getDT_ID(), getName(), getDescrip(), getDirection(), getReturn_Dimensions(),
                getPrevious_Id());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
