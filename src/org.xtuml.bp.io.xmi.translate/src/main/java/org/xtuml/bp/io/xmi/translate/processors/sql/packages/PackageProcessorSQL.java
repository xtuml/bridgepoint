package org.xtuml.bp.io.xmi.translate.processors.sql.packages;

import java.util.List;
import java.util.StringJoiner;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPackageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.SequenceUtil;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class PackageProcessorSQL extends AbstractPackageProcessor {

    private String pkgId;
    private String name;
    private String descrip;

    public PackageProcessorSQL() {
    }

    public PackageProcessorSQL(String pkgId, String name, String descrip) {
        this.pkgId = pkgId;
        this.name = name;
        this.descrip = descrip;
    }

    @Override
    public String getPackage_ID() {
        if (this.pkgId != null) {
            return SQLUtils.idValue(pkgId);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSys_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getDirect_Sys_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getName() {
        if (this.name != null) {
            return SQLUtils.stringValue(this.name);
        }
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        if (this.descrip != null) {
            return SQLUtils.stringValue(this.descrip);
        }
        return SQLUtils.stringValue("EA Element " + getModelElement().getFullName());
    }

    @Override
    public String getNum_Rng() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getPackage_ID(), getSys_ID(), getDirect_Sys_ID(), getName(), getDescrip(), getNum_Rng());
    }

    @Override
    public String createSupportingElements() {
        if (getModelElement() == null) {
            // do not process for direct package creations
            return "";
        }
        // in some cases it may be the best option
        // to create certain package types here
        //
        // Create Sequence Packages here
        if (isSequence()) {
            SequenceUtil util = new SequenceUtil();
            return util.createSequence(getModelElement());
        }
        return "";
    }

    private boolean isSequence() {
        String type = getModelElement().getType().getName();
        switch (type) {
            case "interaction":
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    public static String[] createPackage(String name, String description) {
        String pkgId = IdProcessor.UUID().toString();
        PackageProcessorSQL packageProcessorSQL = new PackageProcessorSQL(pkgId, name, description);
        packageProcessorSQL.setKeyLetters("EP_PKG");
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(pkgId,
                IdProcessor.NULL_ID, 7);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        StringJoiner result = new StringJoiner("").add(SQLUtils.getProcessorOutput(packageProcessorSQL))
                .add(SQLUtils.getProcessorOutput(packageableElementProcessorSQL));
        String[] pkgInfo = new String[2];
        pkgInfo[0] = result.toString();
        pkgInfo[1] = pkgId;
        return pkgInfo;
    }
}
