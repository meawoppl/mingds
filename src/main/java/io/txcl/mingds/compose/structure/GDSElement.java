package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.ARef;
import io.txcl.mingds.record.ElFlags;
import io.txcl.mingds.record.EndEl;
import io.txcl.mingds.record.Layer;
import io.txcl.mingds.record.Plex;
import io.txcl.mingds.record.SRef;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class GDSElement {
    private final GDSIIRecord<?> structureType;
    private ElFlags elflags;
    private Plex plex;
    private int layer;

    public GDSElement(GDSIIRecord<?> structureType) {
        this.structureType = structureType;
    }

    protected abstract GDSStream getContents();

    /**
     * Return a list of segments which represent a closed path. This path can be stroked/filled by
     *
     * @param renderPolygon
     */
    public abstract void render(Consumer<List<Vector2D>> renderPolygon);

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

    public void setLayer(int layer) {
        this.layer = layer;
    }

    // AFAIK never used...
    public void setElflags(ElFlags elflags) {
        this.elflags = elflags;
    }

    public void setPlex(Plex plex) {
        this.plex = plex;
    }
}
