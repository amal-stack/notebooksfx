package com.amalstack.notebooksfx.util.controls;

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
            case OPEN -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.FOLDER_OPEN);
            case CREATE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.PLUS);
            case EDIT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.PENCIL); // Alt: FontAwesome.Glyph.EDIT
            case DELETE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.TRASH); // Alt: FontAwesome.Glyph.REMOVE
            case SAVE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SAVE);
            case BACK -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.ARROW_LEFT);
            case ERROR -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.EXCLAMATION_TRIANGLE).color(Color.CRIMSON);
        };
    }
}
