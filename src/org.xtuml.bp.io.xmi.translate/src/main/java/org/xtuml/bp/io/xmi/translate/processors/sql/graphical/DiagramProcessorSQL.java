package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractDiagramProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class DiagramProcessorSQL extends AbstractDiagramProcessor {

    private boolean selfCreated = false;

    public DiagramProcessorSQL(boolean selfCreated) {
        this.selfCreated = selfCreated;
    }

    @Override
    public String getdiagramId() {
        if (selfCreated) {
            // creating a root package diagram
            return SQLUtils.idValue(getModelElement().getPlainAttribute("id") + "_self");
        } else {
            return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
        }
    }

    @Override
    public String getname() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getzoom() {
        return SQLUtils.numberValue(1.000000);
    }

    @Override
    public String getviewportX() {
        return SQLUtils.numberValue(0.000000);
    }

    @Override
    public String getviewportY() {
        return SQLUtils.numberValue(0.000000);
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
        return Stream.of(getdiagramId(), getname(), getzoom(), getviewportX(), getviewportY(), getSmb_ID())
                .collect(Collectors.toList());
    }
}
