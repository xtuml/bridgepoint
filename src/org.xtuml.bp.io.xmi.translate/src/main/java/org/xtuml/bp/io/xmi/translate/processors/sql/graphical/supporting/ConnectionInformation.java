package org.xtuml.bp.io.xmi.translate.processors.sql.graphical.supporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphconnectorProcessorSQL;

/**
 * Data is collected during creation of the non-graphical elements.
 * 
 */
public class ConnectionInformation {

    public static Map<String, ConnectionInformation> connectionMap = new HashMap<>();

    public static Map<String, List<ConnectionInformation>> connectionToStartGeMap = new HashMap<>();
    public static Map<String, List<ConnectionInformation>> connectionToEndGeMap = new HashMap<>();

    private String representsId = IdProcessor.NULL_ID;
    private String startEle = IdProcessor.NULL_ID;
    private String endEle = IdProcessor.NULL_ID;
    private GraphconnectorProcessorSQL startCon = null;
    private GraphconnectorProcessorSQL endCon = null;
    private int ooaType = -1;
    private boolean startsOnWs = false;
    private boolean endsOnWs = false;

    private String ooaId;

    public ConnectionInformation(String representsId, String startEle, int ooaType, String ooaId) {
        this.representsId = representsId;
        this.startEle = startEle;
        this.ooaType = ooaType;
        this.ooaId = ooaId;
        connectionMap.put(representsId, this);
        // configure a map for start ele to connection for GD_CON
        List<ConnectionInformation> geConnections = connectionToStartGeMap.get(startEle);
        if (geConnections == null) {
            geConnections = new ArrayList<>();
            connectionToStartGeMap.put(startEle, geConnections);
        }
        geConnections.add(this);
    }

    public boolean endsOnWs() {
        return endsOnWs;
    }

    public void setEndsOnWs(boolean endsOnWs) {
        this.endsOnWs = endsOnWs;
    }

    public boolean startsOnWs() {
        return startsOnWs;
    }

    public void setStartsOnWs(boolean startsOnWs) {
        this.startsOnWs = startsOnWs;
    }

    public int getOoaType() {
        return ooaType;
    }

    public void setOoaType(int ooaType) {
        this.ooaType = ooaType;
    }

    public String getRepresentsId() {
        return representsId;
    }

    public void setStartEle(String startEle) {
        this.startEle = startEle;
    }

    public String getStartEle() {
        return startEle;
    }

    public void setEndEle(String endEle) {
        // add to the end map
        List<ConnectionInformation> geConnections = connectionToEndGeMap.get(endEle);
        if (geConnections == null) {
            geConnections = new ArrayList<>();
            connectionToEndGeMap.put(endEle, geConnections);
        }
        geConnections.add(this);
        this.endEle = endEle;
    }

    public String getEndEle() {
        return endEle;
    }

    public GraphconnectorProcessorSQL getStartCon() {
        return startCon;
    }

    public void setStartCon(GraphconnectorProcessorSQL startCon) {
        this.startCon = startCon;
    }

    public GraphconnectorProcessorSQL getEndCon() {
        return endCon;
    }

    public void setEndCon(GraphconnectorProcessorSQL endCon) {
        this.endCon = endCon;
    }

    public String getOoaId() {
        if (ooaId != null) {
            return ooaId;
        }
        return null;
    }
}
