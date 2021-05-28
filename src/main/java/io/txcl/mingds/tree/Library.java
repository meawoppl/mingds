package io.txcl.mingds.tree;

import io.txcl.mingds.record.BgnLib;
import io.txcl.mingds.record.EndLib;
import io.txcl.mingds.record.Header;
import io.txcl.mingds.record.LibName;
import io.txcl.mingds.record.Units;
import io.txcl.mingds.stream.GDSStream;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final LibName libName;
    private final List<Structure> structureStreams;

    public Library(String libname) {
        this.libName = new LibName(libname);
        this.structureStreams = new ArrayList<>();
    }

    public static Library empty() {
        return new Library(LibName.DEFAULT_NAME);
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
