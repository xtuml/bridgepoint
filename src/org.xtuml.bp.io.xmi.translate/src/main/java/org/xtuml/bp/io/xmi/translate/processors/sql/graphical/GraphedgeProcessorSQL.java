package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractGraphedgeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class GraphedgeProcessorSQL extends AbstractGraphedgeProcessor {

    private String firstConId;
    private String lastConId;

    public GraphedgeProcessorSQL(String firstConId, String lastConId) {
        this.firstConId = firstConId;
        this.lastConId = lastConId;
    }

    @Override
    public String getfirst_conId() {
        return SQLUtils.idValue(firstConId);
    }

    @Override
    public String getlast_conId() {
        return SQLUtils.idValue(lastConId);
    }

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
        return Stream.of(getfirst_conId(), getlast_conId(), getelementId()).collect(Collectors.toList());
    }
}
