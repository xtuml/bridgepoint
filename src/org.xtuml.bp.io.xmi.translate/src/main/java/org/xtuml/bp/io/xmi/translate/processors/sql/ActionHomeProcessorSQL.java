package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractActionHomeProcessor;

public class ActionHomeProcessorSQL extends AbstractActionHomeProcessor {

    private UUID actionId;

    public ActionHomeProcessorSQL(UUID actionId) {
        this.actionId = actionId;
    }

    @Override
    public String getAct_ID() {
        return SQLUtils.idValue(actionId.toString());
    }

    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getAct_ID(), getSM_ID()).collect(Collectors.toList());
    }
}
