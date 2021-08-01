package org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractSubtypeSupertypeAssociationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class SubtypeSupertypeAssociationProcessorSQL extends AbstractSubtypeSupertypeAssociationProcessor {

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getRel_ID());
    }
}
