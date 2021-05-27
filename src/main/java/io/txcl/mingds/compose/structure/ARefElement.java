package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.ARef;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.Map;

public class ARefElement extends AbstractRefElement {
    public ARefElement(String name) {
        super(new ARef(), name);
    }

    @Override
    protected GDSStream getContents() {
        return null;
    }

    @Override
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        return null;
    }
}
