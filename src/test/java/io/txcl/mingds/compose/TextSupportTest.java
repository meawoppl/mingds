package io.txcl.mingds.compose;

import io.txcl.mingds.render.Render;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import io.txcl.mingds.stream.GDSIIStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Test;

public class TextSupportTest {

    @Test
    public void testTextRender() {

        Vector2D lower = new Vector2D(-10, -10);
        Vector2D upper = new Vector2D(10, 10);

        Render pcr = new Render(lower, upper, 1024);

        pcr.doGraphics(
                g2d -> {
                    Font font = new Font("Noto", Font.PLAIN, 6);
                    FontRenderContext fontRenderContext = g2d.getFontRenderContext();
                    GlyphVector glyphVector = font.layoutGlyphVector(fontRenderContext, "dif".toCharArray(), 0, 3, Font.LAYOUT_LEFT_TO_RIGHT);
                    TextSupport.spoolGlyphVector(glyphVector)
                            .forEach(c -> pcr.fillSegments(c, Color.BLUE));
                    TextSupport.spoolGlyphVector(glyphVector)
                            .forEach(c -> pcr.strokeSegments(c, Color.RED));
                    // g2d.drawGlyphVector(glyphVector, 40, 60);
                });

        pcr.saveAsPNG(Paths.get("footest.png"));
    }

    @Test
    public void testTextToPolygon(){
        for (int i = 3; i < 20; i++) {
            List<List<Vector2D>> fbb = TextSupport.textToPolygons("M", i);

            DoubleSummaryStatistics dssX = fbb.stream().flatMap(Collection::stream).mapToDouble(Vector2D::getX).summaryStatistics();
            DoubleSummaryStatistics dssY = fbb.stream().flatMap(Collection::stream).mapToDouble(Vector2D::getY).summaryStatistics();

            System.out.printf("%d X: %f-%f Y %f-%f\n", i, dssX.getMin(), dssX.getMax(), dssY.getMin(), dssY.getMax());
        }
    }

    @Test
    public void fontListing(){
        TextSupport.availableFonts().forEach(System.out::println);
    }

    @Test
    public void testFontToGDSII() throws Exception {
        List<List<Vector2D>> foo = TextSupport.textToPolygons("Foo Bar Baz", 10);
        GDSIIStream.to(Path.of("texttest.gds"), PolygonStream.ofPolygons(foo.stream()));
    }
}
