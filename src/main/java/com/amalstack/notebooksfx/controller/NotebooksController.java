package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
import com.amalstack.notebooksfx.notebook.UpdateDetailPaneCommand;
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
    private final NotebookRepository notebookRepo;
    private final User user;
    private final NotebookTableViewFactory tableFactory;

    public NotebooksController(
            NotebookRepository notebookRepo,
            NotebookTableViewFactory tableFactory,
            User user) {
        this.notebookRepo = notebookRepo;
        this.tableFactory = tableFactory;
        this.user = user;
    }

    @FXML
    private MasterDetailPane masterDetailPane;
    @FXML
    private TextField searchTextField;
    @FXML
    public Button searchTextClearButton;
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
    public void initialize() {
        setButtonGraphics();
        TableView<NotebookViewModel> notebooksTableView = tableFactory.create(
                getNotebooks(),
                searchTextField,
                newUpdateDetailPaneCommand()
        );
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
        return notebookRepo.findByUserId(user.id())
                .stream()
                .map(NotebookViewModel::fromNotebook)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}


