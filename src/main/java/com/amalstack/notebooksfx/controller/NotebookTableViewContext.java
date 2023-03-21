package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.command.Commands;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
import com.amalstack.notebooksfx.notebook.OpenNotebookCommand;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;

public class NotebookTableViewContext {
    private final NotebookTableViewFactory factory;
    private final DataAccessService dataAccessService;
    private final NavigationManager navigationManager;
    private final GraphicNodeProvider graphicNodeProvider;
    private final ReadOnlyObjectWrapper<NotebookViewModel> currentNotebook = new ReadOnlyObjectWrapper<>();
    private TableView<NotebookViewModel> tableView;
    private ObservableList<NotebookViewModel> notebooks;

    public NotebookTableViewContext(NotebookTableViewFactory factory,
                                    DataAccessService dataAccessService,
                                    NavigationManager navigationManager,
                                    GraphicNodeProvider graphicNodeProvider) {
        this.factory = factory;
        this.dataAccessService = dataAccessService;
        this.navigationManager = navigationManager;
        this.graphicNodeProvider = graphicNodeProvider;
    }

    public void initialize(TextField searchTextField) {
        notebooks = loadNotebooks();

        // Passing rowClickCommand as null after refactoring, earlier the UpdateDetailPaneCommand was passed
        // which is now part of the changeListener for currentNotebookProperty in NotebooksController.
        // The approach before refactoring also works, but the detail pane contains
        // the information of the deleted notebook even after the deletion.
        tableView = factory.create(notebooks, searchTextField, null);

        tableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> currentNotebook.set(newValue));
    }

    public TableView<NotebookViewModel> getTableView() {
        return tableView;
    }

    public DataAccessService getDataAccessService() {
        return dataAccessService;
    }

    public Node getGraphicNode(Graphic graphic) {
        return graphicNodeProvider.getNode(graphic);
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

    public EventHandlers eventHandlers() {
        return new EventHandlers(this);
    }

    public ObservableList<NotebookViewModel> getNotebooks() {
        return notebooks;
    }

    public ReadOnlyObjectProperty<NotebookViewModel> currentNotebookProperty() {
        return currentNotebook;
    }

    private ObservableList<NotebookViewModel> loadNotebooks() {
        return dataAccessService.notebooks().findByCurrentUser()
                .stream()
                .map(NotebookViewModel::fromNotebook)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static class EventHandlers {

        private final NotebookTableViewContext context;

        private EventHandlers(NotebookTableViewContext context) {
            this.context = context;
        }

        public EventHandler<ActionEvent> open() {
            return event -> CommandExecutor.execute(
                    new OpenNotebookCommand(context.getNavigationManager()),
                    context.tableView.getSelectionModel().getSelectedItem());
        }

        public EventHandler<ActionEvent> create() {
            return Commands.eventHandler(new CreateNotebookCommand(context));
        }

        public EventHandler<ActionEvent> edit() {
            return event -> CommandExecutor.execute(
                    new EditNotebookCommand(
                            context,
                            context.tableView.getSelectionModel().getSelectedItem()));
        }

        public EventHandler<ActionEvent> delete() {
            return event -> CommandExecutor.execute(
                    new DeleteNotebookFromTableCommand(
                            context,
                            context.tableView.getSelectionModel().getSelectedItem()));
        }
    }
}
