package io.txcl.mingds.compose;

import io.txcl.mingds.compose.structure.Structure;
import io.txcl.mingds.record.BgnLib;
import io.txcl.mingds.record.EndLib;
import io.txcl.mingds.record.Header;
import io.txcl.mingds.record.LibName;
import io.txcl.mingds.record.Units;
import io.txcl.mingds.stream.GDSStream;
import java.util.ArrayList;
import java.util.List;

public class GDSBuilder {
    private final LibName libName;
    private final List<Structure> structureStreams;

    public GDSBuilder(String libname) {
        this.libName = new LibName(libname);
        this.structureStreams = new ArrayList<>();
    }

    public static GDSBuilder empty() {
        return new GDSBuilder(LibName.DEFAULT_NAME);
    }

    public void addStructure(Structure structureStream) {
        structureStreams.add(structureStream);
    }

    public GDSStream stream() {
        GDSStream head = GDSStream.of(new Header(), new BgnLib(), this.libName, new Units());
        return head.concat(structureStreams.stream().flatMap(Structure::stream))
                .concat(new EndLib());
    }
}
