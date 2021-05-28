package io.txcl.mingds.cli;

import io.txcl.mingds.GDSTestFiles;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GDSValidateTest {
    @Test
    public void testCLIFail() {
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);

        GDSValidate.mainWrapped(new String[] {"noexit"}, ai::set);
        Assertions.assertNotEquals(0, ai.get());
    }

    @Test
    public void testCLISuccess() throws Exception {
        final Path first = GDSTestFiles.getTestPaths().findFirst().get();
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);

        GDSValidate.mainWrapped(new String[] {first.toString()}, ai::set);
        Assertions.assertEquals(0, ai.get());
    }

    @Test
    public void testCLISuccessAll() throws Exception {
        List<Path> paths = GDSTestFiles.getTestPaths().collect(Collectors.toList());
        AtomicInteger ai = new AtomicInteger(Integer.MIN_VALUE);
        final String[] args = paths.stream().map(Path::toString).toArray(String[]::new);
        GDSValidate.mainWrapped(args, ai::set);
        Assertions.assertEquals(0, ai.get());
    }
}
