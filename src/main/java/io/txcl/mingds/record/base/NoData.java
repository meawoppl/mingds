package io.txcl.mingds.record.base;

import com.google.common.base.Preconditions;

public abstract class NoData extends GDSIIRecord<Void> {
    public NoData(byte[] bytes, RecordType rt) {
        super(rt);
        Preconditions.checkArgument(bytes.length == 0);
    }

    @Override
    public GDSIITypes getDataType() {
        return GDSIITypes.NODATA;
    }

    @Override
    public Void getElement(int i) {
        throw new RuntimeException("?");
    }

    @Override
    public void setElement(int i, Void v) {
        throw new RuntimeException("?");
    }
}
