package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.editor.command.*;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewController;
import com.amalstack.notebooksfx.editor.nav.PageTreeItemModel;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.util.Initializer;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.views.ViewNames;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.controlsfx.control.MasterDetailPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.ResourceBundle;

public class EditorController {
    private final EditorContextFactory factory;
    private final GraphicNodeProvider graphic;
    private final DataAccessService dataAccessService;
    private final NavigationManager navigationManager;

    private EditorContext context;

    @FXML
    private MenuBar editorMenuBar;

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

    @FXML
    private StatusBarController statusBarController;

    @FXML
    private ResourceBundle resources;

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
        context = factory.create(editorTextArea, outputWebView, resources);

        Initializer.initialize(
                new EditorToolbarInitializer(context, editorToolbar),
                new OutputWebViewInitializer(context, refreshBtn, webViewProgress, graphic)
        );


        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SECTION));
        viewSectionsBtn.selectedProperty().bindBidirectional(masterDetailPane.showDetailNodeProperty());
        viewSectionsBtn.selectedProperty().addListener(this::toggleGraphic);

        backToNotebooksBtn.setGraphic(graphic.getNode(Graphic.BACK));
        backToNotebooksBtn.setOnAction(event -> {
            savePage(notebookTreeViewController.getContext().getCurrentPage());
            navigationManager.navigateTo(ViewNames.HOME);
        });

        notebookTreeViewController.setOnContextCreated(this::onNotebookTreeViewContextCreated);

        CommandExecutor.execute(
                new ToggleNotebookTreeViewCommand(masterDetailPane, viewSectionsBtn, graphic),
                true);
        CommandExecutor.execute(new DisableEditorCommand(editorTextArea));

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
            loadPage(currentPage);
            return;
        }
        CommandExecutor.execute(new DisableEditorCommand(editorTextArea));
    }

    private void loadPage(PageTreeItemModel currentPage) {
        CommandExecutor.execute(
                new LoadPageIntoEditorCommand(editorTextArea, currentPage),
                new PreviewHtmlCommand(context)
        );
    }

    private void savePage(PageTreeItemModel previousPage) {
        CommandExecutor.execute(new SavePageCommand(dataAccessService, context), previousPage);
    }

    private void onNotebookTreeViewContextCreated(NotebookTreeViewContext notebookTreeViewContext) {
        Initializer.initialize(new EditorMenuBarInitializer(editorMenuBar,
                resources,
                notebookTreeViewContext,
                context,
                navigationManager.getStage()));

        var currentPage = notebookTreeViewContext.getCurrentPage();
        if (currentPage != null) {
            loadPage(currentPage);
        }
        notebookTreeViewContext.currentPageProperty().addListener(this::onPageChanged);
        statusBarController.setContext(context, notebookTreeViewContext);
    }
}

