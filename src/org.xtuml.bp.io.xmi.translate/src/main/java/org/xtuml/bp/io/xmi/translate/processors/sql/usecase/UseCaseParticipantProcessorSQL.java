package org.xtuml.bp.io.xmi.translate.processors.sql.usecase;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractUseCaseParticipantProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.InteractionParticipantProcessorSQL;

public class UseCaseParticipantProcessorSQL extends AbstractUseCaseParticipantProcessor {
    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Full name: " + getModelElement().getFullName());
    }

    @Override
    public String createSupportingElements() {
        // create Interaction Participant
        InteractionParticipantProcessorSQL ipProcessor = new InteractionParticipantProcessorSQL();
        ipProcessor.setModelElement(getModelElement());
        ipProcessor.setKeyLetters("SQ_P");
        return SQLUtils.getInsertStatement(ipProcessor, getModelElement());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getName(), getDescrip()).collect(Collectors.toList());
    }
}
