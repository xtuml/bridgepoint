package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractConnectorProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ConnectorProcessorSQL extends AbstractConnectorProcessor {
    @Override
    public String getelementId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getAssoc_elementId() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getelementId(), getAssoc_elementId()).collect(Collectors.toList());
    }
}
