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
        StringJoiner joiner = new StringJoiner("\n");
        return joiner.add(SQLUtils.getInsertStatement(graphelementProcessorSQL, getModelElement()))
                .add(SQLUtils.getInsertStatement(graphnodeProcessorSQL, getModelElement()))
                .add(SQLUtils.getInsertStatement(diagramelementProcessorSQL, getModelElement()))
                .add(SQLUtils.getInsertStatement(shapeProcessorSQL, getModelElement()))
                .add(SQLUtils.getInsertStatement(noncontainingShapeProcessorSQL, getModelElement())).toString();
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
            default:
                break;
        }
        return 0;
    }

    @Override
    public boolean ignoreTranslation() {
        return getOoaTypeFromOoaId() == 0;
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream
                .of(getelementId(), getdiagramId(), getOOA_ID(), getOOA_Type(), getrepresents(), getrepresents_path())
                .collect(Collectors.toList());
    }
}
