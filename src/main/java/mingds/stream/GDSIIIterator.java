package mingds.stream;

import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import mingds.format.ByteMunging;
import mingds.record.base.GDSIIRecord;
import mingds.record.base.RecordType;

public class GDSIIIterator implements Iterator<GDSIIRecord<?>> {
    private final DataInputStream dis;
    private final AtomicInteger ai;

    public static GDSIIIterator fromBytes(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);
        return new GDSIIIterator(dis);
    }

    public static GDSIIIterator fromPath(Path path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path.toFile());
        DataInputStream dis = new DataInputStream(fis);
        return new GDSIIIterator(dis);
    }

    public GDSIIIterator(DataInputStream dis) {
        this.dis = dis;
        this.ai = new AtomicInteger(0);
    }

    public GDSIIRecord<?> next() {
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

    public GDSIIRecord<?> nextRecord() throws IOException {
        GDSIIRecord<?> ret = GDSIIRecord.deserialize(nextBlock());

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
