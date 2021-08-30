package org.xtuml.bp.io.xmi.translate.processors.sql.association;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassAsLinkProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassAsAssociatedOneSideProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassAsAssociatedOtherSideProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ClassInAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.LinkedAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.association.supporting.ReferringClassInAssocProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectorModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ModelProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.supporting.GraphicalModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class ClassAsLinkProcessorSQL extends AbstractClassAsLinkProcessor {
    private String assocId = IdProcessor.NULL_ID;
    ClassInAssociationProcessorSQL assrCia = null;

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        assocId = IdProcessor.UUID().toString();
        assrCia = new ClassInAssociationProcessorSQL(getModelElement().getPlainAttribute("id"), assocId);
        assrCia.setKeyLetters("R_OIR");
        assrCia.setModelElement(getModelElement());
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public String getOIR_ID() {
        return SQLUtils.idValue(assrCia.getId());
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(1);
    }

    @Override
    public String createSupportingElements() {
        StringBuilder supporting = new StringBuilder();
        supporting.append(assrCia.getProcessorOutput());
        ReferringClassInAssocProcessorSQL referringClassInAssocProcessorSQL = new ReferringClassInAssocProcessorSQL(
                getModelElement().getPlainAttribute("id"), assocId, assrCia.getId());
        referringClassInAssocProcessorSQL.setKeyLetters("R_RGO");
        referringClassInAssocProcessorSQL.setModelElement(getModelElement());
        supporting.append(referringClassInAssocProcessorSQL.getProcessorOutput());
        ClassAsAssociatedOneSideProcessorSQL classAsAssociatedOneSideProcessorSQL = new ClassAsAssociatedOneSideProcessorSQL(
                getModelElement(), "R_AONE", assocId);
        supporting.append(classAsAssociatedOneSideProcessorSQL.getProcessorOutput());
        ClassAsAssociatedOtherSideProcessorSQL classAsAssociatedOtherSideProcessorSQL = new ClassAsAssociatedOtherSideProcessorSQL(
                getModelElement(), "R_AOTH", assocId);
        supporting.append(classAsAssociatedOtherSideProcessorSQL.getProcessorOutput());
        LinkedAssociationProcessorSQL linkedAssociationProcessorSQL = new LinkedAssociationProcessorSQL(assocId);
        linkedAssociationProcessorSQL.setKeyLetters("R_ASSOC");
        supporting.append(linkedAssociationProcessorSQL.getProcessorOutput());
        AssociationProcessorSQL associationProcessorSQL = new AssociationProcessorSQL(assocId, getAssociationNumber(),
                "", "R_ASSR");
        associationProcessorSQL.setModelElement(getModelElement());
        associationProcessorSQL.setKeyLetters("R_REL");
        supporting.append(associationProcessorSQL.getProcessorOutput());
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(assocId,
                getModelElement().getOwner().getPlainAttribute("id"), 9);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        supporting.append(packageableElementProcessorSQL.getProcessorOutput());
        /**
         * Create the graphics now, we need two connectors the main and the link
         */
        GraphicalModel graphicalModel = new GraphicalModel(getModelElement().getOwner().getPlainAttribute("id"), 112);
        String diagramId = ModelProcessorSQL.createdModels.get(graphicalModel);
        ConnectorModel mainConnector = GraphicalElementProcessorSQL.createConnector(assocId,
                classAsAssociatedOneSideProcessorSQL.getClassId(), classAsAssociatedOtherSideProcessorSQL.getClassId(),
                diagramId, 24, 0f);
        ConnectorModel linkConnector = GraphicalElementProcessorSQL.createConnector(assocId, assrCia.getClassId(),
                assocId, diagramId, 34, 0f);
        supporting.append(mainConnector.getSql()).append(linkConnector.getSql());
        return supporting.toString();
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
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID(), getMult()).collect(Collectors.toList());
    }
}
