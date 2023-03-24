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
            case IMPORT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.ARROW_DOWN);
            case CUT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.CUT);
            case UNDO -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.UNDO);
            case REDO -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.REPEAT);
            case COPY -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.COPY);
            case PASTE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.PASTE);
            case FIND -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SEARCH);
            case REPLACE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SEARCH_PLUS);
            case PREVIEW -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.BINOCULARS);
            case ABOUT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.INFO_CIRCLE);
            case BOLD -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.BOLD);
            case EMPHASIZE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.ITALIC);
            case UNDERLINE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.UNDERLINE);
            case SUPERSCRIPT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SUPERSCRIPT);
            case SUBSCRIPT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SUBSCRIPT);
            case STRIKETHROUGH -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.STRIKETHROUGH);
            case HORIZONTAL_RULE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.MINUS);
            case URL -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.LINK);
            case IMAGE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.IMAGE);
            case OL -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.LIST_OL);
            case UL -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.LIST_UL);
            case TABLE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.TABLE);
            case HEADING -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.HEADER);
            case CODE -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.CODE);
            case SELECT_ALL -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.CHECK_SQUARE);
            case CLEAR -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.ERASER);
            case EXIT -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.SIGN_OUT);
            case ERROR -> new Glyph(FONT_FAMILY, FontAwesome.Glyph.EXCLAMATION_TRIANGLE).color(Color.CRIMSON);
        };
    }
}
