package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.IntRecord;
import io.txcl.mingds.record.base.RecordType;

public class Width extends IntRecord {
    public Width(byte[] bytes) {
        super(bytes, RecordType.WIDTH);
        Preconditions.checkArgument(bytes.length == 4);
    }

    public int getWidth() {
        return getElement(1);
    }

    public static Width of(int width) {
        final Width w = new Width(new byte[4]);
        w.setElement(0, width);
        return w;
    }
}
