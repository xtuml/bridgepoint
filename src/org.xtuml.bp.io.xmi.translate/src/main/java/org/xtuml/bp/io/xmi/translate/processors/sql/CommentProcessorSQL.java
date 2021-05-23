package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractCommentProcessor;

public class CommentProcessorSQL extends AbstractCommentProcessor {
    @Override
    public String getComment_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getElement_ID() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getbody() {
        return SQLUtils.stringValue(getModelElement().getPlainAttribute("body"));
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getComment_ID(), getElement_ID(), getbody()).collect(Collectors.toList());
    }
}
