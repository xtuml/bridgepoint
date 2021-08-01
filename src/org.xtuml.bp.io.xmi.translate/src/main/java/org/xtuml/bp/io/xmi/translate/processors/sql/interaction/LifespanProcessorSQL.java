package org.xtuml.bp.io.xmi.translate.processors.sql.interaction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLifespanProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class LifespanProcessorSQL extends AbstractLifespanProcessor {
    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSource_Part_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getDestroyed() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getSource_Part_ID(), getDescrip(), getDestroyed()).collect(Collectors.toList());
    }
}
