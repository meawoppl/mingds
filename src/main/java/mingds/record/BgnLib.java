package mingds.record;

import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

import java.time.LocalDateTime;

public class BgnLib extends ShortRecord {
    public BgnLib(){
        super(12);
        setModifiedTime(LocalDateTime.now());
        setAccessTime(LocalDateTime.now());
    }

    public BgnLib(byte[] bytes) {
        super(bytes);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.BGNLIB;
    }

    public void setModifiedTime(LocalDateTime ldt) {
        setElement(0, (short) ldt.getYear());
        setElement(1, (short) ldt.getMonthValue());
        setElement(2, (short) ldt.getDayOfMonth());

        setElement(3, (short) ldt.getHour());
        setElement(4, (short) ldt.getMinute());
        setElement(5, (short) ldt.getSecond());
    }

    public void setAccessTime(LocalDateTime ldt) {
        setElement(6, (short) ldt.getYear());
        setElement(7, (short) ldt.getMonthValue());
        setElement(8, (short) ldt.getDayOfMonth());

        setElement(9, (short) ldt.getHour());
        setElement(10, (short) ldt.getMinute());
        setElement(11, (short) ldt.getSecond());
    }

    public LocalDateTime getModifiedTime() {
        return LocalDateTime.of(getElement(0), getElement(1), getElement(2), getElement(3), getElement(4), getElement(5));
    }

    public LocalDateTime getAccessedTime() {
        return LocalDateTime.of(getElement(6), getElement(7), getElement(8), getElement(9), getElement(10), getElement(11));
    }
}
