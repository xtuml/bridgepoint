package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;

public class DataTypeProcessorSQL extends AbstractDataTypeProcessor {

    private String id;

    public DataTypeProcessorSQL() {

    }

    public DataTypeProcessorSQL(String id) {
        this.id = id;
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public String createSupportingElements() {
        if (this.id == null) {
            String dtId = IdProcessor.getId(getModelElement().getName()) != null
                    ? IdProcessor.getId(getModelElement().getName())
                    : null;
            PackageableElementProcessorSQL peProcessor = new PackageableElementProcessorSQL(dtId);
            peProcessor.setKeyLetters("PE_PE");
            peProcessor.setModelElement(getModelElement());
            UserDataTypeProcessorSQL udtProcessor = new UserDataTypeProcessorSQL(dtId);
            udtProcessor.setKeyLetters("S_UDT");
            udtProcessor.setModelElement(getModelElement());
            return SQLUtils.getInsertStatement(peProcessor, getModelElement()) + "\n"
                    + SQLUtils.getInsertStatement(udtProcessor, getModelElement());
        }
        return "";
    }

    @Override
    public String getDT_ID() {
        if (this.id != null) {
            return SQLUtils.idValue(this.id);
        }
        // see if a references has already created an id
        String id = IdProcessor.getId(getModelElement().getName());
        if (id != null) {
            return SQLUtils.preprocessedIdValue(id);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getDom_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Model: " + getModelElement().getFullName());
    }

    @Override
    public String getDefaultValue() {
        return SQLUtils.stringValue("");
    }

    @Override
    public List<String> getValues(ModelElement modelElement) {
        return List.of(getDT_ID(), getDom_ID(), getName(), getDescrip(), getDefaultValue());
    }
}
