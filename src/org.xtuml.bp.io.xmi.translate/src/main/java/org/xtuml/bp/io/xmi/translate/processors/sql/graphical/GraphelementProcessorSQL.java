package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractGraphelementProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class GraphelementProcessorSQL extends AbstractGraphelementProcessor {

    private float x = 0f;
    private float y = 0f;
    private String Id;

    public GraphelementProcessorSQL(float x, float y, String Id) {
        this.x = x;
        this.y = y;
        this.Id = Id;
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
        if (Id != null) {
            return SQLUtils.idValue(Id);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getSmb_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getpositionX(), getpositionY(), getelementId(), getSmb_ID()).collect(Collectors.toList());
    }
}
