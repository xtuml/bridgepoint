package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLocalEventProcessor;

public class LocalEventProcessorSQL extends AbstractLocalEventProcessor {

    private String eventId;

    public LocalEventProcessorSQL(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getSMevt_ID() {
        return SQLUtils.idValue(eventId);
    }

    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
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
        return Stream.of(getSMevt_ID(), getSM_ID(), getSMspd_ID()).collect(Collectors.toList());
    }
}
