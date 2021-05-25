package io.txcl.mingds.compose;

import com.google.common.collect.Lists;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.support.PathDecoder;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class TextSupport {
    public static int POINT_SIZE = 14;

    public static List<Vector2D> processAndFlattenPath(PathIterator pathIterator) {
        final List<List<Vector2D>> contours = PathDecoder.processPathIterator(pathIterator);

        // Stitch the endpoints to make hole features come out with the right parity
        List<Vector2D> flattened = new ArrayList<>();

        for (List<Vector2D> thisContour : contours) {
            if (flattened.isEmpty()) {
                flattened.addAll(thisContour);
                continue;
            }

            Vector2D lastPoint = flattened.get(flattened.size() - 1);
            flattened.addAll(thisContour);
            flattened.add(lastPoint);
        }

        return flattened;
    }

    public static List<List<Vector2D>> spoolGlyphVector(GlyphVector gv) {
        List<List<Vector2D>> lol = Lists.newArrayList();
        int nGlyphs = gv.getNumGlyphs();
        for (int i = 0; i < nGlyphs; i++) {
            PathIterator pathIterator =
                    gv.getGlyphOutline(i).getPathIterator(gv.getGlyphTransform(i));
            List<Vector2D> glyphPath = processAndFlattenPath(pathIterator);
            if (glyphPath.size() <= 2) {
                continue;
            }
            lol.add(glyphPath);
        }
        return lol;
    }

    public static List<List<Vector2D>> textToPolygons(String text, double targetHeightMM) {
        return textToPolygons(text, targetHeightMM, Vector2D.ZERO);
    }

    public static List<List<Vector2D>> textToPolygons(
            String text, double targetHeightMM, Vector2D location) {
        // This constant makes me kinda angry, but this is the world we live in so... yolo:
        // https://en.wikipedia.org/wiki/Point_(typography)
        double startHeightMM = POINT_SIZE * 0.3527 * 2;
        double scale = targetHeightMM / startHeightMM;
        AffineTransform xform = AffineTransform.getScaleInstance(scale, scale);
        xform.translate(location.getX(), location.getY());
        FontRenderContext fontRenderContext = new FontRenderContext(xform, true, true);
        Font font = new Font("Noto Mono", Font.PLAIN, POINT_SIZE).deriveFont(xform);

        GlyphVector glyphVector =
                font.layoutGlyphVector(
                        fontRenderContext,
                        text.toCharArray(),
                        0,
                        text.length(),
                        Font.LAYOUT_LEFT_TO_RIGHT);
        // TODO(meawoppl) resize here...
        return TextSupport.spoolGlyphVector(glyphVector);
    }

    public static List<String> availableFonts() {
        return Lists.newArrayList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    }

    public static GDSStream textToPolygonRecords(String text, double heightMM, Vector2D location) {
        return PolygonStream.ofPolygons(0, textToPolygons(text, heightMM, location).stream());
    }
}
