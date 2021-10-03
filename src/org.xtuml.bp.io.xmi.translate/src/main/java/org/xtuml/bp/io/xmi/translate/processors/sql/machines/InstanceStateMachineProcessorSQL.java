package org.xtuml.bp.io.xmi.translate.processors.sql.machines;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInstanceStateMachineProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.impl.MooreStateMachineProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting.StateMachineProcessorSQL;

import com.sdmetrics.model.ModelElement;

public class InstanceStateMachineProcessorSQL extends AbstractInstanceStateMachineProcessor {
	@Override
	public String getSM_ID() {
		return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
	}

	@Override
	public String getObj_ID() {
		return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
	}

	@Override
	public String createSupportingElements() {
		StringBuilder supporting = new StringBuilder();
		// Need to create SM_SM
		StateMachineProcessorSQL smProcessor = new StateMachineProcessorSQL();
		smProcessor.setModelElement(getModelElement());
		smProcessor.setKeyLetters("SM_SM");
		MooreStateMachineProcessorSQL mooreProcessor = new MooreStateMachineProcessorSQL();
		mooreProcessor.setKeyLetters("SM_MOORE");
		mooreProcessor.setModelElement(getModelElement());
		return supporting.append(smProcessor.getProcessorOutput()).append(mooreProcessor.getProcessorOutput())
				.toString();
	}

	@Override
	public String getProcessorOutput() {
		return SQLUtils.getProcessorOutput(this);
	}

	@Override
	public List<String> getValues(ModelElement element) {
		return Stream.of(getSM_ID(), getObj_ID()).collect(Collectors.toList());
	}
}
