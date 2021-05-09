package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractGraphicalElementProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class GraphicalElementProcessorSQL extends AbstractGraphicalElementProcessor {
    @Override
    public String getelementId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getdiagramId() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getOOA_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("element"));
    }

    @Override
    public String getOOA_Type() {
        return SQLUtils.stringValue("101");
    }

    @Override
    public String getrepresents() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getrepresents_path() {
        return SQLUtils.stringValue(getModelElement().getPlainAttribute("bounds"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream
                .of(getelementId(), getdiagramId(), getOOA_ID(), getOOA_Type(), getrepresents(), getrepresents_path())
                .collect(Collectors.toList());
    }
}
