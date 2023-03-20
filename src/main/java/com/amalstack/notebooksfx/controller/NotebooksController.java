package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.nav.Parents;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
import com.amalstack.notebooksfx.notebook.OpenNotebookCommand;
import com.amalstack.notebooksfx.notebook.UpdateDetailPaneCommand;
import com.amalstack.notebooksfx.util.http.AuthenticationContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class NotebooksController {
    private final DataAccessService dataAccessService;

    private final NotebookTableViewFactory tableFactory;

    private final AuthenticationContext authenticationContext;

    private final NavigationManager navigationManager;

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
            NavigationManager navigationManager) {
        this.dataAccessService = dataAccessService;
        this.tableFactory = tableFactory;
        this.authenticationContext = authenticationContext;
        this.navigationManager = navigationManager;
    }

    @FXML
    public void initialize() {

        if (!authenticationContext.isAuthenticated()) {
            navigationManager.navigateTo(Parents.AUTH);
        }
        setButtonGraphics();
        TableView<NotebookViewModel> notebooksTableView = tableFactory.create(
                getNotebooks(),
                searchTextField,
                newUpdateDetailPaneCommand()
        );
        notebookOpenButton.setOnAction(x -> CommandExecutor.execute(
                new OpenNotebookCommand(navigationManager),
                notebooksTableView.getSelectionModel().getSelectedItem()));
        notebookDeleteButton.setOnAction(x -> {
            var notebook = notebooksTableView.getSelectionModel().getSelectedItem();
            CommandExecutor.execute(new DeleteNotebookCommand(
                    notebook.getId(),
                    notebook.getName(),
                    dataAccessService));
        });
        masterDetailPane.setMasterNode(notebooksTableView);
        masterDetailPane.setShowDetailNode(false);
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

    private void setButtonGraphics() {
        notebookCreateButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.PLUS));
        notebookOpenButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.FOLDER_OPEN));
        notebookEditButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.EDIT));
        notebookDeleteButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.REMOVE));
    }

    private ObservableList<NotebookViewModel> getNotebooks() {

        return dataAccessService.notebooks().findByCurrentUser()
                .stream()
                .map(NotebookViewModel::fromNotebook)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}


