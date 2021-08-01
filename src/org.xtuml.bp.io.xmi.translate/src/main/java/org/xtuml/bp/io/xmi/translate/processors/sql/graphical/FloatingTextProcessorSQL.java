package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractFloatingTextProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class FloatingTextProcessorSQL extends AbstractFloatingTextProcessor {

    private String Id;
    private Integer end;
    private String geId;

    public FloatingTextProcessorSQL(Integer end) {
        this.Id = IdProcessor.UUID().toString();
        this.end = end;
    }

    public FloatingTextProcessorSQL(int end, String geId) {
        this.Id = IdProcessor.UUID().toString();
        this.end = end;
        this.geId = geId;
    }

    @Override
    public String getelementId() {
        return SQLUtils.idValue(Id);
    }

    @Override
    public String getconn_elementId() {
        if (geId != null) {
            return SQLUtils.idValue(geId);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getend() {
        return SQLUtils.numberValue(end);
    }

    @Override
    public String getdeltaX() {
        return SQLUtils.numberValue(0.000000);
    }

    @Override
    public String getdeltaY() {
        return SQLUtils.numberValue(0.000000);
    }

    @Override
    public String createSupportingElements() {
        // Floating text is a DIM_ND itself
        DiagramelementProcessorSQL diagramelementProcessorSQL = new DiagramelementProcessorSQL(Id);
        diagramelementProcessorSQL.setKeyLetters("DIM_ELE");
        GraphelementProcessorSQL graphelementProcessorSQL = new GraphelementProcessorSQL(0f, 0f, Id);
        graphelementProcessorSQL.setKeyLetters("DIM_GE");
        GraphnodeProcessorSQL graphnodeProcessorSQL = new GraphnodeProcessorSQL(0f, 0f, Id);
        graphnodeProcessorSQL.setKeyLetters("DIM_ND");
        StringBuilder builder = new StringBuilder();
        return builder.append(SQLUtils.getInsertStatement(graphelementProcessorSQL, getModelElement()))
                .append(SQLUtils.getInsertStatement(graphnodeProcessorSQL, getModelElement())).toString();
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getelementId(), getconn_elementId(), getend(), getdeltaX(), getdeltaY())
                .collect(Collectors.toList());
    }
}
