package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.CantHappen_c;
import org.xtuml.bp.core.ClassAsSubtype_c;
import org.xtuml.bp.core.ClassAsSupertype_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EventIgnored_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.LocalEvent_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.MooreStateMachine_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.DefinitionContext;
import org.xtuml.bp.io.core.XtumlParser.Event_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.State_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.State_machine_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Transition_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Transition_rowContext;

public class StateMachineImportVisitor extends XtumlImportVisitor {

	public StateMachineImportVisitor(Ooaofooa modelRoot) {
		super(modelRoot);
	}

	@Override
	public NonRootModelElement visitDefinition(DefinitionContext ctx) {
		final List<NonRootModelElement> classItems = ctx.class_item().stream().map(this::visit)
				.map(NonRootModelElement.class::cast).collect(Collectors.toList());
		return classItems.stream().filter(((Predicate<NonRootModelElement>) InstanceStateMachine_c.class::isInstance)
				.or(ClassStateMachine_c.class::isInstance)).findAny().orElse(null);
	}

	@Override
	public StateMachineState_c visitState_definition(State_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;
		final StateMachine_c stateMachine = getStateMachine(modelClass, ctx.class_based != null);

		// find or create state
		final String stateName = visitName(ctx.state_name);
		final StateMachineState_c state = Optional
				.ofNullable(StateMachineState_c.StateMachineStateInstance(modelRoot,
						selected -> ((StateMachineState_c) selected).getPath()
								.equals(stateMachine.getPath() + "::" + stateName)))
				.orElseGet(() -> StateMachineState_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
						IdAssigner.NULL_UUID, "", 0, 0, stateMachine.getPath() + "::" + stateName));
		state.setName(stateName);

		// handle marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(STATE_NUM)) {
			state.setNumb(marks.get(STATE_NUM).getInteger());
		}
		if (marks.containsKey(FINAL)) {
			state.setFinal(1);
		}

		// create action home if not already created
		if (MooreActionHome_c.getOneSM_MOAHOnR511(state) == null) {
			final Action_c act = new Action_c(modelRoot);
			act.relateAcrossR515To(stateMachine);
			final ActionHome_c ah = new ActionHome_c(modelRoot);
			ah.relateAcrossR514To(act);
			final MooreActionHome_c mah = new MooreActionHome_c(modelRoot);
			mah.relateAcrossR513To(ah);
			mah.relateAcrossR511To(MooreStateMachine_c.getOneSM_MOOREOnR510(stateMachine));
			mah.relateAcrossR511To(state);
		}

		// populate description
		final Action_c act = Action_c
				.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(MooreActionHome_c.getOneSM_MOAHOnR511(state)));
		if (ctx.description() != null) {
			act.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link to state machine
		state.relateAcrossR501To(stateMachine);

		// populate action semantics
		if (ctx.action_body() != null) {
			act.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
			act.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
			act.setAction_semantics_internal(visitAction_body(ctx.action_body()));
		}

		return state;
	}

	@Override
	public StateMachineEvent_c visitEvent_declaration(Event_declarationContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;
		final StateMachine_c stateMachine = getStateMachine(modelClass, ctx.class_based != null);

		// find or create event
		final String eventName = visitName(ctx.evt_name);
		final StateMachineEvent_c evt = StateMachineEvent_c.resolveInstance(modelRoot, UUID.randomUUID(),
				IdAssigner.NULL_UUID, IdAssigner.NULL_UUID, 0, "", 0, "", "", "",
				stateMachine.getPath() + "::" + eventName);
		evt.setMning(eventName);

		// set description
		if (ctx.description() != null) {
			evt.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(EVENT_NUM)) {
			evt.setNumb(marks.get(EVENT_NUM).getInteger());
		}
		if (marks.containsKey(KEY_LETTERS)) {
			evt.setIs_lbl_u(1);
			evt.setUnq_lbl(marks.get(KEY_LETTERS).getString());
		}

		// every event starts as a local event
		final SemEvent_c semEvt = new SemEvent_c(modelRoot);
		semEvt.relateAcrossR525To(evt);
		final LocalEvent_c locEvt = new LocalEvent_c(modelRoot);
		locEvt.relateAcrossR526To(semEvt);

		// handle parameters
		if (ctx.parameter_list() != null) {
			currentRoot = evt;
			visit(ctx.parameter_list());
			currentRoot = modelClass;
		}

		// link event and data items to state machine
		Stream.of(StateMachineEventDataItem_c.getManySM_EVTDIsOnR532(evt)).forEach(stateMachine::relateAcrossR516To);
		evt.relateAcrossR502To(stateMachine);

		return evt;
	}

	@Override
	public StateMachineEventDataItem_c visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		StateMachineEventDataItem_c prevEvtdi = null;
		for (ParameterContext paramCtx : ctx.parameter()) {
			final StateMachineEventDataItem_c sm_evtdi = (StateMachineEventDataItem_c) visit(paramCtx);
			if (prevEvtdi != null) {
				prevEvtdi.relateAcrossR533ToPrecedes(sm_evtdi);
			}
			prevEvtdi = sm_evtdi;
		}
		return prevEvtdi;
	}

	@Override
	public StateMachineEventDataItem_c visitParameter(ParameterContext ctx) {
		final StateMachineEvent_c evt = (StateMachineEvent_c) currentRoot;

		// create a new parameter
		final StateMachineEventDataItem_c sm_evtdi = new StateMachineEventDataItem_c(modelRoot);
		sm_evtdi.setName(visitName(ctx.param_name));

		// link the data type
		sm_evtdi.relateAcrossR524To((DataType_c) visit(ctx.type_reference()));

		// set the array dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		sm_evtdi.setDimensions(dimString);
		final List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, sm_evtdi);
		for (int i = 0; i < dims.size(); i++) {
			sm_evtdi.Resizedimensions(i, dims.get(i), dims.size());
		}

		// link to event and state machine
		sm_evtdi.relateAcrossR532To(evt);

		return sm_evtdi;
	}

	@Override
	public NonRootModelElement visitState_machine_definition(State_machine_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;
		final StateMachine_c stateMachine = getStateMachine(modelClass, ctx.class_based != null);

		// set description
		if (ctx.description() != null) {
			stateMachine.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// verify consistent width
		if (ctx.evt_names.size() != (ctx.Divider().size() - 1)
				|| ctx.transition_row().stream().anyMatch(row -> ctx.evt_names.size() != row.end_states.size())) {
			throw new CoreImport.XtumlLoadException(
					"State/event matrix rows do not a have consistent number of columns");
		}

		// process each transition row
		for (Transition_rowContext row : ctx.transition_row()) {
			// get the start state
			final String startStateName = visitName(row.start_state_name);
			final StateMachineState_c startState = StateMachineState_c.getOneSM_STATEOnR501(stateMachine,
					selected -> ((StateMachineState_c) selected).getName().equals(startStateName));
			for (int i = 0; i < ctx.evt_names.size(); i++) {

				// get the referred to event
				final String evtPath = visitScoped_name(ctx.evt_names.get(i));
				StateMachineEvent_c refEvt = StateMachineEvent_c.getOneSM_EVTOnR502(stateMachine,
						selected -> ((StateMachineEvent_c) selected).getPath().endsWith(evtPath));
				if (refEvt == null) {
					// search for polymorphic events
					StateMachine_c[] superTypeSms = getSupertypeStateMachines(stateMachine);
					while (superTypeSms.length > 0 && refEvt == null) {
						refEvt = StateMachineEvent_c.getOneSM_EVTOnR502(superTypeSms,
								selected -> ((StateMachineEvent_c) selected)
										.Isassignabletostatemachine(stateMachine.getSm_id(), startState == null)
										&& ((StateMachineEvent_c) selected).getPath().endsWith(evtPath));
						superTypeSms = getSupertypeStateMachines(superTypeSms);
					}
				}

				// get the associated event (create non-local event if necessary)
				final StateMachineEvent_c evt = (StateMachineEvent_c) modelRoot
						.getInstanceList(StateMachineEvent_c.class)
						.get(refEvt.Getassociatedeventforstatemachine(stateMachine.getSm_id()));

				// get the end state
				final String endStateName = Optional.ofNullable(row.end_states.get(i).end_state_name)
						.map(this::visitName).orElse(null);
				final StateMachineState_c endState = StateMachineState_c.getOneSM_STATEOnR501(stateMachine,
						selected -> ((StateMachineState_c) selected).getName().equals(endStateName));

				// if the end state exists, create a transition
				if (endState != null) {
					final Transition_c txn = new Transition_c(modelRoot);
					txn.relateAcrossR506To(endState);

					// if there is no start state, it is a creation transition
					if (startState == null) {
						final CreationTransition_c crTxn = new CreationTransition_c(modelRoot);
						crTxn.relateAcrossR507To(txn);
						crTxn.relateAcrossR509To(LocalEvent_c.getOneSM_LEVTOnR526(SemEvent_c.getOneSM_SEVTOnR525(evt)));
					} else {
						final NewStateTransition_c nsTxn = new NewStateTransition_c(modelRoot);
						nsTxn.relateAcrossR507To(txn);
						final StateEventMatrixEntry_c seme = new StateEventMatrixEntry_c(modelRoot);
						seme.relateAcrossR504To(nsTxn);
						seme.relateAcrossR503To(startState);
						seme.relateAcrossR503To(SemEvent_c.getOneSM_SEVTOnR525(evt));
					}

					// create transition action
					final Action_c act = new Action_c(modelRoot);
					final ActionHome_c ah = new ActionHome_c(modelRoot);
					ah.relateAcrossR514To(act);
					final TransitionActionHome_c tah = new TransitionActionHome_c(modelRoot);
					tah.relateAcrossR513To(ah);
					tah.relateAcrossR530To(txn);
					act.relateAcrossR515To(stateMachine);

					// link transtion to state machine
					txn.relateAcrossR505To(stateMachine);

				} else {
					final StateEventMatrixEntry_c seme = new StateEventMatrixEntry_c(modelRoot);
					seme.relateAcrossR503To(startState);
					seme.relateAcrossR503To(SemEvent_c.getOneSM_SEVTOnR525(evt));
					if (row.end_states.get(i).ch != null) {
						seme.relateAcrossR504To(new CantHappen_c(modelRoot));
					} else if (row.end_states.get(i).ig != null) {
						seme.relateAcrossR504To(new EventIgnored_c(modelRoot));
					}
				}

			}
		}

		return (ctx.class_based != null) ? ClassStateMachine_c.getOneSM_ASMOnR517(stateMachine)
				: InstanceStateMachine_c.getOneSM_ISMOnR517(stateMachine);
	}

	@Override
	public Transition_c visitTransition_definition(Transition_definitionContext ctx) {
		final ModelClass_c modelClass = (ModelClass_c) currentRoot;
		final StateMachine_c stateMachine = getStateMachine(modelClass, ctx.class_based != null);

		// get the start state
		final String startStateName = visitName(ctx.start_state_name);
		final StateMachineState_c startState = StateMachineState_c.getOneSM_STATEOnR501(stateMachine,
				selected -> ((StateMachineState_c) selected).getName().equals(startStateName));

		// get the referred to event
		final String evtPath = visitScoped_name(ctx.evt_name);
		StateMachineEvent_c refEvt = StateMachineEvent_c.getOneSM_EVTOnR502(stateMachine,
				selected -> ((StateMachineEvent_c) selected).getPath().endsWith(evtPath));
		if (refEvt == null) {
			// search for polymorphic events
			StateMachine_c[] superTypeSms = getSupertypeStateMachines(stateMachine);
			while (superTypeSms.length > 0 && refEvt == null) {
				refEvt = StateMachineEvent_c
						.getOneSM_EVTOnR502(superTypeSms,
								selected -> ((StateMachineEvent_c) selected)
										.Isassignabletostatemachine(stateMachine.getSm_id(), startState == null)
										&& ((StateMachineEvent_c) selected).getPath().endsWith(evtPath));
				superTypeSms = getSupertypeStateMachines(superTypeSms);
			}
		}

		// get the associated event (create non-local event if necessary)
		final StateMachineEvent_c evt = (StateMachineEvent_c) modelRoot.getInstanceList(StateMachineEvent_c.class)
				.get(refEvt.Getassociatedeventforstatemachine(stateMachine.getSm_id()));

		// try to find a new state transition
		Transition_c txn = Transition_c.getOneSM_TXNOnR507(
				NewStateTransition_c.getOneSM_NSTXNOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(startState,
						selected -> ((StateEventMatrixEntry_c) selected).getSmevt_id().equals(evt.getSmevt_id()))));
		if (txn == null) {
			// look for creation transition
			txn = Transition_c.getOneSM_TXNOnR507(CreationTransition_c
					.getOneSM_CRTXNOnR509(LocalEvent_c.getOneSM_LEVTOnR526(SemEvent_c.getOneSM_SEVTOnR525(evt))));
		}
		final Action_c act = Action_c
				.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(TransitionActionHome_c.getOneSM_TAHOnR530(txn)));

		// populate description
		if (ctx.description() != null) {
			act.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// populate action semantics
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		act.setDialect(Actiondialect_c.oal); // TODO set dialect to OAL
		act.setSuc_pars(marks.containsKey(NOPARSE) ? Parsestatus_c.doNotParse : Parsestatus_c.parseInitial);
		act.setAction_semantics_internal(visitAction_body(ctx.action_body()));

		return txn;
	}

	private StateMachine_c getStateMachine(ModelClass_c modelClass, boolean isClassBased) {

		// find or create state machine
		final StateMachine_c stateMachine = StateMachine_c.resolveInstance(modelRoot, UUID.randomUUID(), "", 0,
				modelClass.getPath() + "::" + (isClassBased ? "Class State Machine" : "Instance State Machine"));

		// link to class if not already
		if (isClassBased && ClassStateMachine_c.getOneSM_ASMOnR517(stateMachine) == null) {
			final ClassStateMachine_c csm = new ClassStateMachine_c(modelRoot);
			csm.relateAcrossR517To(stateMachine);
			csm.relateAcrossR519To(modelClass);
		} else if (!isClassBased && InstanceStateMachine_c.getOneSM_ISMOnR517(stateMachine) == null) {
			final InstanceStateMachine_c ism = new InstanceStateMachine_c(modelRoot);
			ism.relateAcrossR517To(stateMachine);
			ism.relateAcrossR518To(modelClass);
		}

		return stateMachine;
	}

	private StateMachine_c[] getSupertypeStateMachines(StateMachine_c sm) {
		return getSupertypeStateMachines(new StateMachine_c[] { sm });
	}

	private StateMachine_c[] getSupertypeStateMachines(StateMachine_c[] sms) {
		return StateMachine_c.getManySM_SMsOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(
				ModelClass_c.getManyO_OBJsOnR201(ClassInAssociation_c.getManyR_OIRsOnR203(ReferredToClassInAssoc_c
						.getManyR_RTOsOnR204(ClassAsSupertype_c.getManyR_SUPERsOnR212(SubtypeSupertypeAssociation_c
								.getManyR_SUBSUPsOnR213(ClassAsSubtype_c.getManyR_SUBsOnR205(
										ReferringClassInAssoc_c.getManyR_RGOsOnR203(ClassInAssociation_c
												.getManyR_OIRsOnR201(ModelClass_c.getManyO_OBJsOnR518(
														InstanceStateMachine_c.getManySM_ISMsOnR517(sms))))))))))));
	}

}
