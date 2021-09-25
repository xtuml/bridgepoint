package org.xtuml.bp.io.xmi.translate.processors.sql.interaction;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLifespanProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.ClassParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.ComponentParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.InteractionParticipantProcessorSQL;

public class LifespanProcessorSQL extends AbstractLifespanProcessor {

    private String packageId;
    private ModelElement covered;

    public LifespanProcessorSQL(String packageId, ModelElement covered) {
        this.packageId = packageId;
        this.covered = covered;
    }

    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String createSupportingElements() {
        /**
         * Create the source element, there is not a good element to directly create
         * them with in the EA mm.
         */
        StringJoiner supportingElements = new StringJoiner("");
        ModelElement represents = getSourceParticipantPart();
        if (represents != null) {
            ComponentParticipantProcessorSQL componentParticipantProcessorSQL = new ComponentParticipantProcessorSQL(
                    getModelElement().getName(), packageId);
            componentParticipantProcessorSQL.setModelElement(represents);
            componentParticipantProcessorSQL.setKeyLetters("SQ_COP");
            supportingElements.add(componentParticipantProcessorSQL.getProcessorOutput());
        } else {
            // EA supports elements that xtuml does not (like a provided interface)
            // we will use a Class Participant
            ClassParticipantProcessorSQL classParticipantProcessorSQL = new ClassParticipantProcessorSQL(packageId,
                    false);
            if (getModelElement().getType().getName().equals("lifeline")) {
                classParticipantProcessorSQL.setModelElement(getModelElement().getRefAttribute("represents"));
            } else {
                classParticipantProcessorSQL.setModelElement(getModelElement().getRefAttribute("covered"));
            }
            classParticipantProcessorSQL.setKeyLetters("SQ_CP");
            supportingElements.add(classParticipantProcessorSQL.getProcessorOutput());
        }
        return supportingElements.toString();
    }

    private ModelElement getSourceParticipantPart() {
        ModelElement lifelineElement = getModelElement();
        if (getModelElement().getType().getName().equals("occurrencespec")) {
            lifelineElement = getModelElement().getRefAttribute("covered");
        }
        if (lifelineElement.getType().hasAttribute("represents")) {
            ModelElement representsProperty = lifelineElement.getRefAttribute("represents");
            if (representsProperty != null) {
                ModelElement represents = representsProperty.getRefAttribute("propertytype");
                if (represents != null) {
                    return represents;
                } else {
                    return lifelineElement;
                }
            }
        }
        return null;
    }

    @Override
    public String getSource_Part_ID() {
        ModelElement represents = getSourceParticipantPart();
        if (represents != null) {
            return SQLUtils
                    .idValue(represents.getPlainAttribute("id") + getModelElement().getOwner().getPlainAttribute("id")
                            + InteractionParticipantProcessorSQL.PARTICIPANT_SUFFIX);
        }
        return SQLUtils.idValue(IdProcessor.NULL_ID);
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
