package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractTransitionProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectionInformation;

public class TransitionProcessorSQL extends AbstractTransitionProcessor {
    @Override
    public String getTrans_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getSMstt_ID() {
        ModelElement targetState = getModelElement().getRefAttribute("transtarget");
        return SQLUtils.idValue(targetState.getPlainAttribute("id"));
    }

    @Override
    public String getSMspd_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String createSupportingElements() {
        StringBuilder children = new StringBuilder();
        ModelElement sourceState = getModelElement().getRefAttribute("transsource");
        ModelElement targetState = getModelElement().getRefAttribute("transtarget");
        /**
         * Since we have all of the information at this time go ahead and create events
         * as well
         */
        Collection<?> triggers = getModelElement().getSetAttribute("triggers");
        // create transitions are treated as a type of state with what appears
        // to be no modeled trigger
        ModelElement event = null;
        if (triggers.size() > 0) {
            // we only support one trigger at this time
            ModelElement trigger = (ModelElement) triggers.toArray()[0];
            event = trigger.getRefAttribute("event");
            NewStateTransitionProcessorSQL newStateTransitionProcessorSQL = new NewStateTransitionProcessorSQL(
                    event.getPlainAttribute("id"), sourceState.getPlainAttribute("id"));
            newStateTransitionProcessorSQL.setKeyLetters("SM_NSTXN");
            newStateTransitionProcessorSQL.setModelElement(getModelElement());
            children.append(newStateTransitionProcessorSQL.getProcessorOutput());
            StateEventMatrixEntryProcessorSQL stateEventMatrixEntryProcessorSQL = new StateEventMatrixEntryProcessorSQL(
                    event.getPlainAttribute("id"), sourceState.getPlainAttribute("id"));
            stateEventMatrixEntryProcessorSQL.setKeyLetters("SM_SEME");
            stateEventMatrixEntryProcessorSQL.setModelElement(getModelElement());
            children.append(stateEventMatrixEntryProcessorSQL.getProcessorOutput());
            // setup for graphics later, note we are considering the first as the start
            ConnectionInformation information = new ConnectionInformation(getModelElement().getPlainAttribute("id"),
                    sourceState.getPlainAttribute("id"), 42, getModelElement().getPlainAttribute("id"));
            information.setEndEle(targetState.getPlainAttribute("id"));
        } else {
            // treat the source state as the event
            event = sourceState;
            CreationTransitionProcessorSQL creationTransitionProcessorSQL = new CreationTransitionProcessorSQL(
                    event.getPlainAttribute("id"));
            creationTransitionProcessorSQL.setKeyLetters("SM_CRTXN");
            creationTransitionProcessorSQL.setModelElement(getModelElement());
            children.append(creationTransitionProcessorSQL.getProcessorOutput());
            // setup for graphics later, note we are considering the first as the start
            ConnectionInformation information = new ConnectionInformation(getModelElement().getPlainAttribute("id"),
                    targetState.getPlainAttribute("id"), 49, getModelElement().getPlainAttribute("id"));
            information.setEndsOnWs(true);
            information.setEndEle(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
        }
        if (!StateMachineEventProcessorSQL.instances.contains(event)) {
            StateMachineEventProcessorSQL stateMachineEventProcessorSQL = new StateMachineEventProcessorSQL(event);
            stateMachineEventProcessorSQL.setKeyLetters("SM_EVT");
            stateMachineEventProcessorSQL.setModelElement(getModelElement());
            children.append(stateMachineEventProcessorSQL.getProcessorOutput());
            LocalEventProcessorSQL localEventProcessorSQL = new LocalEventProcessorSQL(event.getPlainAttribute("id"));
            localEventProcessorSQL.setKeyLetters("SM_LEVT");
            localEventProcessorSQL.setModelElement(getModelElement());
            children.append(localEventProcessorSQL.getProcessorOutput());
            SemEventProcessorSQL semEventProcessorSQL = new SemEventProcessorSQL(event.getPlainAttribute("id"));
            semEventProcessorSQL.setKeyLetters("SM_SEVT");
            semEventProcessorSQL.setModelElement(getModelElement());
            children.append(semEventProcessorSQL.getProcessorOutput());
        }
        ActionProcessorSQL actionProcessorSQL = new ActionProcessorSQL();
        actionProcessorSQL.setKeyLetters("SM_ACT");
        actionProcessorSQL.setModelElement(getModelElement());
        children.append(actionProcessorSQL.getProcessorOutput());
        ActionHomeProcessorSQL actionHomeProcessorSQL = new ActionHomeProcessorSQL(actionProcessorSQL.getActionId());
        actionHomeProcessorSQL.setKeyLetters("SM_AH");
        actionHomeProcessorSQL.setModelElement(getModelElement());
        children.append(actionHomeProcessorSQL.getProcessorOutput());
        TransitionActionHomeProcessorSQL transitionActionHomeProcessorSQL = new TransitionActionHomeProcessorSQL(
                actionProcessorSQL.getActionId());
        transitionActionHomeProcessorSQL.setKeyLetters("SM_TAH");
        transitionActionHomeProcessorSQL.setModelElement(getModelElement());
        children.append(transitionActionHomeProcessorSQL.getProcessorOutput());
        return children.toString();
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getTrans_ID(), getSM_ID(), getSMstt_ID(), getSMspd_ID()).collect(Collectors.toList());
    }
}
