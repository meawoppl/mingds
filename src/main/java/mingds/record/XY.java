package mingds.record;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import mingds.record.base.IntRecord;
import mingds.record.base.RecordType;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class XY extends IntRecord {
    public XY(List<Vector2D> points){
        super(new byte[4*2*points.size()], RecordType.XY);
        // Check maximum point count
        Preconditions.checkArgument(points.size() <= 50);
        // Make sure first and last point are the same
        Preconditions.checkArgument(points.get(0).equals(points.get(points.size()-1)));

        for (int i = 0; i < points.size(); i++) {
            Vector2D v = points.get(i);
            setElement(i*2, (int) Math.round(v.getX()));
            setElement((i*2)+1, (int) Math.round(v.getY()));
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
