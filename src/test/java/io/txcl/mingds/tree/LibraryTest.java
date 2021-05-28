package io.txcl.mingds.tree;

import io.txcl.mingds.GDSTestFiles;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.KLayoutValidator;
import io.txcl.mingds.validate.ValidationException;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LibraryTest {
    @Test
    public void testBuilderMinimal(@TempDir Path td) throws IOException, ValidationException {
        GDSStream stream = new Library("foo-test-db").stream();

        Path gdsTemp = td.resolve("foo.gds");
        GDSStream.to(gdsTemp, stream);

        new KLayoutValidator().validate(gdsTemp);
    }

    @Test
    public void testFromPath() throws Exception {

        final Path path = GDSTestFiles.getTestPaths().findFirst().get();
        Library.fromPath(path);
    }

}
