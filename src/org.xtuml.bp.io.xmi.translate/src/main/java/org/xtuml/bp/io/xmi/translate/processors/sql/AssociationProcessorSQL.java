package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractAssociationProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectionInformation;

public class AssociationProcessorSQL extends AbstractAssociationProcessor {

    static int count = 1;
    static ModelElement owner;

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getNumb() {
        // EA captures a name only, but it may include the number?
        // we attempt to extract the number from that
        // otherwise fallback on incrementing numbering
        String name = getModelElement().getName();
        if (name.startsWith("R")) {
            String numb = name.substring(1, name.length());
            if (numb.chars().allMatch(Character::isDigit)) {
                return SQLUtils.numberValue(Integer.parseInt(numb));
            }
        }
        if (owner != null && owner == getModelElement().getOwner()) {
            count++;
        } else {
            count = 1;
            owner = getModelElement().getOwner();
        }
        return SQLUtils.numberValue(count);
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getSS_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String createSupportingElements() {
        // processors to output for
        List<AbstractXtumlTypeProcessor> processors = new ArrayList<>();
        // look at the member ends for the associated classes
        Collection<?> ends = getModelElement().getSetAttribute("ownedends");

        // create simple assoc
        SimpleAssociationProcessorSQL simpProcessor = new SimpleAssociationProcessorSQL();
        simpProcessor.setModelElement(getModelElement());
        simpProcessor.setKeyLetters("R_SIMP");
        processors.add(simpProcessor);
        // process each side
        ends.stream().forEach(e -> {
            ModelElement element = (ModelElement) e;
            ModelElement owningClass = element.getRefAttribute("propertytype");
            if (owningClass != null) {
                ClassInAssociationProcessorSQL cia = new ClassInAssociationProcessorSQL(
                        owningClass.getPlainAttribute("id"), getModelElement().getPlainAttribute("id"));
                cia.setKeyLetters("R_OIR");
                processors.add(cia);
                Collection<ModelElement> boundCollection = element.getOwnedElements();
                if (boundCollection != null && boundCollection.size() > 0) {
                    ModelElement[] bounds = boundCollection.toArray(new ModelElement[0]);
                    String lowerBound = bounds[0].getPlainAttribute("value");
                    String upperBound = bounds[1].getPlainAttribute("value");
                }
                ClassAsSimpleParticipantProcessorSQL part = new ClassAsSimpleParticipantProcessorSQL(
                        owningClass.getPlainAttribute("id"), getModelElement().getPlainAttribute("id"), cia.getId(), 0,
                        0, element.getName());
                part.setKeyLetters("R_PART");
                processors.add(part);
                ReferredToClassInAssocProcessorSQL rto = new ReferredToClassInAssocProcessorSQL(
                        owningClass.getPlainAttribute("id"), getModelElement().getPlainAttribute("id"), cia.getId());
                rto.setKeyLetters("R_RTO");
                processors.add(rto);
                // setup for graphics later, note we are considering the first as the start
                ConnectionInformation information = ConnectionInformation.connectionMap
                        .get(getModelElement().getPlainAttribute("id"));
                if (information != null) {
                    information.setEndEle(owningClass.getPlainAttribute("id"));
                } else {
                    new ConnectionInformation(getModelElement().getPlainAttribute("id"),
                            owningClass.getPlainAttribute("id"));
                }
            }
        });
        StringJoiner joiner = new StringJoiner("\n");
        processors.forEach(p -> joiner.add(p.getProcessorOutput()));
        return joiner.toString();
    }

    @Override
    public IgnoreType ignoreTranslation() {
        IgnoreType ignoreType = super.ignoreTranslation();
        boolean ignore = ignoreType == IgnoreType.NOT_HANDLED;
        if (ignoreType == IgnoreType.NOT_IGNORED) {
            // look at the member ends for the associated classes
            Collection<?> memberEnds = getModelElement().getSetAttribute("memberends");
            Collection<?> ownedEnds = getModelElement().getSetAttribute("ownedends");
            // do not support memberEnds between datatypes this way
            boolean datatypeAssoc = memberEnds.stream()
                    .anyMatch(e -> ((ModelElement) e).getOwner().getType().getName().equals("datatype"));
            // generalizations handled separately
            Collection<?> generalizations = getModelElement().getSetAttribute("generalizations");
            boolean subsupAssoc = generalizations != null && generalizations.size() != 0;
            // TODO: see if reflexive assocs have one end, or two
            boolean onlyOneSide = ownedEnds.size() < 2;
            ignore = datatypeAssoc || subsupAssoc || onlyOneSide;
        }
        return ignore ? IgnoreType.HANDLED : IgnoreType.NOT_IGNORED;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getRel_ID(), getNumb(), getDescrip(), getSS_ID()).collect(Collectors.toList());
    }
}
