package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractShapeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ShapeProcessorSQL extends AbstractShapeProcessor {
    @Override
    public String getelementId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return List.of(getelementId());
    }
}
