package org.xtuml.bp.io.xmi.translate.processors.sql.types.supporting;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInstanceReferenceDataTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class InstanceReferenceDataTypeProcessorSQL extends AbstractInstanceReferenceDataTypeProcessor {
    private String id;
    private String pkgId;
    private int type;

    public InstanceReferenceDataTypeProcessorSQL(String id, String pkgId, int type) {
        this.id = id;
        this.pkgId = pkgId;
        this.type = type;
    }

    @Override
    public String createSupportingElements() {
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(
                IdProcessor.process(this.id, null), this.pkgId, this.type);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        return SQLUtils.getProcessorOutput(packageableElementProcessorSQL);
    }

    @Override
    public String getDT_ID() {
        return SQLUtils.idValue(this.id);
    }

    @Override
    public String getisSet() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getDT_ID(), getisSet(), getObj_ID());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
