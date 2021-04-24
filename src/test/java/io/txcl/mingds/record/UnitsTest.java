package io.txcl.mingds.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitsTest {
    @Test
    public void testUnitsInitGet() {
        Units units = new Units();
        Assertions.assertEquals(units.getDatabaseToUserUnit(), 1e-3);
        Assertions.assertEquals(units.getDatabaseUnitInMeters(), 1e-9);
    }
}
