package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInteractionParticipantProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class InteractionParticipantProcessorSQL extends AbstractInteractionParticipantProcessor {
    public static final String PARTICIPANT_SUFFIX = "_PART";
    private String id;

    public InteractionParticipantProcessorSQL() {
    }

    public InteractionParticipantProcessorSQL(String id) {
        this.id = id;
    }

    @Override
    public String getPart_ID() {
        if (id != null) {
            return SQLUtils.idValue(id);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSequence_Package_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getSequence_Package_ID()).collect(Collectors.toList());
    }
}
