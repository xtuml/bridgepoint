package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractInterfaceReferenceProcessor;

public class InterfaceReferenceProcessorSQL extends AbstractInterfaceReferenceProcessor {

    private String portId;
    private String interfaceId;

    public InterfaceReferenceProcessorSQL(String portId, String interfaceId) {
        // already processed by port processor
        this.portId = portId;
        this.interfaceId = interfaceId;
    }

    @Override
    public String getId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getFormal_Interface_Id() {
        return SQLUtils.idValue(interfaceId);
    }

    @Override
    public String getDelegation_Id() {
        // not supported yet
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getPort_Id() {
        // already processed for SQL output by port processor
        return portId;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getId(), getFormal_Interface_Id(), getDelegation_Id(), getPort_Id())
                .collect(Collectors.toList());
    }
}
