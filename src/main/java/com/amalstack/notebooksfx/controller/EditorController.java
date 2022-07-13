package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.editor.command.CommandCode;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EditorController {
    private final EditorContextFactory factory;
    private final GraphicNodeProvider graphic;

    private EditorContext context;

    @FXML
    private VirtualizedScrollPane<StyleClassedTextArea> editorScrollPane;
    @FXML
    private MasterDetailPane masterDetailPane;
    @FXML
    private ToolBar mainToolbar;
    @FXML
    private Button viewSectionsBtn;
    @FXML
    private Button newSectionBtn;
    @FXML
    private ToolBar editorToolbar;
    @FXML
    private VBox detailPaneContainer;
    @FXML
    private StyleClassedTextArea editorTextArea;
    @FXML
    private Button saveBtn;
    @FXML
    private WebView outputWebView;
    @FXML
    private ToolBar webViewToolbar;
    @FXML
    private ProgressBar webViewProgress;
    @FXML
    private ToolBar treeToolbar;

    public EditorController(EditorContextFactory factory, GraphicNodeProvider graphic) {
        this.factory = factory;
        this.graphic = graphic;
    }

    public void initialize() {
        context = factory.create(editorTextArea);
        addEditorToolbarControls();
        initOutputWebView();
        initNotebookTreeView();
    }

    private void addEditorToolbarControls() {
        var items = editorToolbar.getItems();
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

        items.add(new Separator());

        items.add(context.getControl(CommandCode.WRAP));
    }

    private void initOutputWebView() {
        saveBtn.setOnAction(event -> previewHtml());
        saveBtn.setGraphic(graphic.getNode(Graphic.REFRESH));

        var progressProperty = webViewProgress.progressProperty();
        progressProperty.bind(outputWebView
                .getEngine()
                .getLoadWorker()
                .progressProperty());
        webViewProgress.visibleProperty()
                .bind(Bindings
                        .when(progressProperty.lessThan(0)
                                .or(progressProperty.isEqualTo(1)))
                        .then(false)
                        .otherwise(true));
    }


    private void initNotebookTreeView() {
        var treeView = createTreeView();
        detailPaneContainer.getChildren().add(treeView);
        masterDetailPane.setShowDetailNode(false);
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
        viewSectionsBtn.setOnAction(this::toggleDetailPane);
    }

    private void previewHtml() {
        String html = context.toHtml();
        //System.out.println(html);
        outputWebView.getEngine().loadContent(html, "text/html");
    }

    private TreeView<TreeItemModel> createTreeView() {
        return NotebookTreeViewBuilder.forModel(getModel())
                .onTreeItemSelect(this::onTreeItemSelect)
                .onEditCommit(this::onTreeViewEditCommit)
                .configure(treeView -> {
                    VBox.setVgrow(treeView, Priority.ALWAYS);
                    treeView.setShowRoot(true);
                    treeView.setEditable(true);
                })
                .build();
    }

    private NotebookTreeItemModel getModel() {
        var treeItem = new NotebookTreeItemModel(1, "Current");
        treeItem.getSections()
                .addAll(IntStream.rangeClosed(1, 5).mapToObj(i -> {
                    var section = new SectionTreeItemModel(i, "Section " + i);
                    section.getPages()
                            .addAll(IntStream.rangeClosed(1, 5)
                                    .mapToObj(j ->
                                            new PageTreeItemModel(j,
                                                    "Page " + i + " . " + j,
                                                    "Content of Page **" + i + "." + j + "**. ++Hello++"))
                                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    return section;
                }).toList());
        return treeItem;
    }

    private void onTreeItemSelect(
            ObservableValue<? extends TreeItem<TreeItemModel>> observableValue,
            TreeItem<TreeItemModel> previousItem,
            TreeItem<TreeItemModel> currentItem) {
        if (previousItem != null && previousItem.getValue() instanceof PageTreeItemModel page) {
            String previousContent = page.getContent();
            String currentContent = editorTextArea.getText();
            if (!previousContent.equals(currentContent)) {
                page.setContent(currentContent);
            }
        }
        if (currentItem != null && currentItem.getValue() instanceof PageTreeItemModel page) {
            String content = page.getContent();
            editorTextArea.replaceText(content);
            previewHtml();
        }
    }

    private void onTreeViewEditCommit(TreeView.EditEvent<TreeItemModel> treeItemModelEditEvent) {
        //TODO: Save name change
    }

    private void toggleDetailPane(ActionEvent event) {
        boolean shouldShow = !masterDetailPane.isShowDetailNode();
        if (shouldShow) {
            masterDetailPane.setShowDetailNode(true);
            viewSectionsBtn.setGraphic(graphic.getNode(Graphic.HIDE));
            return;
        }
        masterDetailPane.setShowDetailNode(false);
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
    }
}

