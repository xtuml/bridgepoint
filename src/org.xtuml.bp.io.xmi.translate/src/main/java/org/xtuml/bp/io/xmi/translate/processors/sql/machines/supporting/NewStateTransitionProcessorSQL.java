package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractNewStateTransitionProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class NewStateTransitionProcessorSQL extends AbstractNewStateTransitionProcessor {

    private String targetStateId;
    private String eventId;

    public NewStateTransitionProcessorSQL(String eventId, String targetStateId) {
        this.eventId = eventId;
        this.targetStateId = targetStateId;
    }

    @Override
    public String getTrans_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getSMstt_ID() {
        return SQLUtils.idValue(targetStateId);
    }

    @Override
    public String getSMevt_ID() {
        return SQLUtils.idValue(eventId);
    }

    @Override
    public String getSMspd_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getTrans_ID(), getSM_ID(), getSMstt_ID(), getSMevt_ID(), getSMspd_ID())
                .collect(Collectors.toList());
    }
}
