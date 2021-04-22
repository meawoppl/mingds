package mingds.stream;

import com.google.common.base.Preconditions;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import mingds.format.ByteMunging;
import mingds.record.base.RecordBase;
import mingds.record.base.RecordType;

public class GDSIIIterator implements Iterator<RecordBase<?>> {
    private final DataInputStream dis;
    private final AtomicInteger ai;

    public GDSIIIterator(DataInputStream dis) {
        this.dis = dis;
        this.ai = new AtomicInteger(0);
    }

    public RecordBase<?> next() {
        try {
            ai.incrementAndGet();
            return nextRecord();
        } catch (Exception e) {
            System.err.printf("Failure while processing record number %d\n", ai.get());
            throw new RuntimeException(e);
        }
    }

    public byte[] nextBlock() throws IOException {
        byte[] encodedLength = dis.readNBytes(2);
        Preconditions.checkArgument(
                encodedLength.length == 2, "Attempted to read with no records remaining.");
        int recordLength = ByteMunging.toDataInputStream(encodedLength).readUnsignedShort();
        byte[] encoded = new byte[recordLength];
        encoded[0] = encodedLength[0];
        encoded[1] = encodedLength[1];
        int readBytes = dis.readNBytes(encoded, 2, recordLength - 2);
        Preconditions.checkArgument(readBytes == recordLength - 2, "Record appears truncated");

        return encoded;
    }

    public RecordBase<?> nextRecord() throws IOException {
        RecordBase<?> ret = RecordBase.deserialize(nextBlock());

        // Sometimes GDSII files are padded to the closest 2048 bytes with nulls.
        // If we encounter an ENDLIB, consume the remaining bytes, and throw if
        // you find something else.
        if (RecordType.ENDLIB == ret.getRecordType()) {
            while (dis.available() > 0) {
                Preconditions.checkArgument(
                        dis.read() == 0, "Unexpected padding value after ENDLIB");
            }
        }

        return ret;
    }

    @Override
    public boolean hasNext() {
        try {
            return dis.available() > 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
