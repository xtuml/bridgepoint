package org.xtuml.bp.io.xmi.translate.processors.sql.classes;

import java.util.Collection;
import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractOperationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class OperationProcessorSQL extends AbstractOperationProcessor {

    static ModelElement lastElement = null;
    static int count = 1;
    static ModelElement owner;

    @Override
    public String getTfr_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
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
    public String getDT_ID() {
        String refId = getModelElement().getPlainAttribute("type");
        if (refId.equals("")) {
            refId = IdProcessor.NULL_ID;
            /** UML2 may treat return type as an owned parameter */
            Collection<ModelElement> parameters = getModelElement().getOwnedElements();
            if (parameters != null) {
                for (ModelElement parameter : parameters) {
                    if (parameter.getName().equals("return")) {
                        ModelElement refAttribute = parameter.getRefAttribute("parametertype");
                        if (refAttribute != null) {
                            refId = refAttribute.getPlainAttribute("id");
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
            }
        }
        return SQLUtils.idValue(refId);
    }

    @Override
    public String getInstance_Based() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getAction_Semantics_internal() {
        return SQLUtils.stringValue("// TODO see if any code can be copied");
    }

    @Override
    public String getSuc_Pars() {
        return SQLUtils.numberValue(3);
    }

    @Override
    public String getReturn_Dimensions() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getPrevious_Tfr_ID() {
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
    public String getDialect() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getNumb() {
        if (owner != null && owner == getModelElement().getOwner()) {
            count++;
        } else {
            count = 1;
            owner = getModelElement().getOwner();
        }
        return SQLUtils.numberValue(count);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getTfr_ID(), getObj_ID(), getName(), getDescrip(), getDT_ID(), getInstance_Based(),
                getAction_Semantics_internal(), getSuc_Pars(), getReturn_Dimensions(), getPrevious_Tfr_ID(),
                getDialect(), getNumb());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
