package com.amalstack.notebooksfx;

import javafx.scene.Node;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

public class DefaultGraphicNodeProvider implements GraphicNodeProvider {
    public Node getNode(Graphic graphic) {
        return switch (graphic) {
            case NOTEBOOK -> new Glyph("FontAwesome", FontAwesome.Glyph.BOOK);
            case SECTION -> new Glyph("FontAwesome", FontAwesome.Glyph.COLUMNS);
            case PAGE -> new Glyph("FontAwesome", FontAwesome.Glyph.FILE_ALT).useGradientEffect();
            case REFRESH -> new Glyph("FontAwesome", FontAwesome.Glyph.REFRESH);
            case SHOW -> new Glyph("FontAwesome", FontAwesome.Glyph.EYE);
            case HIDE -> new Glyph("FontAwesome", FontAwesome.Glyph.EYE_SLASH);
        };
    }
}
