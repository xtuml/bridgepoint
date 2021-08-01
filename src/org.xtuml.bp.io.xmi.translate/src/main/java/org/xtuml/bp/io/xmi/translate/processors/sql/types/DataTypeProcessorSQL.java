package org.xtuml.bp.io.xmi.translate.processors.sql.types;

import java.util.List;
import java.util.Optional;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.types.supporting.UserDataTypeProcessorSQL;
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
            if (getModelElement().getOwnedElements() != null) {
                Optional<ModelElement> coreType = getModelElement().getOwnedElements().stream()
                        .filter(o -> o.getType().getName().equals("primitivetype")).findAny();
                if (coreType.isPresent()) {
                    // TODO: associate with core type
                }
                Optional<ModelElement> edt = getModelElement().getOwnedElements().stream()
                        .filter(o -> o.getType().getName().equals("enumeration")).findAny();
                if (edt.isPresent()) {
                    // S_EDT handled by its own processor
                    return "";
                }
                UserDataTypeProcessorSQL udtProcessor = new UserDataTypeProcessorSQL(dtId);
                udtProcessor.setKeyLetters("S_UDT");
                udtProcessor.setModelElement(getModelElement());
                return SQLUtils.getInsertStatement(peProcessor, getModelElement()) + "\n"
                        + SQLUtils.getInsertStatement(udtProcessor, getModelElement());
            }
        }
        return "";
    }

    @Override
    public String getDT_ID() {
        if (this.id != null) {
            return SQLUtils.idValue(this.id);
        }
        // see if a reference has already created an id
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

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
