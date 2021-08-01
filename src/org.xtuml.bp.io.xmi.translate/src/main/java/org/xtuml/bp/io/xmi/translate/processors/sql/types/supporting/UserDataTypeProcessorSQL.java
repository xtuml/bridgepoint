package org.xtuml.bp.io.xmi.translate.processors.sql.types.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractUserDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class UserDataTypeProcessorSQL extends AbstractUserDataTypeProcessor {

    private String id;

    public UserDataTypeProcessorSQL() {

    }

    public UserDataTypeProcessorSQL(String id) {
        this.id = id;
    }

    @Override
    public String getDT_ID() {
        if (this.id != null) {
            return SQLUtils.preprocessedIdValue(this.id);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getCDT_DT_ID() {
        return SQLUtils.idValue(IdProcessor.getIdForType("same_as<Base_Attribute>"));
    }

    @Override
    public String getGen_Type() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getDefinition() {
        return SQLUtils.stringValue("");
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getDT_ID(), getCDT_DT_ID(), getGen_Type(), getDefinition());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
