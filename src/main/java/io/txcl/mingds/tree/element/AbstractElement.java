package io.txcl.mingds.tree.element;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.interfaces.Renderable;
import io.txcl.mingds.record.ARef;
import io.txcl.mingds.record.ElFlags;
import io.txcl.mingds.record.EndEl;
import io.txcl.mingds.record.Layer;
import io.txcl.mingds.record.Plex;
import io.txcl.mingds.record.SRef;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.ExtentCollector;
import io.txcl.mingds.validate.RecordValidator;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javax.annotation.Nullable;

public abstract class AbstractElement implements Renderable {
    private final GDSIIRecord<?> structureType;
    private ElFlags elflags;
    private Plex plex;
    private int layer;

    public AbstractElement(GDSIIRecord<?> structureType) {
        this.structureType = structureType;
    }

    protected abstract GDSStream getContents();

    public abstract Map<Integer, List<List<Vector2D>>> getPolygons();

    /**
     * Get a stream of records that represent this Structural instance
     *
     * @return GDSRecordStream
     */
    public GDSStream stream() {
        GDSStream stream = GDSStream.of(structureType);

        if (elflags != null) {
            stream = stream.concat(elflags);
        }

        if (plex != null) {
            stream = stream.concat(plex);
        }

        // All structure have a layer attribute, except ARef/SRef
        if (!(structureType instanceof SRef || structureType instanceof ARef)) {
            stream = stream.concat(new Layer(layer));
        }

        return stream.concat(getContents()).concat(new EndEl());
    };

    public Box getExtents() {
        return getPolygons().values().stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(ExtentCollector.toBox());
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    // AFAIK never used...
    public void setElflags(@Nullable ElFlags elflags) {
        this.elflags = elflags;
    }

    public void setPlex(@Nullable Plex plex) {
        this.plex = plex;
    }

    public String getElementName() {
        return this.getClass().getSimpleName();
    }

    public static AbstractElement fromStream(GdsiiParser.ElementContext ctx) {
        if (ctx.arefElement() != null) {
            return ARefElement.fromRecords(ctx.arefElement());
        }

        if (ctx.boundaryElement() != null) {
            return BoundaryElement.fromRecords(ctx.boundaryElement());
        }

        if (ctx.boxElement() != null) {
            return BoxElement.fromParseContext(ctx.boxElement());
        }

        if (ctx.pathElement() != null) {
            throw new RuntimeException("TODO PATH");
        }

        if (ctx.nodeElement() != null) {
            throw new RuntimeException("TODO NODE");
        }

        if (ctx.srefElement() != null) {
            throw new RuntimeException("TODO SREF");
        }

        if (ctx.textElement() != null) {
            throw new RuntimeException("TODO TEXT");
        }

        throw new RuntimeException("Unreachable");
    }
}
