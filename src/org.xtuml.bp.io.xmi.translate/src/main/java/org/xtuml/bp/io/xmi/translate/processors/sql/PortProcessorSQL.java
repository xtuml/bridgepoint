package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPortProcessor;

public class PortProcessorSQL extends AbstractPortProcessor {

    private String componentId;
    private String id;
    private int count;
    private static Map<String, Integer> counts = new HashMap<>();

    public PortProcessorSQL(String componentId) {
        this.id = IdProcessor.UUID().toString();
        this.componentId = componentId;
        Integer count = counts.get(componentId);
        if (count == null) {
            this.count = 1;
        } else {
            this.count = count + 1;
        }
        counts.put(componentId, this.count);
    }

    @Override
    public String getId() {
        return SQLUtils.idValue(id);
    }

    @Override
    public String getComponent_Id() {
        return SQLUtils.idValue(componentId);
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue("Port" + count);
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getDoNotShowPortOnCanvas() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getKey_Lett() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getId(), getComponent_Id(), getName(), getMult(), getDoNotShowPortOnCanvas(), getKey_Lett())
                .collect(Collectors.toList());
    }

}
