package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.List;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractMooreStateMachineProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

import com.sdmetrics.model.ModelElement;

public class MooreStateMachineProcessorSQL extends AbstractMooreStateMachineProcessor {
    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getSM_ID());
    }
}
