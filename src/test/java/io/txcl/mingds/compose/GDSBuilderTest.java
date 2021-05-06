package io.txcl.mingds.compose;

import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.KLayoutValidator;
import io.txcl.mingds.validate.ValidationException;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class GDSBuilderTest {
    @Test
    public void testBuilderMinimal(@TempDir Path td) throws IOException, ValidationException {
        GDSStream stream = new GDSBuilder("foo-test-db").stream();

        Path gdsTemp = td.resolve("foo.gds");
        GDSStream.to(gdsTemp, stream);

        new KLayoutValidator().validate(gdsTemp);
    }
}
