package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractActionProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ActionProcessorSQL extends AbstractActionProcessor {

    private UUID actionId = IdProcessor.UUID();

    public UUID getActionId() {
        return actionId;
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
    public String getSuc_Pars() {
        return SQLUtils.numberValue(3);
    }

    @Override
    public String getAction_Semantics_internal() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName() + "\n\n"
                + "TODO: see about finding code from EA model (maybe trigger action)");
    }

    @Override
    public String getDialect() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream
                .of(getAct_ID(), getSM_ID(), getSuc_Pars(), getAction_Semantics_internal(), getDescrip(), getDialect())
                .collect(Collectors.toList());
    }
}
