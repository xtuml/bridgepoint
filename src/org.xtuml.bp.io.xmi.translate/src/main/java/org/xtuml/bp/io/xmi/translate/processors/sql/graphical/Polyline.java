package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Polyline {
    List<Point> points;

    public Polyline(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public String toString() {
        return Stream.of(points).map(Object::toString).collect(Collectors.joining(", "));
    }
}
