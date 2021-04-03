package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractComponentProcessor;

public class ComponentProcessorSQL extends AbstractComponentProcessor {
    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getPackage_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getNestedComponent_Id() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element " + getModelElement().getFullName());
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getRoot_Package_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getisRealized() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getRealized_Class_Path() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getKey_Lett() {
        return SQLUtils.stringValue(SQLUtils.convertToPascalCase(getModelElement().getName()));
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getId(), getPackage_ID(), getNestedComponent_Id(), getName(), getDescrip(), getMult(),
                getRoot_Package_ID(), getisRealized(), getRealized_Class_Path(), getKey_Lett());
    }
}
