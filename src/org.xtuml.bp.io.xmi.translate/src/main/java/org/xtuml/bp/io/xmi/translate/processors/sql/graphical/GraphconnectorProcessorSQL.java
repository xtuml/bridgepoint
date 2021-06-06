package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractGraphconnectorProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class GraphconnectorProcessorSQL extends AbstractGraphconnectorProcessor {

    private String Id;
    public Float x;
    public Float y;
    private String anchorElementId;

    public GraphconnectorProcessorSQL(String anchorElementId, Float x, Float y) {
        this.Id = IdProcessor.UUID().toString();
        this.anchorElementId = anchorElementId;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return Id;
    }

    @Override
    public String getconId() {
        return SQLUtils.idValue(Id);
    }

    @Override
    public String getpositionX() {
        return SQLUtils.numberValue(x);
    }

    @Override
    public String getpositionY() {
        return SQLUtils.numberValue(y);
    }

    @Override
    public String getelementId() {
        return SQLUtils.idValue(anchorElementId);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getconId(), getpositionX(), getpositionY(), getelementId()).collect(Collectors.toList());
    }
}
