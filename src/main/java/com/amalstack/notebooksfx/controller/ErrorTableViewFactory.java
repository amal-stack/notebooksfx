package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.builder.TableColumnConfig;
import com.amalstack.notebooksfx.builder.TableViewBuilder;
import com.amalstack.notebooksfx.data.model.ErrorEntry;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ErrorTableViewFactory {

    public static final List<TableColumnConfig<ErrorEntry, ?>> COLUMNS = List.of(
            new TableColumnConfig<ErrorEntry, String>(new TableColumn<>("Key"), c -> c.getValue().keyProperty(), null),
            new TableColumnConfig<ErrorEntry, String>(new TableColumn<>("Details"), c -> c.getValue().valueProperty(), null)
    );

    public static TableView<ErrorEntry> create(ObservableList<ErrorEntry> errorEntries) {
        return new TableViewBuilder<ErrorEntry>()
                .withId("errorTableView")
                .addColumns(COLUMNS)
                .withData(errorEntries)
                .configure(tv -> tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY))
                .build();
    }
}
