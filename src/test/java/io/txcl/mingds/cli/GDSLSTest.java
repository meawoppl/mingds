package io.txcl.mingds.cli;

import io.txcl.mingds.GDSTestFiles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GDSLSTest extends GDSTestFiles {
    @Test
    public void testCLIFail() {
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);

        GDSLS.mainWrapped(new String[] {"noexit"}, ai::set);
        Assertions.assertNotEquals(0, ai.get());
    }

    @Test
    public void testCLISuccess() throws Exception {
        final Path first = GDSTestFiles.getTestPaths().findFirst().get();
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);

        GDSLS.mainWrapped(new String[] {first.toString()}, ai::set);
        Assertions.assertEquals(0, ai.get());
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testCLISuccessAll(Path path) throws Exception {
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);
        GDSLS.mainWrapped(new String[]{path.toString()}, ai::set);
        Assertions.assertEquals(0, ai.get());
    }

}