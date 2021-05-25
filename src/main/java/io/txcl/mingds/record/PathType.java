package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class PathType extends ShortRecord {
    public PathType() {
        this(new byte[2]);
    }

    public PathType(byte[] bytes) {
        super(bytes, RecordType.PATHTYPE);
        Preconditions.checkArgument(bytes.length == 2);
    }

    public static PathType of(int pathType) {
        final PathType pt = new PathType();
        pt.setElement(0, ByteMunging.requireShort(pathType));
        return pt;
    }
}
