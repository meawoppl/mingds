package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class PathType extends ShortRecord {
    public PathType(byte[] bytes) {
        super(bytes, RecordType.PATHTYPE);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
