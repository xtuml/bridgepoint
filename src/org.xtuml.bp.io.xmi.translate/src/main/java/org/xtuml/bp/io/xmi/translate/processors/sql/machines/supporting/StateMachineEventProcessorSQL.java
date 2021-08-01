package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractStateMachineEventProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class StateMachineEventProcessorSQL extends AbstractStateMachineEventProcessor {

    static int count = 1;
    static ModelElement lastElement;
    public static List<ModelElement> instances = new ArrayList<>();
    private ModelElement event;

    public StateMachineEventProcessorSQL(ModelElement event) {
        this.event = event;
        instances.add(event);
    }

    @Override
    public String getSMevt_ID() {
        return SQLUtils.idValue(event.getPlainAttribute("id"));
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
    public String getNumb() {
        if (lastElement != null && lastElement.getOwner() == getModelElement().getOwner()) {
            count++;
            lastElement = getModelElement();
        } else {
            count = 1;
            lastElement = getModelElement();
        }
        return SQLUtils.numberValue(count);
    }

    @Override
    public String getMning() {
        String name = getModelElement().getName();
        Collection<ModelElement> relations = event.getRelations("event");
        if (relations != null) {
            ModelElement namingEvent = (ModelElement) relations.toArray()[0];
            name = namingEvent.getName();
        }
        if (name.equals("")) {
            // using state as creation transition event
            name = event.getName();
        }
        return SQLUtils.stringValue(name);
    }

    @Override
    public String getIs_Lbl_U() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getUnq_Lbl() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getDrv_Lbl() {
        // let BP figure this out
        return SQLUtils.stringValue("");
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + event.getFullName());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getSMevt_ID(), getSM_ID(), getSMspd_ID(), getNumb(), getMning(), getIs_Lbl_U(), getUnq_Lbl(),
                getDrv_Lbl(), getDescrip()).collect(Collectors.toList());
    }
}
