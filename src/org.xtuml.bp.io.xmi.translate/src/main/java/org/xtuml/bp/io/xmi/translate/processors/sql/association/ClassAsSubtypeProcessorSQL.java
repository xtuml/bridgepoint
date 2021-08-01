package org.xtuml.bp.io.xmi.translate.processors.sql.association;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.AbstractXtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassAsSubtypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassAsSupertypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassInAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ReferredToClassInAssocProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ReferringClassInAssocProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.SubtypeSupertypeAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectorModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ModelProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.supporting.GraphicalModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class ClassAsSubtypeProcessorSQL extends AbstractClassAsSubtypeProcessor {

    static List<String> createdSupertypes = new ArrayList<>();

    static Map<String, String> supertypeAssoc = new HashMap<>();

    ModelElement subtype = null;
    String classId = null;
    String assocId = null;
    ClassInAssociationProcessorSQL cia = null;

    private Map<String, ConnectorModel> superConnectors = new HashMap<>();

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
        String supertypeId = supertype.getPlainAttribute("id");
        GraphicalModel graphicalModel = new GraphicalModel(
                getModelElement().getOwner().getOwner().getPlainAttribute("id"), 112);
        String diagramId = ModelProcessorSQL.createdModels.get(graphicalModel);
        if (!createdSupertypes.contains(supertypeId)) {
            String pkgId = getModelElement().getOwner().getOwner().getPlainAttribute("id");
            PackageableElementProcessorSQL peProcessor = new PackageableElementProcessorSQL(null, pkgId);
            peProcessor.setModelElement(getModelElement());
            peProcessor.setKeyLetters("PE_PE");
            processors.add(peProcessor);
            SubtypeSupertypeAssociationProcessorSQL subSup = new SubtypeSupertypeAssociationProcessorSQL();
            subSup.setModelElement(getModelElement());
            subSup.setKeyLetters("R_SUBSUP");
            int numb = getAssociationNumber();
            AssociationProcessorSQL superAssocProcessorSQL = new AssociationProcessorSQL(assocId, numb,
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
            ConnectorModel superConnector = GraphicalElementProcessorSQL.createConnector(assocId, supertypeId, null,
                    diagramId, 36);
            // cache super connector for other subtypes
            superConnectors.put(assocId, superConnector);
            supertypeOutput = SQLUtils.getInsertStatement(subSup, getModelElement())
                    + SQLUtils.getInsertStatement(superAssocProcessorSQL, null)
                    + SQLUtils.getInsertStatement(superCia, null) + SQLUtils.getInsertStatement(superProcessor, null)
                    + SQLUtils.getInsertStatement(rto, null) + superConnector.getSql();
            supertypeAssoc.put(supertypeId, assocId);
            createdSupertypes.add(supertypeId);
        }
        ConnectorModel superConnector = superConnectors.get(assocId);
        cia.setKeyLetters("R_OIR");
        processors.add(cia);
        ReferringClassInAssocProcessorSQL referringProcessor = new ReferringClassInAssocProcessorSQL(classId, assocId,
                cia.getId());
        referringProcessor.setKeyLetters("R_RGO");
        processors.add(referringProcessor);
        StringJoiner joiner = new StringJoiner("");
        processors.forEach(p -> joiner.add(p.getProcessorOutput()));
        joiner.add(supertypeOutput.toString());
        /* Create the subtype connectors now */
        ConnectorModel subConnector = GraphicalElementProcessorSQL.createConnector(cia.getId(), cia.getClassId(),
                superConnector.getKey(), diagramId, 35);
        joiner.add(subConnector.getSql());
        return joiner.toString();
    }

    private int getAssociationNumber() {
        ModelElement eaDiagramConnector = XMITranslate.eaDiagramConnectors
                .get(getModelElement().getPlainAttribute("id"));
        if (eaDiagramConnector.getName().startsWith("R")) {
            String assocNumbString = eaDiagramConnector.getName().replace("R", "");
            return Integer.valueOf(assocNumbString);
        }
        return 0;
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
