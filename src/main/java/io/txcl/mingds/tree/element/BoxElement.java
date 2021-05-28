package io.txcl.mingds.tree.element;

import io.txcl.mingds.record.BoxType;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.SingleKeyMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class BoxElement extends AbstractElement {
    private final Box box;
    private final int boxType;

    public BoxElement(Box box, int boxType) {
        super(new io.txcl.mingds.record.Box());
        this.box = box;
        this.boxType = boxType;
    }

    private List<Vector2D> getVertices() {
        Vector2D lower = box.getLower();
        Vector2D upper = box.getUpper();
        Vector2D m1 = new Vector2D(lower.getX(), upper.getY());
        Vector2D m2 = new Vector2D(upper.getX(), lower.getY());
        return List.of(lower, m1, upper, m2, lower);
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(new BoxType(boxType), new XY(getVertices()));
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        return SingleKeyMap.create(getLayer(), List.of(getVertices()));
    }
}
