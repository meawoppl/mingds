package io.txcl.mingds.stream;

import io.txcl.mingds.record.base.RecordType;
import java.util.HashMap;
import java.util.Map;

public class GDSStreamStats {
    public static Map<RecordType, Integer> captureStreamStats(GDSStream stream) {
        Map<RecordType, Integer> ret = new HashMap<>();

        stream.forEach(
                rec -> {
                    RecordType t = rec.getRecordType();
                    ret.put(t, ret.getOrDefault(t, 0) + 1);
                });

        return ret;
    }
}
