package io.txcl.mingds.support;

import io.txcl.mingds.render.Box;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collector;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.Pair;

public class ExtentCollector {
    public static Collector<Vector2D, Pair<DoubleSummaryStatistics, DoubleSummaryStatistics>, Box>
            toBox() {

        return Collector.of(
                () -> Pair.create(new DoubleSummaryStatistics(), new DoubleSummaryStatistics()),
                (Pair<DoubleSummaryStatistics, DoubleSummaryStatistics> pDSS, Vector2D vec) -> {
                    pDSS.getFirst().accept(vec.getX());
                    pDSS.getSecond().accept(vec.getY());
                },
                (pDSS1, pDSS2) -> {
                    DoubleSummaryStatistics xs = new DoubleSummaryStatistics();
                    DoubleSummaryStatistics ys = new DoubleSummaryStatistics();

                    xs.combine(pDSS1.getFirst());
                    xs.combine(pDSS2.getFirst());

                    ys.combine(pDSS1.getSecond());
                    ys.combine(pDSS2.getSecond());

                    return Pair.create(xs, ys);
                },
                (pDSS) -> {
                    Vector2D lower =
                            new Vector2D(pDSS.getFirst().getMin(), pDSS.getSecond().getMin());
                    Vector2D upper =
                            new Vector2D(pDSS.getFirst().getMax(), pDSS.getSecond().getMax());
                    return new Box(lower, upper);
                },
                Collector.Characteristics.UNORDERED);
    }
}
