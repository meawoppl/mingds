package io.txcl.mingds.tree;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.*;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.RecordValidator;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        return head.concat(structures.stream().flatMap(Structure::stream))
                .concat(new EndLib());
    }

    public static Library fromPath(Path path) throws IOException {
        final ParserRuleContext parseTree = RecordValidator.getParseTree(GDSStream.from(path));

        final LibName libname = (LibName) parseTree.getRuleContexts(GdsiiParser.LibnameContext.class).get(0).start;
        System.out.println(libname.toString());

        // For each structure
        parseTree.getRuleContexts(GdsiiParser.StructureContext.class).forEach((strToken)->{
            final StrName strTree = (StrName) strToken.getRuleContexts(GdsiiParser.StrnameContext.class).get(0).start;
            System.out.println(strTree.toString());
        });

        return Library.empty();
    }
}
