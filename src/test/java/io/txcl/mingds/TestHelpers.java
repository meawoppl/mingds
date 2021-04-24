package io.txcl.mingds;

import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.stream.Stream;
import org.reflections.Reflections;

public class TestHelpers {
    public static boolean isConcrete(Class<?> cls) {
        return !Modifier.isAbstract(cls.getModifiers());
    }

    public static <T> Stream<Class<? extends T>> findNoDataRecords(Class<T> ext, String prefix) {
        Reflections reflection = new Reflections(prefix);
        return reflection.getSubTypesOf(ext).stream()
                .sorted(Comparator.comparing(Class::getName, String::compareTo))
                .filter(TestHelpers::isConcrete);
    }
}
