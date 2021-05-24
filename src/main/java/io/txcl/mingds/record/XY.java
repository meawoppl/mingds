package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.IntRecord;
import io.txcl.mingds.record.base.RecordType;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class XY extends IntRecord {
    public static final int MAX_XYS = 8191;
    public static final AtomicBoolean oversizeWarning = new AtomicBoolean(false);

    public XY(List<Vector2D> points) {
        super(new byte[4 * 2 * points.size()], RecordType.XY);
        // Check maximum point count
        if (points.size() > 50 && !oversizeWarning.get()) {
            System.err.println("Warning: XY with more than 50 points. This limits compatibility");
            oversizeWarning.set(true);
        }

        Preconditions.checkArgument(
                points.size() <= MAX_XYS,
                String.format("Expected <= %d points, got %d", MAX_XYS, points.size()));

        for (int i = 0; i < points.size(); i++) {
            Vector2D v = points.get(i);
            setElement(i * 2, (int) Math.round(v.getX()));
            setElement((i * 2) + 1, (int) Math.round(v.getY()));
        }
    }

    public XY(byte[] bytes) {
        super(bytes, RecordType.XY);
    }

    Vector2D getXY(int index) {
        return new Vector2D(getElement(index * 2), getElement(index * 2 + 1));
    }

    public Stream<Vector2D> getXYs() {
        return IntStream.range(0, nElements() / 2).mapToObj(this::getXY);
    }
}
