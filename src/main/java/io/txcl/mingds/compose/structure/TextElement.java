package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.PathType;
import io.txcl.mingds.record.Presentation;
import io.txcl.mingds.record.Text;
import io.txcl.mingds.record.Width;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.Map;

public class TextElement extends AbstractElement {
    private final int textType;
    private final String string;

    private Presentation presentation;
    private PathType pathType;
    private Width width;

    public TextElement(int textType, String string) {
        super(new Text());
        this.textType = textType;
        this.string = string;
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

    @Override
    protected GDSStream getContents() {
        List<GDSIIRecord<?>> records;



        return null;
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        return null;
    }
}
