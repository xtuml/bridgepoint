package org.xtuml.bp.io.xmi.translate.processors.sql.association;

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
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassAsSimpleParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassInAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ReferredToClassInAssocProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.SimpleAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.supporting.ConnectionInformation;

public class AssociationProcessorSQL extends AbstractAssociationProcessor {

    static int count = 1;
    static ModelElement owner;

    public AssociationProcessorSQL() {
    }

    /**
     * Constructor for creation through subtype (R_SUBSUP)
     */
    String assocId;
    int numb = -1;
    String descrip;
    String subKeyLetters;
    private boolean ignoreSupporting = false;

    public AssociationProcessorSQL(String assocId, int numb, String descrip, String subKeyLetters) {
        this.assocId = assocId;
        this.numb = numb;
        this.descrip = descrip;
        this.subKeyLetters = subKeyLetters;
        ignoreSupporting = true;
    }

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        owner = element.getOwner();
    }

    @Override
    public String getRel_ID() {
        if (assocId != null) {
            return SQLUtils.idValue(assocId, subKeyLetters != null ? subKeyLetters : getKeyLetters());
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
    }

    @Override
    public String getNumb() {
        if (numb != -1) {
            return SQLUtils.numberValue(numb);
        }
        // EA captures a name only, but it may include the number?
        // we attempt to extract the number from that
        // otherwise fallback on incrementing numbering
        String name = getModelElement().getName();
        if (name.startsWith("R")) {
            String numb = name.substring(1, name.length());
            if (numb.chars().allMatch(Character::isDigit)) {
                int number = Integer.parseInt(numb);
                if (number > count) {
                    count = number + 1;
                }
                return SQLUtils.numberValue(number);
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
        if (descrip != null) {
            return SQLUtils.stringValue(descrip);
        }
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getSS_ID() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String createSupportingElements() {
        if (ignoreSupporting) {
            return "";
        }
        // processors to output for
        List<AbstractXtumlTypeProcessor> processors = new ArrayList<>();
        // look at the member ends for the associated classes
        Collection<?> ends = getModelElement().getSetAttribute("ownedends");

        // create simple assoc
        SimpleAssociationProcessorSQL simpProcessor = new SimpleAssociationProcessorSQL();
        simpProcessor.setModelElement(getModelElement());
        simpProcessor.setKeyLetters("R_SIMP");
        String lowerBound = getModelElement().getPlainAttribute("lowerBounds");
        String upperBound = getModelElement().getPlainAttribute("upperBounds");
        if (!lowerBound.equals("") || !upperBound.equals("")) {
            System.out.println();
        }
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
                            owningClass.getPlainAttribute("id"), 24, null);
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
    public boolean handlesPackageableElement() {
        return ignoreSupporting;
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
