package org.xtuml.bp.io.xmi.translate.processors.sql.machines;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractStateMachineStateProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class StateMachineStateProcessorSQL extends AbstractStateMachineStateProcessor {

    static int number = 0;

    @Override
    public String getSMstt_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getSM_ID() {
        // use grand-parent, as parent is a region that is on supported
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getSMspd_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getNumb() {
        return SQLUtils.numberValue(++number);
    }

    @Override
    public String getFinal() {
        String kind = getModelElement().getPlainAttribute("kind");
        return SQLUtils.numberValue(kind.equals("final") ? 1 : 0);
    }

    @Override
    public IgnoreType ignoreTranslation() {
        String kind = getModelElement().getPlainAttribute("kind");
        return kind.equals("initial") ? IgnoreType.HANDLED : IgnoreType.NOT_IGNORED;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getSMstt_ID(), getSM_ID(), getSMspd_ID(), getName(), getNumb(), getFinal())
                .collect(Collectors.toList());
    }
}
