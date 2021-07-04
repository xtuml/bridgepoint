package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractUseCaseAssociationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectionInformation;

public class UseCaseAssociationProcessorSQL extends AbstractUseCaseAssociationProcessor {

    private String sourceId;
    private String destId;

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        Collection<?> ends = getModelElement().getSetAttribute("ownedends");
        ends.stream().forEach(e -> {
            ModelElement end = (ModelElement) e;
            ModelElement owningElement = end.getRefAttribute("propertytype");
            if (sourceId == null) {
                sourceId = owningElement.getPlainAttribute("id");
            } else {
                destId = owningElement.getPlainAttribute("id");
            }
        });
    }

    @Override
    public IgnoreType ignoreTranslation() {
        Collection<?> ends = getModelElement().getSetAttribute("ownedends");
        boolean anyOwningEmpty = ends.stream().filter(e -> {
            ModelElement end = (ModelElement) e;
            ModelElement owningElement = end.getRefAttribute("propertytype");
            return owningElement == null;
        }).findAny().isPresent();
        return anyOwningEmpty ? IgnoreType.HANDLED : IgnoreType.NOT_IGNORED;
    }

    @Override
    public String getAssoc_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getSource_Part_ID() {
        return SQLUtils.idValue(sourceId);
    }

    @Override
    public String getDestination_Part_ID() {
        return SQLUtils.idValue(destId);
    }

    @Override
    public String createSupportingElements() {
        // TODO Need to handle other subtypes of use case association
        ConnectionInformation information = new ConnectionInformation(getModelElement().getPlainAttribute("id"),
                sourceId, 87, getModelElement().getPlainAttribute("id"));
        information.setEndEle(destId);
        BinaryAssociationProcessorSQL binaryAssociationProcessorSQL = new BinaryAssociationProcessorSQL();
        binaryAssociationProcessorSQL.setModelElement(getModelElement());
        binaryAssociationProcessorSQL.setKeyLetters("UC_BA");
        return binaryAssociationProcessorSQL.getProcessorOutput();
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getAssoc_ID(), getSource_Part_ID(), getDestination_Part_ID()).collect(Collectors.toList());
    }
}
