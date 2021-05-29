package io.txcl.mingds.tree;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.*;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.RecordValidator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class Library {
    private final LibName libName;
    private final List<Structure> structures;

    public Library(String libname) {
        this.libName = new LibName(libname);
        this.structures = new ArrayList<>();
    }

    public static Library empty() {
        return new Library(LibName.DEFAULT_NAME);
    }

    public void addStructure(Structure structureStream) {
        structures.add(structureStream);
    }

    public GDSStream stream() {
        GDSStream head = GDSStream.of(new Header(), new BgnLib(), this.libName, new Units());
        return head.concat(structures.stream().flatMap(Structure::stream)).concat(new EndLib());
    }

    public static Library fromPath(Path path) throws IOException {
        GdsiiParser.StreamContext parseTree = RecordValidator.getParseTree(GDSStream.from(path));
        LibName libNameRec = (LibName) parseTree.libname().start;
        final Library library = new Library(libNameRec.getName());
        parseTree.structure().stream().map(Structure::fromContext).forEach(library::addStructure);
        return library;
    }
}
