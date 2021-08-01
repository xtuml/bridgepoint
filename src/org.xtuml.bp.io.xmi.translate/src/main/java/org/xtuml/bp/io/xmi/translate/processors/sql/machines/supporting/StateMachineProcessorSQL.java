package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractStateMachineProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class StateMachineProcessorSQL extends AbstractStateMachineProcessor {
    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getConfig_ID() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getSM_ID(), getDescrip(), getConfig_ID()).collect(Collectors.toList());
    }
}
