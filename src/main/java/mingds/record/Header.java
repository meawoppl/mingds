package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class Header extends ShortRecord {
    public Header(byte[] bytes) {
        super(bytes, RecordType.HEADER);
        Preconditions.checkArgument(bytes.length == 2);
    }

    public int getVersion() {
        return getElement(0);
    }

    public void setVersion(int version) {
        Preconditions.checkArgument(version < Short.MAX_VALUE);
        setElement(0, (short) version);
    }
}
