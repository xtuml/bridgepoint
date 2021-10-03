package org.xtuml.bp.io.xmi.translate.processors.sql.machines.supporting;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractMooreActionHomeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

import com.sdmetrics.model.ModelElement;

public class MooreActionHomeProcessorSQL extends AbstractMooreActionHomeProcessor {
	
    private UUID actionId;

	public MooreActionHomeProcessorSQL(UUID actionId) {
    	this.actionId = actionId;
	}
    
	@Override
    public String getAct_ID() {
		return SQLUtils.idValue(actionId.toString());
    }
    @Override
    public String getSM_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }
    @Override
    public String getSMstt_ID() {
    	return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(
          getAct_ID()
          , getSM_ID()
          , getSMstt_ID()
        ).collect(Collectors.toList());
    }
}
