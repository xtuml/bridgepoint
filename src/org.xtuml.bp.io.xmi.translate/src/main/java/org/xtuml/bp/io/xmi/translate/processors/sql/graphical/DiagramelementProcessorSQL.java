package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractDiagramelementProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class DiagramelementProcessorSQL extends AbstractDiagramelementProcessor {
    private String geId;

    public DiagramelementProcessorSQL() {
    }

    public DiagramelementProcessorSQL(String geId) {
        this.geId = geId;
    }

    @Override
    public String getelementId() {
        if (geId != null) {
            return SQLUtils.idValue(geId);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getisVisible() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getcontainer_elementId() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getelementId(), getisVisible(), getcontainer_elementId()).collect(Collectors.toList());
    }
}
