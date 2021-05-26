package io.txcl.mingds.support;

import java.util.HashMap;
import java.util.Map;

public class SingleKeyMap {
    public static <K, V> Map<K, V> create(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
