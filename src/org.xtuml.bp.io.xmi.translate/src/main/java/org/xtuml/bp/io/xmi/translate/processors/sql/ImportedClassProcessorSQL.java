package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractImportedClassProcessor;

public class ImportedClassProcessorSQL extends AbstractImportedClassProcessor {

    UUID importedClassId = IdProcessor.UUID();
    String packageId = null;

    public ImportedClassProcessorSQL(String packageId) {
        this.packageId = packageId;
    }

    @Override
    public String getIObj_ID() {
        // DO NOT use the keyletter argument here, imported classes
        // are handled via the graphical element location
        return SQLUtils.idValue(importedClassId.toString());
    }

    public UUID getImportedClassId() {
        return importedClassId;
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getModl_Typ() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getSS_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getObj_Name() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getObj_KL() {
        return SQLUtils.stringValue(SQLUtils.convertToPascalCase(getModelElement().getName()));
    }

    @Override
    public String createSupportingElements() {
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(
                importedClassId.toString(), packageId, 19);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        return SQLUtils.getInsertStatement(packageableElementProcessorSQL, getModelElement());
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
        return Stream.of(getIObj_ID(), getObj_ID(), getModl_Typ(), getSS_ID(), getObj_Name(), getObj_KL())
                .collect(Collectors.toList());
    }
}
