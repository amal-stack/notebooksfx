package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.controller.IdUtil;
import com.amalstack.notebooksfx.editor.builder.EditorButtonBuilder;
import com.amalstack.notebooksfx.editor.builder.EditorSplitMenuButtonBuilder;
import com.amalstack.notebooksfx.editor.builder.MenuItemBuilder;
import com.amalstack.notebooksfx.editor.command.*;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCodeCombination;
import org.controlsfx.glyphfont.FontAwesome;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

record EditorOperation(
        CommandCode commandCode,
        TextEditorCommand textEditorCommand,
        Node control,
        List<KeyCodeCombination> shortcuts) {
}

public class DefaultEditorControlProvider implements EditorControlProvider {
    private final Map<CommandCode, Node> cmdControlMap;
    private final StyleClassedTextArea editorTextArea;
    private final String rootId;

    @Override
    public Node getControl(CommandCode op) {
        return cmdControlMap.get(op);
    }

    @Override
    public Collection<Node> getAllControls() {
        return cmdControlMap.values();
    }

    @Override
    public StyleClassedTextArea getEditorTextArea() {
        return editorTextArea;
    }

    public DefaultEditorControlProvider(StyleClassedTextArea editorTextArea) {
        this(editorTextArea, editorTextArea.getId());
    }

    public DefaultEditorControlProvider(StyleClassedTextArea editorTextArea, String rootId) {
        this.editorTextArea = editorTextArea;
        this.rootId = rootId;
        cmdControlMap = createControls();
    }

    protected Map<CommandCode, Node> createControls() {

        var boldButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnBold"))
                .withGlyph(FontAwesome.Glyph.BOLD)
                .performs(new BoldTextEditorCommand())
                .build();

        var emphasizeButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnEmphasis"))
                .withGlyph(FontAwesome.Glyph.ITALIC)
                .performs(new EmphasizeTextEditorCommand())
                .build();

        var underlineButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnUnderline"))
                .withGlyph(FontAwesome.Glyph.UNDERLINE)
                .performs(new EmphasizeTextEditorCommand())
                .build();

        var superscriptButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnSuperscript"))
                .withGlyph(FontAwesome.Glyph.SUPERSCRIPT)
                .performs(new SuperscriptTextEditorCommand())
                .build();

        var subscriptButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnSubscript"))
                .withGlyph(FontAwesome.Glyph.SUBSCRIPT)
                .performs(new SubscriptTextEditorCommand())
                .build();

        var strikethroughButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnStrikethrough"))
                .withGlyph(FontAwesome.Glyph.STRIKETHROUGH)
                .performs(new StrikethroughTextEditorCommand())
                .build();

        var ruleButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnHorizontalRule"))
                .withGlyph(FontAwesome.Glyph.PAGELINES)
                .performs(new HorizontalRuleTextEditorCommand())
                .build();

        var urlButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnUrl"))
                .withGlyph(FontAwesome.Glyph.LINK)
                .performs(new UrlTextEditorCommand())
                .build();

        var imgButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnImg"))
                .withGlyph(FontAwesome.Glyph.IMAGE)
                .performs(new ImageTextEditorCommand())
                .build();


        var olButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnOl"))
                .withGlyph(FontAwesome.Glyph.LIST_OL)
                .performs(new OrderedListTextEditorCommand())
                .build();

        var ulButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnUl"))
                .withGlyph(FontAwesome.Glyph.LIST_UL)
                .performs(new UnorderedListTextEditorCommand())
                .build();

        var tableButton = EditorButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(IdUtil.combine(rootId, "btnTable"))
                .withGlyph(FontAwesome.Glyph.TABLE)
                .performs(new TableTextEditorCommand())
                .build();

        var headingSplitMenuButtonId = IdUtil.combine(rootId, "splitMenuBtnHeading");
        var headingSplitMenuButton = EditorSplitMenuButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(headingSplitMenuButtonId)
                .withGlyph(FontAwesome.Glyph.HEADER)
                .addMenuItem(getHeadingForLevel(1, headingSplitMenuButtonId))
                .addMenuItem(getHeadingForLevel(2, headingSplitMenuButtonId))
                .addMenuItem(getHeadingForLevel(3, headingSplitMenuButtonId))
                .addMenuItem(getHeadingForLevel(4, headingSplitMenuButtonId))
                .addMenuItem(getHeadingForLevel(5, headingSplitMenuButtonId))
                .addMenuItem(getHeadingForLevel(6, headingSplitMenuButtonId))
                .build();

        var codeSplitMenuButtonId = IdUtil.combine(rootId, "splitMenuBtnCode");
        var codeSplitMenuButton = EditorSplitMenuButtonBuilder.create()
                .forEditor(editorTextArea)
                .withId(codeSplitMenuButtonId)
                .withGlyph(FontAwesome.Glyph.CODE)
                .addMenuItem(item -> item
                        .withId(IdUtil.combine(codeSplitMenuButtonId, "Inline"))
                        .withText("Inline Code")
                        .performs(new InlineCodeTextEditorCommand())
                        .build()
                )
                .addMenuItem(item -> item
                        .withId(IdUtil.combine(codeSplitMenuButtonId, "Block"))
                        .withText("Block Code")
                        .performs(new BlockCodeTextEditorCommand())
                        .build()
                )
                .build();

        return Map.ofEntries(
                Map.entry(CommandCode.BOLD, boldButton),
                Map.entry(CommandCode.EMPHASIZE, emphasizeButton),
                Map.entry(CommandCode.UNDERLINE, underlineButton),
                Map.entry(CommandCode.SUPERSCRIPT, superscriptButton),
                Map.entry(CommandCode.SUBSCRIPT, subscriptButton),
                Map.entry(CommandCode.STRIKETHROUGH, strikethroughButton),
                Map.entry(CommandCode.HEADING, headingSplitMenuButton),
                Map.entry(CommandCode.CODE, codeSplitMenuButton),
                Map.entry(CommandCode.HORIZONTAL_RULE, ruleButton),
                Map.entry(CommandCode.ORDERED_LIST, olButton),
                Map.entry(CommandCode.UNORDERED_LIST, ulButton),
                Map.entry(CommandCode.URL, urlButton),
                Map.entry(CommandCode.IMAGE, imgButton),
                Map.entry(CommandCode.TABLE, tableButton)
        );
    }

    private Function<MenuItemBuilder, MenuItem> getHeadingForLevel(int level, String containerId) {
        return item -> item.withId(IdUtil.combine(containerId, "H" + level))
                .withText("Heading " + level)
                .performs(new HeadingTextEditorCommand(level))
                .build();
    }
}
