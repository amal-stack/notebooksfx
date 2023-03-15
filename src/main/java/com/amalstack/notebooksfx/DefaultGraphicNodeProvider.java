package com.amalstack.notebooksfx;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

public class DefaultGraphicNodeProvider implements GraphicNodeProvider {

    public static final String FONT_FAMILY = "FontAwesome";

    @Override
    public Node getNode(Graphic graphic) {
        return switch (graphic) {
            case NOTEBOOK -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.BOOK);
            case SECTION -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.COLUMNS);
            case PAGE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.FILE_ALT);
            case REFRESH -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.REFRESH);
            case SHOW -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.EYE);
            case HIDE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.EYE_SLASH);
            case ERROR -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.EXCLAMATION_TRIANGLE).color(Color.CRIMSON);
        };
    }
}
