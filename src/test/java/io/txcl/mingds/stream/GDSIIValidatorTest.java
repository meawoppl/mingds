package io.txcl.mingds.stream;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import io.txcl.mingds.record.base.GDSIIRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GDSIIValidatorTest {
    private static Stream<Path> getTestPaths() throws Exception {
        return GDSIIStreamTest.getTestPaths();
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testPassingGrammar(Path p) throws Exception, GDSIIValidator.ValidationException {
        List<GDSIIRecord<?>> records = GDSIIStream.from(p).collect(Collectors.toList());
        GDSIIValidator.validateRecords(records);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testPassingGrammarFail(Path p)
            throws Exception, GDSIIValidator.ValidationException {
        List<GDSIIRecord<?>> records = GDSIIStream.from(p).collect(Collectors.toList());
        Collections.shuffle(records, new Random(3));
        Assertions.assertThrows(
                GDSIIValidator.ValidationException.class,
                () -> GDSIIValidator.validateRecords(records));
    }
}
