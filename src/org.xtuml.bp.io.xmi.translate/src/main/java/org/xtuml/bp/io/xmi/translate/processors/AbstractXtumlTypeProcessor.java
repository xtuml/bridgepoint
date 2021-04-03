package org.xtuml.bp.io.xmi.translate.processors;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public abstract class AbstractXtumlTypeProcessor implements XtumlTypeProcessor {

    private String keyLetters;
    private ModelElement modelElement;

    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public void setKeyLetters(String keyLetters) {
        this.keyLetters = keyLetters;
    }

    @Override
    public ModelElement getModelElement() {
        return modelElement;
    }

    @Override
    public void setModelElement(ModelElement element) {
        this.modelElement = element;
    }

    public boolean handlesPackageableElement() {
        return false;
    };

    @Override
    public String process(ModelElement element, String keyLetters) {
        setModelElement(element);
        setKeyLetters(keyLetters);
        StringBuilder inserts = new StringBuilder();
        inserts.append(SQLUtils.getInsertStatement(this, getModelElement()));
        if (!handlesPackageableElement() && SQLUtils.requiresPackageableElement(getModelElement())) {
            inserts.append(SQLUtils.createPackageableElement(getModelElement()));
        }
        inserts.append(createSupportingElements());
        return inserts.toString();
    }

    public abstract List<String> getValues(ModelElement modelElement);
}
