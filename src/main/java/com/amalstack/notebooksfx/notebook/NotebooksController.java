package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.ViewNames;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.notebook.command.UpdateDetailPaneCommand;
import com.amalstack.notebooksfx.util.Initializer;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.http.AuthenticationContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import org.controlsfx.control.MasterDetailPane;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public class NotebooksController {

    private final DataAccessService dataAccessService;

    private final NotebookTableViewFactory tableFactory;

    private final AuthenticationContext authenticationContext;

    private final NavigationManager navigationManager;

    private final GraphicNodeProvider graphicNodeProvider;

    @FXML
    private Button searchTextClearButton;

    @FXML
    private MenuBar notebooksMenuBar;

    @FXML
    private MasterDetailPane masterDetailPane;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button notebookCreateButton;

    @FXML
    private Label notebookTitleLabel;

    @FXML
    private Button notebookOpenButton;

    @FXML
    private Button notebookEditButton;

    @FXML
    private Button notebookDeleteButton;

    @FXML
    private Label notebookDescLabel;

    @FXML
    private ResourceBundle resources;


    public NotebooksController(
            DataAccessService dataAccessService,
            NotebookTableViewFactory tableFactory,
            AuthenticationContext authenticationContext,
            NavigationManager navigationManager,
            GraphicNodeProvider graphicNodeProvider) {
        this.dataAccessService = dataAccessService;
        this.tableFactory = tableFactory;
        this.authenticationContext = authenticationContext;
        this.navigationManager = navigationManager;
        this.graphicNodeProvider = graphicNodeProvider;
    }

    @FXML
    public void initialize() {

        if (!authenticationContext.isAuthenticated()) {
            navigationManager.navigateTo(ViewNames.AUTH);
        }

        NotebookTableViewContext context = new NotebookTableViewContext(
                tableFactory,
                dataAccessService,
                navigationManager,
                graphicNodeProvider,
                resources);

        context.initialize(searchTextField);

        Initializer.initialize(new NotebooksMenuBarInitializer(notebooksMenuBar,
                context,
                resources)
        );

        setButtonGraphics();

        setButtonEventHandlers(context);

        masterDetailPane.setMasterNode(context.getTableView());
        masterDetailPane.setShowDetailNode(false);

        context.currentNotebookProperty().addListener(((observableValue, oldValue, newValue) ->
                CommandExecutor.execute(newUpdateDetailPaneCommand(), newValue)));
    }

    private void setButtonEventHandlers(NotebookTableViewContext context) {
        notebookCreateButton.setOnAction(context.eventHandlers().create());
        notebookOpenButton.setOnAction(context.eventHandlers().open());
        notebookEditButton.setOnAction(context.eventHandlers().edit());
        notebookDeleteButton.setOnAction(context.eventHandlers().delete());
        searchTextClearButton.setOnAction(event -> searchTextField.setText(""));
    }

    private void setButtonGraphics() {
        notebookCreateButton.setGraphic(graphicNodeProvider.getNode(Graphic.CREATE));
        notebookOpenButton.setGraphic(graphicNodeProvider.getNode(Graphic.OPEN));
        notebookEditButton.setGraphic(graphicNodeProvider.getNode(Graphic.EDIT));
        notebookDeleteButton.setGraphic(graphicNodeProvider.getNode(Graphic.DELETE));
        searchTextClearButton.setGraphic(graphicNodeProvider.getNode(Graphic.CLEAR));
    }

    @NotNull
    private UpdateDetailPaneCommand newUpdateDetailPaneCommand() {
        return new UpdateDetailPaneCommand(
                new UpdateDetailPaneCommand.Context(
                        masterDetailPane,
                        notebookTitleLabel,
                        notebookDescLabel)
        );
    }
}