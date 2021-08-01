package org.xtuml.bp.io.xmi.translate.processors.sql.components;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractProvisionProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.components.supporting.InterfaceReferenceProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.components.supporting.PortProcessorSQL;

public class ProvisionProcessorSQL extends AbstractProvisionProcessor {
    @Override
    public String getProvision_Id() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getInformalName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Full name: " + getModelElement().getFullName());
    }

    @Override
    public String getpathFromComponent() {
        // let bridgepoint figure this out
        return SQLUtils.stringValue("");
    }

    @Override
    public String createSupportingElements() {
        PortProcessorSQL portProcessor = new PortProcessorSQL(getModelElement().getOwner().getPlainAttribute("id"));
        portProcessor.setKeyLetters("C_PO");
        InterfaceReferenceProcessorSQL irProcessor = new InterfaceReferenceProcessorSQL(portProcessor.getId(),
                getModelElement().getPlainAttribute("interfaceid"));
        irProcessor.setModelElement(getModelElement());
        irProcessor.setKeyLetters("C_IR");
        return SQLUtils.getInsertStatement(portProcessor, getModelElement()) + "\n"
                + SQLUtils.getInsertStatement(irProcessor, getModelElement());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getProvision_Id(), getName(), getInformalName(), getDescrip(), getpathFromComponent())
                .collect(Collectors.toList());
    }
}
