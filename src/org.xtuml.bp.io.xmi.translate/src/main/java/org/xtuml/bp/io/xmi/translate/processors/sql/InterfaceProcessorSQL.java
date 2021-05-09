package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInterfaceProcessor;

public class InterfaceProcessorSQL extends AbstractInterfaceProcessor {
    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getPackage_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
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
    public List<String> getValues(ModelElement element) {
        return List.of(getId(), getPackage_ID(), getName(), getDescrip());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
