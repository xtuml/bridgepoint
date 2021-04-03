package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractModelClassProcessor;

public class ModelClassProcessorSQL extends AbstractModelClassProcessor {
    static int count = 1;
    static ModelElement lastElement;

    @Override
    public String createSupportingElements() {
        if (getModelElement().getName().equals("")) {
            return "";
        }
        InstanceReferenceDataTypeProcessorSQL irdtProcessor = new InstanceReferenceDataTypeProcessorSQL(
                getModelElement().getName().trim());
        irdtProcessor.setKeyLetters("S_IRDT");
        irdtProcessor.setModelElement(getModelElement());
        DataTypeProcessorSQL dtProcessor = new DataTypeProcessorSQL(getModelElement().getName().trim());
        dtProcessor.setKeyLetters("S_DT");
        dtProcessor.setModelElement(getModelElement());
        return SQLUtils.getInsertStatement(dtProcessor, getModelElement()) + "\n"
                + SQLUtils.getInsertStatement(irdtProcessor, getModelElement());
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName().equals("")
                ? getModelElement().getPlainAttribute("id") + " - Potential Imported Class"
                : getModelElement().getName());
    }

    @Override
    public String getNumb() {
        if (lastElement != null && lastElement.getOwner() == getModelElement().getOwner()) {
            count++;
        } else {
            count = 1;
        }
        return SQLUtils.numberValue(count);
    }

    @Override
    public String getKey_Lett() {
        return SQLUtils.stringValue(SQLUtils.convertToPascalCase(getModelElement().getName()));
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSS_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getObj_ID(), getName(), getNumb(), getKey_Lett(), getDescrip(), getSS_ID());
    }
}
