package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInformalSynchronousMessageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class InformalSynchronousMessageProcessorSQL extends AbstractInformalSynchronousMessageProcessor {
    @Override
    public String getMsg_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getMsg_ID());
    }
}
