package io.txcl.mingds.compose.structure;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.*;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.PathDecoder;
import io.txcl.mingds.support.SingleKeyMap;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PathElement extends AbstractElement {
    private final int dataType;
    private final int pathType;
    private final int width;
    private final List<Vector2D> xy;

    public PathElement(int dataType, int pathType, int width, List<Vector2D> xy) {
        super(new Path());
        this.dataType = dataType;
        this.pathType = pathType;
        this.width = width;
        this.xy = xy;
        Preconditions.checkArgument(xy.size() >= 2);
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(DType.of(dataType), PathType.of(pathType), Width.of(width), new XY(xy));
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        // TODO(meawoppl) foist to static
        Map<Integer, Integer> gdsPathTypeToAwtCapType = new HashMap<>();
        gdsPathTypeToAwtCapType.put(0, BasicStroke.CAP_BUTT);
        gdsPathTypeToAwtCapType.put(1, BasicStroke.CAP_SQUARE);
        gdsPathTypeToAwtCapType.put(2, BasicStroke.CAP_ROUND);

        BasicStroke stroke =
                new BasicStroke(
                        width,
                        gdsPathTypeToAwtCapType.get(pathType),
                        pathType == 2 ? BasicStroke.JOIN_ROUND : BasicStroke.JOIN_MITER);

        Path2D.Double path = new Path2D.Double();
        path.setWindingRule(Path2D.WIND_NON_ZERO);
        Vector2D first = xy.get(0);
        path.moveTo(first.getX(), first.getY());
        xy.forEach(v -> path.lineTo(v.getX(), v.getY()));

        Shape outlineShape = stroke.createStrokedShape(path);
        PathIterator pathIterator =
                outlineShape.getPathIterator(new AffineTransform(1, 0, 0, -1, 0, 0));

        return SingleKeyMap.create(getLayer(), PathDecoder.processPathIterator(pathIterator));
    }
}
