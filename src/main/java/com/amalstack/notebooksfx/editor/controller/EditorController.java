package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.Parents;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.editor.command.*;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewController;
import com.amalstack.notebooksfx.editor.nav.PageTreeItemModel;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.util.Initializer;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EditorController {
    private final EditorContextFactory factory;
    private final GraphicNodeProvider graphic;
    private final DataAccessService dataAccessService;
    private final NavigationManager navigationManager;

    private EditorContext context;

    @FXML
    private VirtualizedScrollPane<StyleClassedTextArea> editorScrollPane;
    @FXML
    private MasterDetailPane masterDetailPane;
    @FXML
    private ToolBar mainToolbar;
    @FXML
    private ToggleButton viewSectionsBtn;
    @FXML
    private Button backToNotebooksBtn;
    @FXML
    private ToolBar editorToolbar;
    @FXML
    private VBox detailPaneContainer;
    @FXML
    private StyleClassedTextArea editorTextArea;
    @FXML
    private Button refreshBtn;
    @FXML
    private WebView outputWebView;
    @FXML
    private ToolBar webViewToolbar;
    @FXML
    private ProgressBar webViewProgress;

    @FXML
    private NotebookTreeViewController notebookTreeViewController;

    public EditorController(EditorContextFactory factory,
                            GraphicNodeProvider graphic,
                            DataAccessService dataAccessService,
                            NavigationManager navigationManager) {
        this.factory = factory;
        this.graphic = graphic;
        this.dataAccessService = dataAccessService;
        this.navigationManager = navigationManager;
    }

    public void initialize() {
        context = factory.create(editorTextArea);

        Initializer.initialize(
                new EditorToolbarInitializer(context, editorToolbar),
                new OutputWebViewInitializer(outputWebView, context, refreshBtn, webViewProgress, graphic)
        );

        viewSectionsBtn.selectedProperty().bindBidirectional(masterDetailPane.showDetailNodeProperty());
        viewSectionsBtn.selectedProperty().addListener(this::toggleGraphic);

        backToNotebooksBtn.setGraphic(graphic.getNode(Graphic.BACK));
        backToNotebooksBtn.setOnAction(event -> {
            savePage(notebookTreeViewController.getCurrentPage());
            navigationManager.navigateTo(Parents.HOME);
        });

        // default initial state
        //masterDetailPane.setShowDetailNode(true);
        CommandExecutor.execute(new ToggleNotebookTreeViewCommand(masterDetailPane, viewSectionsBtn, graphic), true);
        CommandExecutor.execute(new DisableEditorCommand(editorTextArea));

        notebookTreeViewController.currentPageProperty().addListener(this::onPageChanged);
    }

    private void toggleGraphic(ObservableValue<? extends Boolean> observable,
                               Boolean oldValue,
                               Boolean newValue) {
        if (newValue) {
            viewSectionsBtn.setGraphic(graphic.getNode(Graphic.HIDE));
            return;
        }
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
    }

    private void onPageChanged(ObservableValue<? extends PageTreeItemModel> observable,
                               PageTreeItemModel previousPage,
                               PageTreeItemModel currentPage) {
        if (previousPage != null) {
            savePage(previousPage);
        }
        if (currentPage != null) {
            CommandExecutor.execute(
                    new LoadPageIntoEditorCommand(editorTextArea, currentPage),
                    new PreviewHtmlCommand(context, outputWebView)
            );
            return;
        }
        CommandExecutor.execute(new DisableEditorCommand(editorTextArea));
    }

    private void savePage(PageTreeItemModel previousPage) {
        CommandExecutor.execute(new SavePageCommand(dataAccessService, context), previousPage);
    }
}

