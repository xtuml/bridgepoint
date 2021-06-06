package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;

/**
 * Data is collected during creation of the non-graphical elements.
 * 
 */
public class ConnectionInformation {

    public static Map<String, ConnectionInformation> connectionMap = new HashMap<>();

    public static Map<String, List<ConnectionInformation>> connectionToStartGeMap = new HashMap<>();
    public static Map<String, List<ConnectionInformation>> connectionToEndGeMap = new HashMap<>();

    private String assocId = IdProcessor.NULL_ID;
    private String startEle = IdProcessor.NULL_ID;
    private String endEle = IdProcessor.NULL_ID;
    private GraphconnectorProcessorSQL startCon = null;
    private GraphconnectorProcessorSQL endCon = null;

    public ConnectionInformation(String assocId, String startEle) {
        this.assocId = assocId;
        this.startEle = startEle;
        connectionMap.put(assocId, this);
        // configure a map for start ele to connection for GD_CON
        List<ConnectionInformation> geConnections = connectionToStartGeMap.get(startEle);
        if (geConnections == null) {
            geConnections = new ArrayList<>();
            connectionToStartGeMap.put(startEle, geConnections);
        }
        geConnections.add(this);
    }

    public String getAssocId() {
        return assocId;
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
}
