package mingds.record;

import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.stream.Stream;
import junit.framework.TestCase;
import mingds.record.base.NoData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

public class NoDataTests extends TestCase {
    public static boolean isConcrete(Class<?> cls) {
        return !Modifier.isAbstract(cls.getModifiers());
    }

    public static Stream<Class<? extends NoData>> findNoDataRecords() {
        Reflections reflection = new Reflections("mingds.record");
        return reflection.getSubTypesOf(NoData.class).stream()
                .sorted(Comparator.comparing(Class::getName, String::compareTo))
                .filter(NoDataTests::isConcrete);
    }

    public void testSimple() {
        assertEquals(8, findNoDataRecords().count());
    }

    public void testGetSetCallsThrow() {
        Boundary b = new Boundary();
        Assertions.assertThrows(RuntimeException.class, () -> b.getElement(0));
        Assertions.assertThrows(RuntimeException.class, () -> b.setElement(0, null));
    }

    @ParameterizedTest
    @MethodSource("findNoDataRecords")
    public void testNoDataClassHasConstructor(Class<?> cls) throws Exception {
        assertNotNull(cls.getConstructor().newInstance());
    }
}
