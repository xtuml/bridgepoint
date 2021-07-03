package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

public class ConnectorDescription {
    public Polyline line;
    public Edge edge;

    public ConnectorDescription(Polyline line, Edge edge) {
        this.line = line;
        this.edge = edge;
    }

    @Override
    public String toString() {
        return "{ polyline: " + line.toString() + ", edge: " + edge.toString() + " }";
    }
}
