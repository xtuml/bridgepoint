package org.xtuml.bp.io.xmi.translate.processors.sql.interaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLifespanProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectorModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.ClassParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.ComponentParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.InteractionParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class LifespanProcessorSQL extends AbstractLifespanProcessor {

    private String packageId;

    public LifespanProcessorSQL(String packageId) {
        this.packageId = packageId;
    }

    @Override
    public String getPart_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String createSupportingElements() {
        StringJoiner supportingElements = new StringJoiner("");
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(
                IdProcessor.process(getModelElement().getPlainAttribute("id")), packageId, 14);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        supportingElements.add(packageableElementProcessorSQL.getProcessorOutput());
        // create supertype
        InteractionParticipantProcessorSQL interactionParticipantProcessorSQL = new InteractionParticipantProcessorSQL();
        interactionParticipantProcessorSQL.setModelElement(getModelElement());
        interactionParticipantProcessorSQL.setKeyLetters("SQ_P");
        supportingElements.add(interactionParticipantProcessorSQL.getProcessorOutput());
        /**
         * Create the source element, there is not a good element to directly create
         * them with in the EA mm.
         */
        ModelElement represents = getSourceParticipantPart();
        String elementId = null;
        String graphPartId = null;
        int type = 0;
        if (represents != null) {
            ComponentParticipantProcessorSQL componentParticipantProcessorSQL = new ComponentParticipantProcessorSQL(
                    getModelElement().getName(), packageId);
            componentParticipantProcessorSQL.setModelElement(represents);
            componentParticipantProcessorSQL.setKeyLetters("SQ_COP");
            supportingElements.add(componentParticipantProcessorSQL.getProcessorOutput());
            elementId = componentParticipantProcessorSQL.getModelElement().getPlainAttribute("id");
            graphPartId = componentParticipantProcessorSQL.getId();
            type = 107;
        } else {
            // EA supports elements that xtuml does not (like a provided interface)
            // we will use a Class Participant
            ClassParticipantProcessorSQL classParticipantProcessorSQL = new ClassParticipantProcessorSQL(packageId,
                    false);
            if (getModelElement().getType().getName().equals("lifeline")) {
                classParticipantProcessorSQL.setModelElement(getModelElement().getRefAttribute("represents"));
            } else {
                if (getModelElement().getType().hasAttribute("covered")) {
                    classParticipantProcessorSQL.setModelElement(getModelElement().getRefAttribute("covered"));
                } else {
                    classParticipantProcessorSQL.setModelElement(getModelElement());
                }
            }
            classParticipantProcessorSQL.setKeyLetters("SQ_CP");
            supportingElements.add(classParticipantProcessorSQL.getProcessorOutput());
            elementId = classParticipantProcessorSQL.getModelElement().getPlainAttribute("id");
            graphPartId = classParticipantProcessorSQL.getId();
            type = 63;
        }
        supportingElements.add(createGraphicalElements(packageId, elementId, graphPartId, type));
        return supportingElements.toString();
    }

    private String createGraphicalElements(String pkgId, String elementId, String partId, int type) {
        StringJoiner graphOutput = new StringJoiner("");
        // find the model instance associated with this
        Optional<ModelElement> potentialModel = XMITranslate.graphicElements.keySet().stream()
                .filter(gd -> gd.getType().getMapping().equals("GD_MD")
                        && gd.getPlainAttribute("parentAsRepresents").equals(pkgId))
                .findFirst();
        if (potentialModel.isPresent()) {
            ModelElement graphModel = potentialModel.get();
            // find the graphical element associated with this element
            Collection<ModelElement> graphElements = graphModel.getRelations("context");
            Optional<ModelElement> potentialGraphElement = graphElements.stream().filter(ge -> {
                return ge.getPlainAttribute("element").equals(elementId);
            }).findFirst();
            // create the shape
            if (potentialGraphElement.isPresent()) {
                ModelElement graphElement = potentialGraphElement.get();
                GraphicalElementProcessorSQL graphicalElementProcessorSQL = new GraphicalElementProcessorSQL(
                        graphElement.getPlainAttribute("id"), partId, graphModel.getPlainAttribute("id"), type, false);
                graphicalElementProcessorSQL.setModelElement(graphElement);
                graphicalElementProcessorSQL.setKeyLetters("GD_GE");
                graphOutput.add(graphicalElementProcessorSQL.getProcessorOutput());
                graphicalElementProcessorSQL.postprocess(graphElement, "GD_GE");
                // create the lifeline
                ConnectorModel lifeline = GraphicalElementProcessorSQL.createConnector(
                        getModelElement().getPlainAttribute("id"), elementId, null, graphModel.getPlainAttribute("id"),
                        57, 600F);
                graphOutput.add(lifeline.getSql());
            }
        }
        return graphOutput.toString();
    }

    private ModelElement getSourceParticipantPart() {
        ModelElement lifelineElement = getModelElement();
        if (getModelElement().getType().getName().equals("occurrencespec")) {
            lifelineElement = getModelElement().getRefAttribute("covered");
        }
        if (lifelineElement.getType().hasAttribute("represents")) {
            ModelElement representsProperty = lifelineElement.getRefAttribute("represents");
            if (representsProperty != null) {
                ModelElement represents = representsProperty.getRefAttribute("propertytype");
                if (represents != null) {
                    return represents;
                } else {
                    return lifelineElement;
                }
            }
        }
        return null;
    }

    @Override
    public String getSource_Part_ID() {
        ModelElement represents = getSourceParticipantPart();
        if (represents == null) {
            if (getModelElement().getType().getName().equals("lifeline")) {
                represents = getModelElement().getRefAttribute("represents");
            } else {
                if (getModelElement().getType().hasAttribute("covered")) {
                    represents = getModelElement().getRefAttribute("covered");
                } else {
                    represents = getModelElement();
                }
            }
            if (represents != null) {
                return SQLUtils.idValue(represents.getPlainAttribute("id") + packageId
                        + InteractionParticipantProcessorSQL.PARTICIPANT_SUFFIX);
            }
        }
        if (represents != null) {
            return SQLUtils.idValue(represents.getPlainAttribute("id") + packageId
                    + InteractionParticipantProcessorSQL.PARTICIPANT_SUFFIX);
        }
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getDescrip() {
        return SQLUtils.stringValue("EA Element: " + getModelElement().getFullName());
    }

    @Override
    public String getDestroyed() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getPart_ID(), getSource_Part_ID(), getDescrip(), getDestroyed()).collect(Collectors.toList());
    }
}
