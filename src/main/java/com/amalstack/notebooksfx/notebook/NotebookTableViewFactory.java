package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.Command;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public interface NotebookTableViewFactory {
    TableView<NotebookViewModel> create(
            ObservableList<NotebookViewModel> data,
            TextField searchTextField,
            Command<NotebookViewModel, Void> rowClickCommand);
}
