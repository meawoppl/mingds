package io.txcl.mingds.validate;

import io.txcl.mingds.GDSTestFiles;
import io.txcl.mingds.compose.GDSBuilder;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class KLayoutValidatorTest extends GDSTestFiles {
    @Test
    public void testKLayoutInstalled() {
        KLayoutValidator.isKLayoutAvailable();
        // Test the memoized path too
        KLayoutValidator.isKLayoutAvailable();
    }

    @Test
    public void testMinimalStream() throws ValidationException {
        new KLayoutValidator().validate(GDSBuilder.empty().stream());
    }

    @Test
    public void testMinimalFile(@TempDir Path path) throws ValidationException, IOException {
        Path gds = path.resolve("temp.gds");
        GDSStream.to(gds, GDSBuilder.empty().stream());
        new KLayoutValidator().validate(gds);
    }

    @Test
    public void testKLayoutValidatorFail(@TempDir Path tempDir)
            throws Exception, ValidationException {
        Path fooFile = tempDir.resolve("foo.gds");
        Files.write(fooFile, "bar".getBytes());
        Assertions.assertThrows(
                ValidationException.class, () -> new KLayoutValidator().validate(fooFile));
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testPassingGrammar(Path p) throws Exception, ValidationException {
        GDSStream records = GDSStream.from(p);
        new KLayoutValidator().validate(records);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testFailingGrammar(Path p) throws Exception, ValidationException {
        List<GDSIIRecord<?>> records = GDSStream.from(p).collect(Collectors.toList());
        Collections.shuffle(records, new Random(3));

        Assertions.assertThrows(
                ValidationException.class,
                () -> new KLayoutValidator().validate(GDSStream.of(records)));
    }
}
