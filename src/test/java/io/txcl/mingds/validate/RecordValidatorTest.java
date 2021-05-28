package io.txcl.mingds.validate;

import io.txcl.mingds.GDSTestFiles;
import io.txcl.mingds.tree.GDSBuilder;
import io.txcl.mingds.record.PropAttr;
import io.txcl.mingds.record.PropValue;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.io.IOException;
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

public class RecordValidatorTest extends GDSTestFiles {
    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testPassingGrammar(Path p) throws Exception, ValidationException {
        List<GDSIIRecord<?>> records = GDSStream.from(p).collect(Collectors.toList());
        new RecordValidator().validate(GDSStream.of(records));
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testFailingGrammar(Path p) throws Exception {
        List<GDSIIRecord<?>> records = GDSStream.from(p).collect(Collectors.toList());
        Collections.shuffle(records, new Random(3));
        Assertions.assertThrows(
                ValidationException.class,
                () -> new RecordValidator().validate(GDSStream.of(records)));
    }

    @Test
    public void testMinimalGDSStream() throws ValidationException {
        new RecordValidator().validate(GDSBuilder.empty().stream());
    }

    @Test
    public void testMinimalGDSFile(@TempDir Path path) throws ValidationException, IOException {
        Path gds = path.resolve("minimal.gds");
        GDSStream.to(gds, GDSBuilder.empty().stream());
        new RecordValidator().validate(gds);
    }

    @Test
    public void testValidateRuleName_Valid() throws ValidationException {
        final GDSStream stream =
                GDSStream.of(new PropAttr(new byte[] {0, 1}), new PropValue("barr".getBytes()));
        RecordValidator.validateAgainstRuleName(stream, "property");
    }

    @Test
    public void testValidateRuleName_Invalid() {
        final GDSStream stream =
                GDSStream.of(new PropValue("barr".getBytes()), new PropAttr(new byte[] {0, 1}));
        Assertions.assertThrows(
                ValidationException.class,
                () -> RecordValidator.validateAgainstRuleName(stream, "property"));
    }

    @Test
    public void testValidateRuleName_NotARule() throws ValidationException {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> RecordValidator.validateAgainstRuleName(GDSStream.empty(), "notARule"));
    }
}
