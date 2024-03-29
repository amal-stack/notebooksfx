package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.editor.builder.EditorButtonBuilder;
import com.amalstack.notebooksfx.editor.builder.EditorMenuItemBuilder;
import com.amalstack.notebooksfx.editor.builder.EditorSplitMenuButtonBuilder;
import com.amalstack.notebooksfx.editor.command.text.*;
import com.amalstack.notebooksfx.util.controls.IdUtil;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.glyphfont.FontAwesome;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

public class DefaultEditorControlProvider implements EditorControlProvider {
    private final Map<CommandCode, Node> cmdControlMap;
    private final StyleClassedTextArea editorTextArea;
    private final String rootId;
    private final WebView outputWebView;
    private final ResourceBundle resources;

    public DefaultEditorControlProvider(StyleClassedTextArea editorTextArea, WebView outputWebView, ResourceBundle resources) {
        this(editorTextArea, outputWebView, resources, editorTextArea.getId());
    }

    public DefaultEditorControlProvider(StyleClassedTextArea editorTextArea,
                                        WebView outputWebView,
                                        ResourceBundle resources,
                                        String rootId) {
        this.editorTextArea = editorTextArea;
        this.outputWebView = outputWebView;
        this.rootId = rootId;
        this.resources = resources;
        cmdControlMap = createControls();
    }

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

    @Override
    public WebView getOutputWebView() {
        return outputWebView;
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
                .performs(new UnderlineTextEditorCommand())
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
                        .withText(resources.getString("editor.split_menu_button.inline_code"))
                        .performs(new InlineCodeTextEditorCommand())
                        .build()
                )
                .addMenuItem(item -> item
                        .withId(IdUtil.combine(codeSplitMenuButtonId, "Block"))
                        .withText(resources.getString("editor.split_menu_button.block_code"))
                        .performs(new BlockCodeTextEditorCommand())
                        .build()
                )
                .build();

        var wrapToggleSwitch = new ToggleSwitch(resources.getString("editor.toggle_switch.wrap"));
        wrapToggleSwitch.setId(IdUtil.combine(rootId, "wrapToggle"));
        wrapToggleSwitch.selectedProperty().bindBidirectional(editorTextArea.wrapTextProperty());

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
                Map.entry(CommandCode.TABLE, tableButton),
                Map.entry(CommandCode.WRAP, wrapToggleSwitch)
        );
    }

    private Function<EditorMenuItemBuilder, MenuItem> getHeadingForLevel(int level, String containerId) {
        return item -> item.withId(IdUtil.combine(containerId, "H" + level))
                .withText(resources.getString("editor.split_menu_button.heading") + " " + level)
                .performs(new HeadingTextEditorCommand(level))
                .build();
    }
}
