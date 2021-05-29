package io.txcl.mingds.tree.element;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.SRef;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.tree.StransHelp;
import io.txcl.mingds.tree.Structure;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class SRefElement extends AbstractRefElement {
    private final Vector2D position;
    private Structure structure;

    public SRefElement(String name) {
        this(name, Vector2D.ZERO);
    }

    public SRefElement(String name, Vector2D position) {
        super(new SRef(), name);
        this.position = position;
    }

    public static SRefElement toStructure(Structure structure) {
        final SRefElement ref = new SRefElement(structure.getName());
        ref.setStructure(structure);
        return ref;
    }

    public SRefElement(String name, Vector2D position, double magnification, double angle) {
        super(new SRef(), name, magnification, angle);
        this.position = position;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public SRefElement translated(Vector2D translation) {
        final SRefElement ref =
                new SRefElement(
                        getName(), position.add(translation), getMagnification(), getAngle());
        ref.setStructure(structure);
        return ref;
    }

    public SRefElement rotated(double degrees) {
        final SRefElement ref =
                new SRefElement(getName(), position, getMagnification(), getAngle() + degrees);
        ref.setStructure(structure);
        return ref;
    }

    public SRefElement scaled(double scale) {
        final SRefElement ref =
                new SRefElement(getName(), position, getMagnification() * scale, getAngle());
        ref.setStructure(structure);
        return ref;
    }

    @Override
    protected GDSStream getContents() {
        XY xy = new XY(position);
        return getRefComponents().concat(xy);
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        if (structure == null) {
            System.err.println("WARNING: Tried to render unreferenced structure. Skipping");
            // TODO(meawoppl) maybe render a warning anchor here?
            return new HashMap<>();
        }

        Map<Integer, List<List<Vector2D>>> polygons = new HashMap<>();

        structure
                .getElements()
                .forEach(
                        element -> {
                            element.getPolygons()
                                    .forEach(
                                            (layer, polys) -> {
                                                if (!polygons.containsKey(layer)) {
                                                    polygons.put(layer, new ArrayList<>());
                                                }
                                                polygons.get(layer).addAll(polys);
                                            });
                        });

        return polygons;
    }

    private List<Vector2D> transformPolygon(List<Vector2D> polygon) {
        StransHelp stransHelp = new StransHelp(this.getMagnification(), getAngle(), Vector2D.ZERO);
        AffineTransform base = stransHelp.getTransform();
        return polygon.stream()
                .map(
                        xy -> {
                            Point2D.Double pt = new Point2D.Double(xy.getX(), xy.getY());
                            Point2D ptp = base.transform(pt, null);
                            return new Vector2D(ptp.getX(), ptp.getY());
                        })
                .collect(Collectors.toList());
    }

    public static SRefElement fromSRefContext(GdsiiParser.SrefElementContext ctx){
        ctx.
    }
}
