package io.txcl.mingds.compose;

import io.txcl.mingds.compose.structure.GDSElement;
import io.txcl.mingds.record.BgnStr;
import io.txcl.mingds.record.EndStr;
import io.txcl.mingds.record.StrName;
import io.txcl.mingds.stream.GDSStream;
import java.util.ArrayList;
import java.util.List;

public class Structure {
    private final StrName name;
    private final List<GDSElement> elements;

    public Structure(String name) {
        this.name = new StrName(name);
        this.elements = new ArrayList<>();
    }

    public void addElement(GDSElement elementStream) {
        elements.add(elementStream);
    }

    public String getName() {
        return this.name.getName();
    }

    public GDSStream stream() {
        return GDSStream.of(new BgnStr(), this.name)
                .concat(elements.stream().flatMap(GDSElement::stream))
                .concat(new EndStr());
    }
}
