package mingds.record;

import com.google.common.base.Preconditions;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import mingds.record.base.IntRecord;
import mingds.record.base.RecordType;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class XY extends IntRecord {
    public XY(byte[] bytes) {
        super(bytes, RecordType.XY);
        Preconditions.checkArgument(bytes.length % 8 == 0);
    }

    Vector2D getXY(int index) {
        return new Vector2D(getElement(index * 2), getElement(index * 2 + 1));
    }

    public Stream<Vector2D> getXYs() {
        return IntStream.range(0, nElements() / 2).mapToObj(this::getXY);
    }
}
