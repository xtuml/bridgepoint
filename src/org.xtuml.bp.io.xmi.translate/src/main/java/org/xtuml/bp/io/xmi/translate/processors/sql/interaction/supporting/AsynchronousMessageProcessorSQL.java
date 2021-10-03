package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractAsynchronousMessageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class AsynchronousMessageProcessorSQL extends AbstractAsynchronousMessageProcessor {

    int count = 0;

    public AsynchronousMessageProcessorSQL(int count) {
        this.count = count;
    }

    @Override
    public String createSupportingElements() {
        StringJoiner supporting = new StringJoiner("");
        InformalAsynchronousMessageProcessorSQL informalAsynchronousMessageProcessorSQL = new InformalAsynchronousMessageProcessorSQL();
        informalAsynchronousMessageProcessorSQL.setModelElement(getModelElement());
        informalAsynchronousMessageProcessorSQL.setKeyLetters("MSG_IAM");
        supporting.add(informalAsynchronousMessageProcessorSQL.getProcessorOutput());
        return supporting.toString();
    }

    @Override
    public String getMsg_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getInformalName() {
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
    public String getDurationObservation() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getDurationConstraint() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getisFormal() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getLabel() {
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
        return Stream
                .of(getMsg_ID(), getInformalName(), getDescrip(), getGuardCondition(), getDurationObservation(),
                        getDurationConstraint(), getisFormal(), getLabel(), getSequenceNumb())
                .collect(Collectors.toList());
    }
}
