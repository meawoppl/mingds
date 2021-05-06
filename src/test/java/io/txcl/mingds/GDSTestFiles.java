package io.txcl.mingds;

import io.txcl.mingds.stream.GDSIIStreamsTest;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GDSTestFiles {
    public static Stream<Path> getTestPaths() throws Exception {
        Path root = Path.of("src/test/resources/gds_examples");
        return Files.list(root)
                .map(path -> "/gds_examples/" + path.getFileName().toString())
                .sorted()
                .map(
                        s -> {
                            URL resource = GDSIIStreamsTest.class.getResource(s);
                            Path p;
                            try {
                                p = Paths.get(resource.toURI());
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            return p;
                        });
    }

    @Test
    public void testGetTests() throws Exception {
        Assertions.assertTrue(GDSTestFiles.getTestPaths().count() > 6);
    }
}
