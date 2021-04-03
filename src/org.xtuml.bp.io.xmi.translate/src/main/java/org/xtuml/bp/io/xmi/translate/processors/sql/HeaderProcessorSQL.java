package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;

public class HeaderProcessorSQL extends AbstractXtumlTypeProcessor {

    public String getHeader() {
        return SQLUtils.getHeader();
    }

    @Override
    public String process(ModelElement element, String keyLetters) {
        return getHeader();
    }

    @Override
    public List<String> getValues(ModelElement modelElement) {
        return null;
    }

}
