package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.nav.Parents;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.UpdateDetailPaneCommand;
import com.amalstack.notebooksfx.util.http.AuthenticationContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.jetbrains.annotations.NotNull;

public class NotebooksController {

    private final DataAccessService dataAccessService;

    private final NotebookTableViewFactory tableFactory;

    private final AuthenticationContext authenticationContext;

    private final NavigationManager navigationManager;

    private final GraphicNodeProvider graphicNodeProvider;

    @FXML
    public Button searchTextClearButton;

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
            navigationManager.navigateTo(Parents.AUTH);
        }

        NotebookTableViewContext context = new NotebookTableViewContext(
                tableFactory,
                dataAccessService,
                navigationManager,
                graphicNodeProvider);

        context.initialize(searchTextField);

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
    }

    private void setButtonGraphics() {
        //TODO: Replace with graphic node provider calls
        notebookCreateButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS));
        notebookOpenButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN));
        notebookEditButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.EDIT));
        notebookDeleteButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE));
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