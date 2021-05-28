package io.txcl.mingds.tree;

import io.txcl.mingds.record.Angle;
import io.txcl.mingds.record.Mag;
import io.txcl.mingds.record.STrans;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StransHelp {
    private final double magnification;
    private final double angle;
    private final Vector2D shift;
    private final AffineTransform transform;

    public StransHelp(double magnification, double angle, Vector2D shift) {
        this.magnification = magnification;
        this.angle = angle;
        this.shift = shift;

        this.transform = getTransform();
    }

    public StransHelp(double magnification, double angle) {
        this(magnification, angle, Vector2D.ZERO);
    }

    public AffineTransform getTransform() {
        final AffineTransform base = new AffineTransform();
        final AffineTransform mag =
                AffineTransform.getScaleInstance(magnification, magnification);
        final AffineTransform rot =
                AffineTransform.getRotateInstance(angle * Math.PI / 180, 0, 0);
        final AffineTransform trn =
                AffineTransform.getTranslateInstance(shift.getX(), shift.getY());

        // NOTE(meawoppl) not sure on order here...
        base.preConcatenate(mag);
        base.preConcatenate(rot);
        base.preConcatenate(trn);

        return base;
    }

    public List<List<Vector2D>> transformRegion(List<List<Vector2D>> region) {
        return region.stream().map(this::transformPolygon).collect(Collectors.toList());
    }

    public List<Vector2D> transformPolygon(List<Vector2D> polygon) {
        return polygon.stream().map(this::transformPoint).collect(Collectors.toList());
    }

    public Vector2D transformPoint(Vector2D xy){
        Point2D.Double pt = new Point2D.Double(xy.getX(), xy.getY());
        Point2D ptp = transform.transform(pt, null);
        return new Vector2D(ptp.getX(), ptp.getY());
    }

    public GDSStream stream() {
        if (magnification == 1 && angle == 0) {
            return GDSStream.empty();
        }

        List<GDSIIRecord<?>> recs = new ArrayList<>();
        recs.add(new STrans());

        if (magnification != 1) {
            recs.add(new Mag(magnification));
        }

        if (angle != 0) {
            recs.add(new Angle(angle));
        }

        return GDSStream.of(recs);
    }
}
