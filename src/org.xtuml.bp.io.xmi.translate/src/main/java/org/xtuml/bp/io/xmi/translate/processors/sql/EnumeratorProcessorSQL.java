package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractEnumeratorProcessor;

public class EnumeratorProcessorSQL extends AbstractEnumeratorProcessor {

    static ModelElement lastElement = null;
    static int count = 1;
    static ModelElement owner;

    @Override
    public String getEnum_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getName() {
        return SQLUtils.stringValue(getModelElement().getName());
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element " + getModelElement().getFullName());
    }

    @Override
    public String getEDT_DT_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getPrevious_Enum_ID() {
        String prevId = IdProcessor.NULL_ID;
        if (lastElement != null && lastElement.getOwner() == getModelElement().getOwner()) {
            prevId = lastElement.getPlainAttribute("id");
            lastElement = getModelElement();
        } else {
            lastElement = getModelElement();
        }
        return SQLUtils.idValue(prevId);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getEnum_ID(), getName(), getDescrip(), getEDT_DT_ID(), getPrevious_Enum_ID())
                .collect(Collectors.toList());
    }
}
