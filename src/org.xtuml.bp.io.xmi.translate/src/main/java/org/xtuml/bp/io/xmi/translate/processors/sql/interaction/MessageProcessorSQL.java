package org.xtuml.bp.io.xmi.translate.processors.sql.interaction;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractMessageProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ConnectorModel;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.AsynchronousMessageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.ReturnMessageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting.SynchronousMessageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.supporting.PackageableElementProcessorSQL;

public class MessageProcessorSQL extends AbstractMessageProcessor {

    private String senderPartId;
    private String receiverPartId;
    private String packageId;
    private int graphType;

    static ModelElement owner = null;

    private int count;
    public static Float lastYDiff = 0F;

    public MessageProcessorSQL(String senderPartId, String receiverPartId, int count, String packageId) {
        this.senderPartId = senderPartId;
        this.receiverPartId = receiverPartId;
        this.count = count;
        this.packageId = packageId;
    }

    @Override
    public String createSupportingElements() {
        StringJoiner support = new StringJoiner("");
        PackageableElementProcessorSQL packageableElementProcessorSQL = new PackageableElementProcessorSQL(
                IdProcessor.process(getModelElement().getPlainAttribute("id")), packageId, 17);
        packageableElementProcessorSQL.setKeyLetters("PE_PE");
        support.add(packageableElementProcessorSQL.getProcessorOutput());
        String type = getModelElement().getPlainAttribute("sort");
        switch (type) {
            case "synchCall":
                SynchronousMessageProcessorSQL synchronousMessageProcessorSQL = new SynchronousMessageProcessorSQL(
                        count);
                synchronousMessageProcessorSQL.setModelElement(getModelElement());
                synchronousMessageProcessorSQL.setKeyLetters("MSG_SM");
                support.add(synchronousMessageProcessorSQL.getProcessorOutput());
                graphType = 60;
                break;
            case "asynchSignal":
                AsynchronousMessageProcessorSQL asynchronousMessageProcessorSQL = new AsynchronousMessageProcessorSQL(
                        count);
                asynchronousMessageProcessorSQL.setModelElement(getModelElement());
                asynchronousMessageProcessorSQL.setKeyLetters("MSG_AM");
                support.add(asynchronousMessageProcessorSQL.getProcessorOutput());
                graphType = 66;
                break;
            case "reply":
                ReturnMessageProcessorSQL returnMessageProcessorSQL = new ReturnMessageProcessorSQL(count);
                returnMessageProcessorSQL.setModelElement(getModelElement());
                returnMessageProcessorSQL.setKeyLetters("MSG_R");
                support.add(returnMessageProcessorSQL.getProcessorOutput());
                graphType = 67;
                break;
            default:
                break;
        }
        // create the message graphics
        support.add(createGraphics());
        return support.toString();
    }

    private String createGraphics() {
        StringJoiner graphOutput = new StringJoiner("");
        // find the model instance associated with this
        Optional<ModelElement> potentialModel = XMITranslate.graphicElements.keySet().stream()
                .filter(gd -> gd.getType().getMapping().equals("GD_MD")
                        && gd.getPlainAttribute("parentAsRepresents").equals(packageId))
                .findFirst();
        XMITranslate.logger.debug(XMITranslate.graphicElements.entrySet().size() == 0
                ? "ERROR: Trying to locate home diagram for messages before graphics have been created!"
                : null);
        if (potentialModel.isPresent()) {
            ModelElement graphModel = potentialModel.get();
            // create the connector
            ConnectorModel message = GraphicalElementProcessorSQL.createConnector(
                    getModelElement().getPlainAttribute("id"), senderPartId, receiverPartId,
                    graphModel.getPlainAttribute("id"), graphType, (lastYDiff + 30F) * count);
            lastYDiff = message.getLastYDiff();
            graphOutput.add(message.getSql());
        }
        return graphOutput.toString();
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public String getMsg_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getReceiver_Part_ID() {
        return SQLUtils.idValue(receiverPartId);
    }

    @Override
    public String getSender_Part_ID() {
        return SQLUtils.idValue(senderPartId);
    }

    @Override
    public String getparticipatesInCommunication() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getMsg_ID(), getReceiver_Part_ID(), getSender_Part_ID(), getparticipatesInCommunication())
                .collect(Collectors.toList());
    }
}
