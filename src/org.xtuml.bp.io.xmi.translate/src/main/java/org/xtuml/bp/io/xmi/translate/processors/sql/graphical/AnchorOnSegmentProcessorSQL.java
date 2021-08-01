package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractAnchorOnSegmentProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class AnchorOnSegmentProcessorSQL extends AbstractAnchorOnSegmentProcessor {
    private String conId;
    private String segId;

    AnchorOnSegmentProcessorSQL(String conId, String segId) {
        this.conId = conId;
        this.segId = segId;
    }

    @Override
    public String getconId() {
        return SQLUtils.idValue(conId);
    }

    @Override
    public String getelementId() {
        return SQLUtils.idValue(segId);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getconId(), getelementId()).collect(Collectors.toList());
    }
}
