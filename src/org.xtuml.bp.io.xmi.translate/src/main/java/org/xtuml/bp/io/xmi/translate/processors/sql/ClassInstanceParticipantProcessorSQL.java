package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassInstanceParticipantProcessor;

public class ClassInstanceParticipantProcessorSQL extends AbstractClassInstanceParticipantProcessor {

    private ModelElement classifier;

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        Collection<?> classifiers = getModelElement().getSetAttribute("classifier");
        classifier = ((ModelElement) classifiers.iterator().next());
    }

    @Override
    public void postprocess(ModelElement element, String keyletters) {
        classifier = null;
    }

    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(classifier.getPlainAttribute("id"));
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(classifier.getName());
    }

    @Override
    public String getInformalClassName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getLabel() {
        return SQLUtils.stringValue(getModelElement().getName() + " : " + classifier.getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getisFormal() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getObj_ID(), getName(), getInformalClassName(), getLabel(), getDescrip(),
                getisFormal()).collect(Collectors.toList());
    }
}
