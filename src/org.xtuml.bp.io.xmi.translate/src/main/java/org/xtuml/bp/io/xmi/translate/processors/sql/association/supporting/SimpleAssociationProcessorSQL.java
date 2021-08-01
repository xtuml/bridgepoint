package org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractSimpleAssociationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class SimpleAssociationProcessorSQL extends AbstractSimpleAssociationProcessor {
    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public boolean handlesPackageableElement() {
        // tell the processing system we handle this, to
        // ignore creating it here as its created by
        // the association processor
        return true;
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getRel_ID());
    }
}
