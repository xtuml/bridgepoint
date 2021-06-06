package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractGraphicalElementProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class GraphicalElementProcessorSQL extends AbstractGraphicalElementProcessor {

    @Override
    public String getelementId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getdiagramId() {
        return SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getOOA_ID() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("element"));
    }

    @Override
    public String getOOA_Type() {
        int ooaType = getOoaTypeFromOoaId();
        return SQLUtils.numberValue(ooaType);
    }

    @Override
    public String getrepresents() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getrepresents_path() {
        return SQLUtils.stringValue("");
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public String createSupportingElements() {
        String boundsString = getModelElement().getPlainAttribute("bounds");
        if (isShape()) {
            Rectangle rect = getBoundsFromString(boundsString);
            GraphelementProcessorSQL graphelementProcessorSQL = new GraphelementProcessorSQL(rect.x, rect.y);
            graphelementProcessorSQL.setModelElement(getModelElement());
            graphelementProcessorSQL.setKeyLetters("DIM_GE");
            GraphnodeProcessorSQL graphnodeProcessorSQL = new GraphnodeProcessorSQL(rect.w, rect.h);
            graphnodeProcessorSQL.setModelElement(getModelElement());
            graphnodeProcessorSQL.setKeyLetters("DIM_ND");
            DiagramelementProcessorSQL diagramelementProcessorSQL = new DiagramelementProcessorSQL();
            diagramelementProcessorSQL.setModelElement(getModelElement());
            diagramelementProcessorSQL.setKeyLetters("DIM_ELE");
            ShapeProcessorSQL shapeProcessorSQL = new ShapeProcessorSQL();
            shapeProcessorSQL.setModelElement(getModelElement());
            shapeProcessorSQL.setKeyLetters("GD_SHP");
            NoncontainingShapeProcessorSQL noncontainingShapeProcessorSQL = new NoncontainingShapeProcessorSQL();
            noncontainingShapeProcessorSQL.setModelElement(getModelElement());
            noncontainingShapeProcessorSQL.setKeyLetters("GD_NCS");
            StringBuilder builder = new StringBuilder(
                    SQLUtils.getInsertStatement(graphelementProcessorSQL, getModelElement()));
            // setup DIM_CON
            List<ConnectionInformation> startConnections = ConnectionInformation.connectionToStartGeMap
                    .get(getModelElement().getPlainAttribute("element"));
            if (startConnections != null) {
                startConnections.forEach(conn -> {
                    GraphconnectorProcessorSQL graphCon = new GraphconnectorProcessorSQL(
                            getModelElement().getPlainAttribute("id"), rect.getMidPoint().x, rect.getMidPoint().y);
                    graphCon.setKeyLetters("DIM_CON");
                    if (conn.getStartEle().equals(getModelElement().getPlainAttribute("element"))) {
                        conn.setStartCon(graphCon);
                    }
                    builder.append(SQLUtils.getInsertStatement(graphCon, getModelElement()));
                });
            }
            List<ConnectionInformation> endConnections = ConnectionInformation.connectionToEndGeMap
                    .get(getModelElement().getPlainAttribute("element"));
            if (endConnections != null) {
                endConnections.forEach(conn -> {
                    GraphconnectorProcessorSQL graphCon = new GraphconnectorProcessorSQL(
                            getModelElement().getPlainAttribute("id"), rect.getMidPoint().x, rect.getMidPoint().y);
                    graphCon.setKeyLetters("DIM_CON");
                    if (conn.getEndEle().equals(getModelElement().getPlainAttribute("element"))) {
                        conn.setEndCon(graphCon);
                    }
                    builder.append(SQLUtils.getInsertStatement(graphCon, getModelElement()));
                });
            }
            builder.append(SQLUtils.getInsertStatement(graphnodeProcessorSQL, getModelElement()))
                    .append(SQLUtils.getInsertStatement(diagramelementProcessorSQL, getModelElement()))
                    .append(SQLUtils.getInsertStatement(shapeProcessorSQL, getModelElement()))
                    .append(SQLUtils.getInsertStatement(noncontainingShapeProcessorSQL, getModelElement())).toString();
            return builder.toString();
        } else {
            ConnectorProcessorSQL connectorProcessorSQL = new ConnectorProcessorSQL();
            connectorProcessorSQL.setModelElement(getModelElement());
            connectorProcessorSQL.setKeyLetters("GD_CON");
            StringBuilder builder = new StringBuilder();
            ConnectionInformation information = ConnectionInformation.connectionMap
                    .get(getModelElement().getPlainAttribute("element"));
            String startId = IdProcessor.NULL_ID;
            String endId = IdProcessor.NULL_ID;
            if (information != null) {
                GraphconnectorProcessorSQL startCon = information.getStartCon();
                GraphconnectorProcessorSQL endCon = information.getEndCon();
                if (startCon != null && endCon != null) {
                    startId = information.getStartCon().getId();
                    endId = information.getEndCon().getId();
                    // create line segment and waypoints
                    WaypointProcessorSQL startWaypointProcessorSQL = new WaypointProcessorSQL(startCon.x, startCon.y,
                            IdProcessor.NULL_ID);
                    startWaypointProcessorSQL.setKeyLetters("DIM_WAY");
                    startWaypointProcessorSQL.setModelElement(getModelElement());
                    WaypointProcessorSQL endWayPointProcessorSQL = new WaypointProcessorSQL(endCon.x, endCon.y,
                            startWaypointProcessorSQL.getId());
                    endWayPointProcessorSQL.setKeyLetters("DIM_WAY");
                    endWayPointProcessorSQL.setModelElement(getModelElement());
                    LineSegmentProcessorSQL lineSegProcessorSQL = new LineSegmentProcessorSQL(
                            startWaypointProcessorSQL.getId(), endWayPointProcessorSQL.getId());
                    lineSegProcessorSQL.setKeyLetters("GD_LS");
                    lineSegProcessorSQL.setModelElement(getModelElement());
                    builder.append(SQLUtils.getInsertStatement(lineSegProcessorSQL, getModelElement()))
                            .append(SQLUtils.getInsertStatement(startWaypointProcessorSQL, getModelElement()))
                            .append(SQLUtils.getInsertStatement(endWayPointProcessorSQL, getModelElement()));
                }
            }
            GraphedgeProcessorSQL graphEdgeProcessor = new GraphedgeProcessorSQL(startId, endId);
            graphEdgeProcessor.setModelElement(getModelElement());
            graphEdgeProcessor.setKeyLetters("DIM_ED");
            return builder.append(SQLUtils.getInsertStatement(connectorProcessorSQL, getModelElement()))
                    .append(SQLUtils.getInsertStatement(graphEdgeProcessor, getModelElement())).toString();
        }
    }

    private Rectangle getBoundsFromString(String boundsString) {
        String[] attributes = boundsString.split(";");
        Map<String, String> values = new HashMap<>();
        Stream.of(attributes).forEach(a -> {
            String[] kv = a.split("=");
            if (kv.length == 2) {
                values.put(kv[0], kv[1]);
            }
        });
        Float x = values.get("Left") != null ? Float.valueOf(values.get("Left")) : 0f;
        Float y = values.get("Top") != null ? Float.valueOf(values.get("Top")) : 0f;
        Float right = values.get("Right") != null ? Float.valueOf(values.get("Right")) : 0f;
        Float bottom = values.get("Bottom") != null ? Float.valueOf(values.get("Bottom")) : 0f;
        Float w = right - x > 0 ? right - x : 100f;
        Float h = bottom - y > 0 ? bottom - y : 100f;
        return new Rectangle(x, y, w, h);
    }

    public boolean isShape() {
        String keyLetters = IdProcessor.elementIdKeyLetts
                .get(UUID.fromString(IdProcessor.process(getModelElement().getPlainAttribute("element"), null)));
        if (keyLetters == null) {
            keyLetters = "";
        }
        switch (keyLetters) {
            case "R_REL":
                return false;
        }
        return true;
    }

    private int getOoaTypeFromOoaId() {
        String keyLetters = IdProcessor.elementIdKeyLetts
                .get(UUID.fromString(IdProcessor.process(getModelElement().getPlainAttribute("element"), null)));
        if (keyLetters == null) {
            keyLetters = "";
        }
        switch (keyLetters) {
            case "C_C":
                return 98;
            case "C_I":
                return 96;
            case "O_OBJ":
                return 21;
            case "PE_C":
                return 210;
            case "S_UDT":
                return 51;
            case "IA_UCP":
                return 85;
            case "SQ_AP":
                return 65;
            case "S_EDT":
                return 52;
            case "SM_STATE":
                return 41;
            case "R_REL":
                return 24;
            default:
                break;
        }
        return 0;
    }

    @Override
    public IgnoreType ignoreTranslation() {
        return getOoaTypeFromOoaId() == 0 ? IgnoreType.NOT_HANDLED : IgnoreType.NOT_IGNORED;
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream
                .of(getelementId(), getdiagramId(), getOOA_ID(), getOOA_Type(), getrepresents(), getrepresents_path())
                .collect(Collectors.toList());
    }
}
