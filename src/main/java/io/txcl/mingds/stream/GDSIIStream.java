package io.txcl.mingds.stream;

import com.google.common.collect.Streams;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public class GDSIIStream {
    public static Stream<GDSIIRecord<?>> from(Path path) throws IOException {
        return Streams.stream(GDSIIIterator.fromPath(path));
    }

    public static Stream<GDSIIRecord<?>> from(byte[] bytes) {
        return Streams.stream(GDSIIIterator.fromBytes(bytes));
    }

    public static void to(Path path, Stream<GDSIIRecord<?>> recordStream) throws IOException {
        OutputStream fos = new FileOutputStream(path.toFile());
        recordStream.forEach(
                r -> {
                    try {
                        fos.write(r.serialize());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void validate(Stream<GDSIIRecord<?>> recordStream) {}

    public static byte[] serialize(Stream<GDSIIRecord<?>> recordBaseStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        recordBaseStream.forEach(
                r -> {
                    try {
                        baos.write(r.serialize());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        return baos.toByteArray();
    }
}
