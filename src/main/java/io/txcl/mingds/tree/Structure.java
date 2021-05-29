package io.txcl.mingds.tree;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.BgnStr;
import io.txcl.mingds.record.EndStr;
import io.txcl.mingds.record.StrName;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.ExtentCollector;
import io.txcl.mingds.tree.element.AbstractElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Structure {
    private final StrName name;
    private final List<AbstractElement> elements;

    public Structure(String name) {
        this.name = new StrName(name);
        this.elements = new ArrayList<>();
    }

    public void addElement(AbstractElement elementStream) {
        elements.add(elementStream);
    }

    public String getName() {
        return this.name.getName();
    }

    public GDSStream stream() {
        return GDSStream.of(new BgnStr(), this.name)
                .concat(elements.stream().flatMap(AbstractElement::stream))
                .concat(new EndStr());
    }

    public Box getExtents() {
        return this.elements.stream()
                .flatMap(element -> element.getPolygons().values().stream())
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(ExtentCollector.toBox());
    }

    public List<AbstractElement> getElements() {
        return elements;
    }

    public static Structure fromContext(GdsiiParser.StructureContext ctx){
        final StrName strname = (StrName) ctx.strname().start;
        final Structure structure = new Structure(strname.getName());
        ctx.element().stream().map(AbstractElement::fromStream).forEach(structure::addElement);
        return structure;
    }
}
