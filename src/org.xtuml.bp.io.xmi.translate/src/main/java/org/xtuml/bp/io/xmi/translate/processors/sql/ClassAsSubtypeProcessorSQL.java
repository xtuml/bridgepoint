package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassAsSubtypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectionInformation;

public class ClassAsSubtypeProcessorSQL extends AbstractClassAsSubtypeProcessor {

    static List<String> createdSupertypes = new ArrayList<>();

    static Map<String, String> supertypeAssoc = new HashMap<>();

    ModelElement subtype = null;
    String classId = null;
    String assocId = null;
    ClassInAssociationProcessorSQL cia = null;

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        /**
         * create the CIA first as we need a handle on the generated cia id
         */

        subtype = element.getOwner();
        classId = subtype.getPlainAttribute("id");
        ModelElement supertype = element.getRefAttribute("general");
        String supertypeId = supertype.getPlainAttribute("id");
        assocId = supertypeAssoc.get(supertypeId) != null ? supertypeAssoc.get(supertypeId)
                : element.getPlainAttribute("id");
        cia = new ClassInAssociationProcessorSQL(classId, assocId);
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(subtype.getPlainAttribute("id"));
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public String getOIR_ID() {
        // subtype graphics use the OIR for represents
        SQLUtils.idValue(getModelElement().getPlainAttribute("id"), getKeyLetters());
        return SQLUtils.idValue(cia.getId(), getKeyLetters());
    }

    @Override
    public String createSupportingElements() {
        ModelElement supertype = getModelElement().getRefAttribute("general");
        String supertypeOutput = "";
        List<AbstractXtumlTypeProcessor> processors = new ArrayList<>();
        ModelElement subtype = getModelElement().getOwner();
        String supertypeId = supertype.getPlainAttribute("id");
        if (!createdSupertypes.contains(supertypeId)) {
            String pkgId = getModelElement().getOwner().getOwner().getPlainAttribute("id");
            PackageableElementProcessorSQL peProcessor = new PackageableElementProcessorSQL(null, pkgId);
            peProcessor.setModelElement(getModelElement());
            peProcessor.setKeyLetters("PE_PE");
            processors.add(peProcessor);
            SubtypeSupertypeAssociationProcessorSQL subSup = new SubtypeSupertypeAssociationProcessorSQL();
            subSup.setModelElement(getModelElement());
            subSup.setKeyLetters("R_SUBSUP");
            AssociationProcessorSQL superAssocProcessorSQL = new AssociationProcessorSQL(assocId, 0,
                    "EA Element: " + getModelElement().getFullName(), "R_SUPER");
            superAssocProcessorSQL.setKeyLetters("R_REL");
            ClassInAssociationProcessorSQL superCia = new ClassInAssociationProcessorSQL(supertypeId, assocId);
            superCia.setKeyLetters("R_OIR");
            ClassAsSupertypeProcessorSQL superProcessor = new ClassAsSupertypeProcessorSQL(supertypeId, assocId,
                    superCia.getId());
            superProcessor.setKeyLetters("R_SUPER");
            ReferredToClassInAssocProcessorSQL rto = new ReferredToClassInAssocProcessorSQL(supertypeId, assocId,
                    superCia.getId());
            rto.setKeyLetters("R_RTO");
            // setup for graphics later, supertype start and end null
            new ConnectionInformation(assocId, supertypeId, 36, null);
            supertypeOutput = SQLUtils.getInsertStatement(subSup, getModelElement())
                    + SQLUtils.getInsertStatement(superAssocProcessorSQL, null)
                    + SQLUtils.getInsertStatement(superCia, null) + SQLUtils.getInsertStatement(superProcessor, null)
                    + SQLUtils.getInsertStatement(rto, null);
            supertypeAssoc.put(supertypeId, assocId);
            createdSupertypes.add(supertypeId);
        }
        // setup for graphics later, subtype start and end supertype
        ConnectionInformation subInfo = new ConnectionInformation(getModelElement().getPlainAttribute("id"),
                subtype.getPlainAttribute("id"), 35, cia.getId());
        subInfo.setEndEle(assocId);
        cia.setKeyLetters("R_OIR");
        processors.add(cia);
        ReferringClassInAssocProcessorSQL referringProcessor = new ReferringClassInAssocProcessorSQL(classId, assocId,
                cia.getId());
        referringProcessor.setKeyLetters("R_RGO");
        processors.add(referringProcessor);
        StringJoiner joiner = new StringJoiner("");
        processors.forEach(p -> joiner.add(p.getProcessorOutput()));
        joiner.add(supertypeOutput.toString());
        return joiner.toString();
    }

    @Override
    public IgnoreType ignoreTranslation() {
        IgnoreType ignoreType = super.ignoreTranslation();
        boolean ignore = ignoreType == IgnoreType.NOT_HANDLED;
        if (ignoreType == IgnoreType.NOT_IGNORED) {
            ModelElement subtype = getModelElement().getOwner();
            if (subtype.getType().getName().equals("datatype")) {
                ignore = true;
            }
            ModelElement supertype = getModelElement().getRefAttribute("general");
            if (supertype == null) {
                ignore = true;
            }
        }
        return ignore ? IgnoreType.HANDLED : IgnoreType.NOT_IGNORED;
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID()).collect(Collectors.toList());
    }
}
