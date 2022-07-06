package com.amalstack.notebooksfx.builder;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public record TableColumnConfig<T, S>(
        TableColumn<T, S> column,
        Callback<TableColumn.CellDataFeatures<T, S>, ObservableValue<S>> cellValueFactory,
        Node graphic) {
    public TableColumn<T, S> toTableColumn() {
        column.setCellValueFactory(cellValueFactory);
        column.setGraphic(graphic);
        return column;
    }
}
