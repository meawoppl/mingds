package io.txcl.mingds.tree.structure;

import io.txcl.mingds.tree.StransHelp;
import io.txcl.mingds.geom.TextSupport;
import io.txcl.mingds.record.PathType;
import io.txcl.mingds.record.Presentation;
import io.txcl.mingds.record.StringRecord;
import io.txcl.mingds.record.Text;
import io.txcl.mingds.record.TextType;
import io.txcl.mingds.record.Width;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.SingleKeyMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class TextElement extends AbstractElement {
    private final int textType;
    private final String string;
    private final Vector2D position;

    private Presentation presentation;
    private PathType pathType;
    private Width width;

    // STrans data
    private double magnification;
    private double rotation;

    public TextElement(int textType, String string, Vector2D position) {
        super(new Text());
        this.textType = textType;
        this.string = string;
        this.position = position;

        this.magnification = 1;
        this.rotation = 0;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public void setPathType(PathType pathType) {
        this.pathType = pathType;
    }

    public void setWidth(Width width) {
        this.width = width;
    }

    public void setMagnification(double magnification) {
        this.magnification = magnification;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    protected GDSStream getContents() {
        List<GDSIIRecord<?>> records = new ArrayList<>();
        records.add(new TextType(textType));

        if (presentation != null) {
            records.add(presentation);
        }

        if (pathType != null) {
            records.add(pathType);
        }

        if (width != null) {
            records.add(width);
        }

        StransHelp stransHelp = new StransHelp(magnification, rotation);
        records.addAll(stransHelp.stream().collect(Collectors.toList()));

        records.add(new XY(position));
        records.add(new StringRecord(string));

        return GDSStream.of(records);
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        int size;
        if (width == null) {
            size = 1;
        } else {
            size = width.getWidth();
        }

        List<List<Vector2D>> polygons = TextSupport.textToPolygons(string, size, position);
        return SingleKeyMap.create(getLayer(), polygons);
    }
}
