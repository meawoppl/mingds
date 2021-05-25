package io.txcl.mingds.interfaces;

import java.util.List;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public interface Renderable extends HasExtents {
    Map<Integer, List<List<Vector2D>>> getPolygons();
}
