package org.xtuml.bp.io.xmi.translate.processors.sql.components.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractExecutablePropertyProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ExecutablePropertyProcessorSQL extends AbstractExecutablePropertyProcessor {
    static int count = 1;
    static ModelElement owner;

    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getInterface_Id() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getDirection() {
        return SQLUtils.numberValue(1);
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
        return List.of(getId(), getInterface_Id(), getDirection(), getName(), getDescrip(), getNumb());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
