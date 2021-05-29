package io.txcl.mingds.tree.element;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.*;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.SingleKeyMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class BoxElement extends AbstractElement {
    private final int boxType;
    private final List<Vector2D> xys;

    public BoxElement(Box box, int boxType) {
        super(new io.txcl.mingds.record.Box());
        this.xys = fromBoxCoords(box.getLower(), box.getUpper());
        this.boxType = boxType;
    }

    public BoxElement(int boxType, List<Vector2D> xy){
        super(new io.txcl.mingds.record.Box());
        this.xys = xy;
        this.boxType = boxType;
    }

    private List<Vector2D> fromBoxCoords(Vector2D lower, Vector2D upper) {
        Vector2D m1 = new Vector2D(lower.getX(), upper.getY());
        Vector2D m2 = new Vector2D(upper.getX(), lower.getY());
        return List.of(lower, m1, upper, m2, lower);
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(new BoxType(boxType), new XY(this.xys));
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        return SingleKeyMap.create(getLayer(), List.of(this.xys));
    }

    public static BoxElement fromParseContext(GdsiiParser.BoxElementContext ctx){
        BoxType boxType = (BoxType) ctx.boxtype().start;
        Layer layerRec = (Layer) ctx.layer().start;
        XY xy = (XY) ctx.xy().start;

        final BoxElement boxElement = new BoxElement(boxType.getBoxType(), xy.getXYs().collect(Collectors.toList()));
        boxElement.setLayer(layerRec.getLayer());

        if(ctx.plex() != null){
            boxElement.setPlex((Plex) ctx.plex().start);
        }

        if(ctx.elflags() != null){
            boxElement.setElflags((ElFlags) ctx.elflags().start);
        }

        return boxElement;
    }
}
