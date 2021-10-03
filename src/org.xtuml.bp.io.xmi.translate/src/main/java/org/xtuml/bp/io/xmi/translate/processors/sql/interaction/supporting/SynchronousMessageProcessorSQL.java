package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractSynchronousMessageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class SynchronousMessageProcessorSQL extends AbstractSynchronousMessageProcessor {

    int count = 0;

    public SynchronousMessageProcessorSQL(int count) {
        this.count = count;
    }

    @Override
    public String createSupportingElements() {
        StringJoiner supporting = new StringJoiner("");
        InformalSynchronousMessageProcessorSQL informalSynchronousMessageProcessorSQL = new InformalSynchronousMessageProcessorSQL();
        informalSynchronousMessageProcessorSQL.setModelElement(getModelElement());
        informalSynchronousMessageProcessorSQL.setKeyLetters("MSG_ISM");
        supporting.add(informalSynchronousMessageProcessorSQL.getProcessorOutput());
        return supporting.toString();
    }

    @Override
    public String getMsg_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getInformalName() {
        ModelElement messageConnector = XMITranslate.eaDiagramConnectors.get(getModelElement().getPlainAttribute("id"));
        String name = getModelElement().getName();
        if (name.equals("") && messageConnector != null) {
            name = messageConnector.getName();
            if (name.equals("")) {
                name = messageConnector.getPlainAttribute("mtLabel");
                if (name.equals("")) {
                    name = messageConnector.getPlainAttribute("mbLabel");
                }
            }
        }
        return SQLUtils.stringValue(name);
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
        return Stream.of(getMsg_ID(), getInformalName(), getDescrip(), getGuardCondition(), getResultTarget(),
                getReturnValue(), getisFormal(), getLabel(), getSequenceNumb()).collect(Collectors.toList());
    }
}
