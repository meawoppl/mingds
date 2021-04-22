package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Angle extends DoubleRecord {
    public Angle() {
        this(new byte[8]);
    }

    public Angle(byte[] bytes) {
        super(bytes, RecordType.ANGLE);
        Preconditions.checkArgument(bytes.length == 8);
    }

    /**
     * Eight-Byte Real Contains a double-precision real number (8 bytes), which is the angular
     * rotation factor. The angle of rotation is measured in degrees and in the counterclockwise
     * direction. For an ARef, the ANGLE rotates the entire array (with the individual array members
     * rigidly attached) about the array reference point. For COLROW record information, the angle
     * of rotation is already included in the coordinates. If this record is omitted, an angle of
     * zero degrees is assumed.
     *
     * @return
     */
    public double getAngle() {
        return getElement(0);
    }
}
