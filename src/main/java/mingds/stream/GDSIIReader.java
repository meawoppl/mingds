package mingds.stream;

import com.google.common.collect.Streams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;
import mingds.record.base.RecordBase;

public class GDSIIReader {
    public static Stream<RecordBase<?>> from(Path path) throws IOException {
        return from(path.toFile());
    }

    public static Stream<RecordBase<?>> from(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        return Streams.stream(new GDSIIIterator(dis));
    }

    public static Stream<RecordBase<?>> from(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);
        return Streams.stream(new GDSIIIterator(dis));
    }

    public static void to(Path path, Stream<RecordBase<?>> recordStream)
            throws FileNotFoundException, IOException {
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

    public static byte[] serialize(Stream<RecordBase<?>> recordBaseStream) {
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
