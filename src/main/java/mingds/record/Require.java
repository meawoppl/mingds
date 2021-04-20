package mingds.record;

import java.util.concurrent.Callable;

public class Require {
    public static <T> T doesNotThrow(Callable<T> provider) {
        try {
            return provider.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
