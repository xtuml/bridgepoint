package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPackageProcessor;

public class PackageProcessorSQL extends AbstractPackageProcessor {
    @Override
    public String getPackage_ID() {
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
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
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
}
