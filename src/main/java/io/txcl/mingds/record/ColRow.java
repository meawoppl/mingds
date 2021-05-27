package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class ColRow extends ShortRecord {
    private static final int MAX_ROW_COL = 32767;
    public ColRow(int nCols, int nRows){
        this();
        Preconditions.checkArgument(nCols > 0);
        Preconditions.checkArgument(nRows > 0);
        Preconditions.checkArgument(nCols <= 32,MAX_ROW_COL);
        Preconditions.checkArgument(nRows <= 32,MAX_ROW_COL);

        setElement(0, ByteMunging.requireShort(nCols));
        setElement(1, ByteMunging.requireShort(nRows));
    }
    public ColRow() {
        super(new byte[4], RecordType.COLROW);
    }

    public ColRow(byte[] bytes){
        super(bytes, RecordType.COLROW);
        Preconditions.checkArgument(bytes.length==4);

    }


}
