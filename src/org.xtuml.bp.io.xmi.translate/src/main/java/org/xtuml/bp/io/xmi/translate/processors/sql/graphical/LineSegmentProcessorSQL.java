package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractLineSegmentProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class LineSegmentProcessorSQL extends AbstractLineSegmentProcessor {

    private String startWay;
    private String endWay;
    private String Id;
    private String conId;
    private String prevElementId = IdProcessor.NULL_ID;

    public LineSegmentProcessorSQL(String conId, String startWay, String endWay) {
        this.conId = conId;
        this.startWay = startWay;
        this.endWay = endWay;
        this.Id = IdProcessor.UUID().toString();
    }

    @Override
    public String getelementId() {
        return SQLUtils.idValue(Id);
    }

    @Override
    public String getconn_elementId() {
        return SQLUtils.idValue(conId);
    }

    @Override
    public String getPrevious_elementId() {
        // most connectors only have one line segment at this
        // time, in the case where there are multiple they
        // are self created and use the set method
        return SQLUtils.idValue(prevElementId);
    }

    public void setPrevious_elementId(String prevElementId) {
        this.prevElementId = prevElementId;
    }

    @Override
    public String getstart_Way_ID() {
        return SQLUtils.idValue(startWay);
    }

    @Override
    public String getend_Way_ID() {
        return SQLUtils.idValue(endWay);
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream
                .of(getelementId(), getconn_elementId(), getPrevious_elementId(), getstart_Way_ID(), getend_Way_ID())
                .collect(Collectors.toList());
    }

    public String getId() {
        return Id;
    }

    public void setStartWay(String startWay) {
        this.startWay = startWay;
    }

    public void setEndWay(String endWay) {
        this.endWay = endWay;
    }

	public String getEndWay() {
		return this.endWay;
	}
}
