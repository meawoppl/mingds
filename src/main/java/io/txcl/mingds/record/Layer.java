package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class Layer extends ShortRecord {
    public Layer() {
        this(new byte[2]);
    }

    public Layer(int layer) {
        this();
        Preconditions.checkArgument(layer <= Short.MAX_VALUE);
        Preconditions.checkArgument(layer >= Short.MIN_VALUE);
        setLayer((short) layer);
    }

    public Layer(byte[] bytes) {
        super(bytes, RecordType.LAYER);
        Preconditions.checkArgument(bytes.length == 2);
    }

    public int getLayer() {
        return getElement(0);
    }

    public void setLayer(short layer) {
        setElement(0, layer);
    }
}
