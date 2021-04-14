package mingds;

import com.google.common.collect.Streams;
import mingds.record.base.RecordBase;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class GDSIIReader {
    public static Stream<RecordBase<?>> from(Path path) throws IOException {
        return from(path.toFile());
    }

    public static Stream<RecordBase<?>> from(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        return Streams.stream(new GDSIIIterator(dis));
    }

}
