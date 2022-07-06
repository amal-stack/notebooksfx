package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.editor.command.CommandCode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EditorController {
    private final EditorContextFactory factory;

    public EditorController(EditorContextFactory factory) {
        this.factory = factory;
    }

    private EditorContext context;
    @FXML
    private VirtualizedScrollPane<StyleClassedTextArea> editorScrollPane;
    @FXML
    private MasterDetailPane masterDetailPane;
    @FXML
    private Button viewSectionsBtn;
    @FXML
    private Button newSectionBtn;
    @FXML
    private ToolBar mainToolbar;
    @FXML
    private TreeView<String> sectionTree;
    @FXML
    private StyleClassedTextArea editorTextArea;
    @FXML
    private Button saveBtn;
    @FXML
    private WebView outputWebView;

    private void addToolbarControls() {
        var items = mainToolbar.getItems();

        items.add(context.getControl(CommandCode.BOLD));
        items.add(context.getControl(CommandCode.EMPHASIZE));
        items.add(context.getControl(CommandCode.UNDERLINE));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.STRIKETHROUGH));
        items.add(context.getControl(CommandCode.SUPERSCRIPT));
        items.add(context.getControl(CommandCode.SUBSCRIPT));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.HEADING));
        items.add(context.getControl(CommandCode.CODE));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.UNORDERED_LIST));
        items.add(context.getControl(CommandCode.ORDERED_LIST));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.URL));
        items.add(context.getControl(CommandCode.IMAGE));
        items.add(context.getControl(CommandCode.TABLE));
    }

    public void initialize() {
        context = factory.create(editorTextArea);
        addToolbarControls();
        var root = new TreeItem<>("Sections");
        sectionTree.setRoot(root);
        saveBtn.setOnAction(event -> {
            String html = context.toHtml();
            System.out.println(html);
            outputWebView.getEngine().loadContent(html, "text/html");
        });
        viewSectionsBtn.setOnAction(event -> masterDetailPane.setShowDetailNode(!masterDetailPane.isShowDetailNode()));
    }
}