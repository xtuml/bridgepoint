package org.xtuml.bp.io.xmi.translate.processors.sql.types;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.types.supporting.UserDataTypeProcessorSQL;

public class DataTypeProcessorSQL extends AbstractDataTypeProcessor {

    private String id;
    private String name;
    private String descrip;

    public DataTypeProcessorSQL() {

    }

    public DataTypeProcessorSQL(String id) {
        this.id = id;
    }

    public DataTypeProcessorSQL(String id, String name, String descrip) {
        this.id = id;
        this.name = name;
        this.descrip = descrip;
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
            // if this data type has not been created, we are in a direct DT creation
            // need to see if it s primitive, else create a UDT
            if (dtId == null) {
                dtId = IdProcessor.process(getModelElement().getPlainAttribute("id"), null);
            }
            PackageableElementProcessorSQL peProcessor = new PackageableElementProcessorSQL(dtId);
            peProcessor.setKeyLetters("PE_PE");
            peProcessor.setModelElement(getModelElement());
            if (getModelElement().getOwnedElements() != null) {
                Optional<ModelElement> coreType = getModelElement().getOwnedElements().stream()
                        .filter(o -> o.getType().getName().equals("primitivetype")).findAny();
                if (coreType.isPresent()) {
                    // TODO: associate with core type
                    XMITranslate.logger.log("Found CDT as primitive type");
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
            } else {
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
        String id = IdProcessor.getId(getAdjustedName());
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
        if (this.name != null) {
            return SQLUtils.stringValue(this.name);
        }
        return SQLUtils.stringValue(getAdjustedName());
    }

    private String getAdjustedName() {
        return getModelElement().getName().replaceAll("\\{", "").replaceAll("\\}", "");
    }

    @Override
    public String getDescrip() {
        if (this.descrip != null) {
            return SQLUtils.stringValue(this.descrip);
        }
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

    @Override
    public IgnoreType ignoreTranslation() {
        // ignore known core types
        switch (getModelElement().getName()) {
            case "int":
                return IgnoreType.HANDLED;
            case "void":
                return IgnoreType.HANDLED;
            case "boolean":
                return IgnoreType.HANDLED;
            case "UUID":
                return IgnoreType.HANDLED;
            default:
                return IgnoreType.NOT_IGNORED;
        }
    }

    public static String createDataType(String id, String name, String descrip, String pkgId) {
        DataTypeProcessorSQL dataTypeProcessorSQL = new DataTypeProcessorSQL(id, name, descrip);
        dataTypeProcessorSQL.setKeyLetters("S_DT");
        PackageableElementProcessorSQL peProcessor = new PackageableElementProcessorSQL(IdProcessor.process(id, "S_DT"),
                pkgId, 3);
        peProcessor.setKeyLetters("PE_PE");
        UserDataTypeProcessorSQL udtProcessor = new UserDataTypeProcessorSQL(IdProcessor.process(id, "S_DT"));
        udtProcessor.setKeyLetters("S_UDT");
        StringJoiner result = new StringJoiner("\n").add(SQLUtils.getProcessorOutput(dataTypeProcessorSQL))
                .add(SQLUtils.getProcessorOutput(peProcessor)).add(SQLUtils.getProcessorOutput(udtProcessor));
        return result.toString();
    }
}
