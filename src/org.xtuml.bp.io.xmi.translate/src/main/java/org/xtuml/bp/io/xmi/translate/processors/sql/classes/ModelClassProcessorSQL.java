package org.xtuml.bp.io.xmi.translate.processors.sql.classes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractModelClassProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.types.DataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.types.supporting.InstanceReferenceDataTypeProcessorSQL;

public class ModelClassProcessorSQL extends AbstractModelClassProcessor {
    static int count = 1;
    static ModelElement lastElement;
    static Set<String> createdClasses = new HashSet<>();

    @Override
    public String createSupportingElements() {
        if (getModelElement().getName().equals("")) {
            return "";
        }
        InstanceReferenceDataTypeProcessorSQL irdtProcessor = new InstanceReferenceDataTypeProcessorSQL(
                getModelElement().getName().trim(), getModelElement().getOwner().getPlainAttribute("id"), 3);
        irdtProcessor.setKeyLetters("S_IRDT");
        irdtProcessor.setModelElement(getModelElement());
        DataTypeProcessorSQL dtProcessor = new DataTypeProcessorSQL(getModelElement().getName().trim());
        dtProcessor.setKeyLetters("S_DT");
        dtProcessor.setModelElement(getModelElement());
        return SQLUtils.getInsertStatement(dtProcessor, getModelElement()) + "\n"
                + SQLUtils.getProcessorOutput(irdtProcessor);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName().equals("")
                ? "UML Classifier ? Container with swimlane - " + getModelElement().getPlainAttribute("id")
                : getModelElement().getName().trim());
    }

    @Override
    public String getNumb() {
        if (lastElement != null && lastElement.getOwner() == getModelElement().getOwner()) {
            count++;
            lastElement = getModelElement();
        } else {
            count = 1;
            lastElement = getModelElement();
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
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getSS_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getObj_ID(), getName(), getNumb(), getKey_Lett(), getDescrip(), getSS_ID());
    }

    @Override
    public IgnoreType ignoreTranslation() {
        if (getModelElement().getName().equals("")) {
            // these are nested containers, we do not need them
            // the actual elements (seuqence, component diagram) are
            // handled differently in BP
            return IgnoreType.HANDLED;
        }
        return IgnoreType.NOT_IGNORED;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
