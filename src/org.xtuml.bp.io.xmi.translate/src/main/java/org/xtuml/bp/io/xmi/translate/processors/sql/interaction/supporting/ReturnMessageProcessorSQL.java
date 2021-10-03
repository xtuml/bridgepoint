package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractReturnMessageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ReturnMessageProcessorSQL extends AbstractReturnMessageProcessor {

    int count = 0;

    public ReturnMessageProcessorSQL(int count) {
        this.count = count;
    }

    @Override
    public String getMsg_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(String.valueOf(count));
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getGuardCondition() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getResultTarget() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getReturnValue() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getSequenceNumb() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getMsg_ID(), getName(), getDescrip(), getGuardCondition(), getResultTarget(), getReturnValue(),
                getSequenceNumb()).collect(Collectors.toList());
    }
}
