package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractComponentParticipantProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class ComponentParticipantProcessorSQL extends AbstractComponentParticipantProcessor {

    private String interactionId;
    private String name;

    public ComponentParticipantProcessorSQL(String name, String interactionId) {
        this.name = name;
        this.interactionId = interactionId;
    }

    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getId(), getKeyLetters());
    }

    public String getId() {
        String id = getModelElement().getPlainAttribute("id") + interactionId
                + InteractionParticipantProcessorSQL.PARTICIPANT_SUFFIX;
        return id;
    }

    @Override
    public String createSupportingElements() {
        StringJoiner supportingElements = new StringJoiner("");
        InteractionParticipantProcessorSQL interactionParticipantProcessorSQL = new InteractionParticipantProcessorSQL(
                getId());
        interactionParticipantProcessorSQL.setKeyLetters("SQ_P");
        supportingElements.add(interactionParticipantProcessorSQL.getProcessorOutput());
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(
                IdProcessor.process(getId()), interactionId, 14);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        supportingElements.add(packageableElementProcessorSQL.getProcessorOutput());
        return supportingElements.toString();
    }

    @Override
    public String getComponent_Id() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getLabel() {
        // derived, return empty string
        return SQLUtils.stringValue("");
    }

    @Override
    public String getInformalComponentName() {
        return name != null ? SQLUtils.stringValue(name) : SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getisFormal() {
        return getModelElement().getType().getName().equals("property") ? SQLUtils.booleanValue(false)
                : SQLUtils.booleanValue(true);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getComponent_Id(), getLabel(), getInformalComponentName(), getDescrip(),
                getisFormal()).collect(Collectors.toList());
    }
}
