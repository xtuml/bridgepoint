package org.xtuml.bp.io.xmi.translate.processors.sql.graphical.supporting;

public class GraphicalModel {
    private String id;
    private int type;

    public GraphicalModel(String id, int type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof GraphicalModel) {
            GraphicalModel other = (GraphicalModel) obj;
            return other.getId().equals(getId()) && other.getType() == getType();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getType() * 31;
    }
}
