package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLinkedAssociationProcessor;

public class LinkedAssociationProcessorSQL extends AbstractLinkedAssociationProcessor {

    private String assocId;

    public LinkedAssociationProcessorSQL(String assocId) {
        this.assocId = assocId;
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
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
