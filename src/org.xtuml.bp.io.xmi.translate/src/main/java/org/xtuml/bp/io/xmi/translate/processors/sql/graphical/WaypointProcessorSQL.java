package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractWaypointProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class WaypointProcessorSQL extends AbstractWaypointProcessor {

    private Float x;
    private Float y;
    private String prevId;
    private String Id;
    private String geId;

    public WaypointProcessorSQL(Float x, Float y, String prevId) {
        this.x = x;
        this.y = y;
        this.prevId = prevId;
        this.Id = IdProcessor.UUID().toString();
    }

    public WaypointProcessorSQL(Float x, Float y, String prevId, String geId) {
        this.x = x;
        this.y = y;
        this.prevId = prevId;
        this.Id = IdProcessor.UUID().toString();
        this.geId = geId;
    }

    public String getId() {
        return Id;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String getWay_ID() {
        return SQLUtils.idValue(Id);
    }

    @Override
    public String getpositionX() {
        return SQLUtils.numberValue(x);
    }

    @Override
    public String getpositionY() {
        return SQLUtils.numberValue(y);
    }

    @Override
    public String getedge_elementId() {
        if (geId != null) {
            return SQLUtils.idValue(geId);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getpolyLine_elementId() {
        return SQLUtils.idValue(IdProcessor.NULL_ID);
    }

    @Override
    public String getprevious_Way_ID() {
        return SQLUtils.idValue(prevId);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getWay_ID(), getpositionX(), getpositionY(), getedge_elementId(), getpolyLine_elementId(),
                getprevious_Way_ID()).collect(Collectors.toList());
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }
}
