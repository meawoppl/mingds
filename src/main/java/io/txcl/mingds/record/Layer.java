package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class Layer extends ShortRecord {
    public Layer() {
        this(new byte[2]);
    }

    public Layer(int layer) {
        this();
        setLayer(ByteMunging.requireShort(layer));
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
