package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

public class GraphicalElement {
    private String id;
    private String represents;
    private Rectangle rect;
    private int type;

    public GraphicalElement(String id, String represents, Rectangle rect, int type) {
        this.id = id;
        this.represents = represents;
        this.rect = rect;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getRepresents() {
        return represents;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getType() {
        return type;
    }

    public boolean isConnector() {
        return type == 24 || type == 36 || type == 35 || type == 34 || type == 57;
    }

    public boolean isLifespan() {
        return type == 57;
    }
}
