package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

public class Rectangle {
    Float x;
    Float y;
    Float w;
    Float h;

    public Rectangle(Float x, Float y, Float w, Float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle() {
    }

    public Float getH() {
        return h;
    }

    public Float getW() {
        return w;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
