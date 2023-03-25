package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public interface NotebookTableViewFactory {
    TableView<NotebookViewModel> create(
            ObservableList<NotebookViewModel> data,
            TextField searchTextField,
            ParameterizedCommand<NotebookViewModel> rowClickCommand,
            ResourceBundle resources,
            GraphicNodeProvider graphicNodeProvider);
}
