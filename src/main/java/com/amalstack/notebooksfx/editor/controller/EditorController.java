package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

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

    @FXML
    private NotebookTreeViewController notebookTreeViewController;

    public EditorController(EditorContextFactory factory,
                            GraphicNodeProvider graphic) {
        this.factory = factory;
        this.graphic = graphic;
    }

    public void initialize() {
        context = factory.create(editorTextArea);
        addEditorToolbarControls();
        initOutputWebView();
        // default initial state
        masterDetailPane.setShowDetailNode(true);
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
        viewSectionsBtn.setOnAction(this::toggleDetailPane);
        editorTextArea.setEditable(false);
        editorTextArea.replaceText("Select a page to edit");

        notebookTreeViewController.currentPageProperty().addListener((observable, previousPage, newPage) -> {
            if (previousPage != null) {

                //TODO: Save contents of previous page
            }
            if (newPage != null) {
                editorTextArea.setEditable(true);
                editorTextArea.replaceText(newPage.getContent());
                previewHtml();
                return;
            }
            editorTextArea.setEditable(false);
            editorTextArea.replaceText("Select a page to edit");
        });
    }

    private void addEditorToolbarControls() {
        new EditorToolbarInitializer(context, editorToolbar).addControls();
    }

    private void initOutputWebView() {
        saveBtn.setOnAction(event -> previewHtml());
        saveBtn.setGraphic(graphic.getNode(Graphic.REFRESH));

        var progressProperty = webViewProgress.progressProperty();

        // Bind progress to web engine load worker's progress
        progressProperty.bind(outputWebView
                .getEngine()
                .getLoadWorker()
                .progressProperty());

        // Show progress only if value is greater than 0 and less than 1
        webViewProgress.visibleProperty()
                .bind(Bindings
                        .when(progressProperty.lessThan(0)
                                .or(progressProperty.isEqualTo(1)))
                        .then(false)
                        .otherwise(true));
    }


    private void previewHtml() {
        String html = context.toHtml();
        //System.out.println(html);
        outputWebView.getEngine().loadContent(html, "text/html");
    }

    private void toggleDetailPane(ActionEvent event) {
        boolean shouldShow = !masterDetailPane.isShowDetailNode();
        if (shouldShow) {
            // show
            masterDetailPane.setShowDetailNode(true);
            viewSectionsBtn.setGraphic(graphic.getNode(Graphic.HIDE));
            return;
        }
        // hide
        masterDetailPane.setShowDetailNode(false);
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
    }

}

