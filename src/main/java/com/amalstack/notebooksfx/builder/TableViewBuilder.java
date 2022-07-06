package com.amalstack.notebooksfx.builder;

import com.amalstack.notebooksfx.Command;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TableViewBuilder<T> implements ControlBuilder<TableView<T>, TableViewBuilder<T>> {
    private final Predicate<T> TRUE_FILTER = item -> true;
    private final Collection<TableColumnConfig<T, ?>> tableColumnConfigs = new ArrayList<>(5);
    private String id;
    private Node placeholder;
    private ObservableList<T> data;
    private Command<T, Void> rowClickCommand;
    private boolean isFilteringEnabled;
    private boolean isSortingEnabled;
    private StringProperty filterQuery;
    private BiPredicate<T, String> filterPredicate;
    private Comparator<? super T> comparator;
    private Consumer<TableView<T>> config;

    public TableView<T> build() {
        var tableView = new TableView<T>();
        tableView.setId(id);
        if (placeholder != null) {
            tableView.setPlaceholder(placeholder);
        }

        tableView.getColumns().addAll(tableColumnConfigs.stream()
                .map(TableColumnConfig::toTableColumn)
                .toList());

        if (rowClickCommand != null) {
            tableView.setRowFactory(view -> {
                var row = new TableRow<T>();
                row.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 1) {
                        rowClickCommand.execute(row.getItem());
                    }
                });
                return row;
            });
        }

        ObservableList<T> tableItems = data;
        if (isFilteringEnabled) {
            tableItems = addFiltering(tableItems);
        }
        if (isSortingEnabled) {
            tableItems = addSorting(tableItems, tableView.comparatorProperty());
        }
        tableView.setItems(tableItems);
        if (config != null) {
            config.accept(tableView);
        }
        return tableView;
    }

    private ObservableList<T> addFiltering(ObservableList<T> tableItems) {
        FilteredList<T> filteredList = new FilteredList<>(data);
        filterQuery.addListener((observableValue, previous, current) -> {
            if (current == null || current.isBlank()) {
                filteredList.setPredicate(TRUE_FILTER);
                return;
            }
            String currentQuery = current.trim().toLowerCase();
            if (previous != null && currentQuery.equals(previous.trim())) {
                return;
            }
            filteredList.setPredicate(model -> filterPredicate.test(model, currentQuery));
        });
        return filteredList;
    }

    private SortedList<T> addSorting(ObservableList<T> data, ReadOnlyObjectProperty<Comparator<T>> tableComparatorProperty) {
        SortedList<T> sortedList = new SortedList<>(data, comparator);
        sortedList.comparatorProperty().bind(tableComparatorProperty);
        return sortedList;
    }

    @Override
    public TableViewBuilder<T> configure(Consumer<TableView<T>> config) {
        this.config = config;
        return this;
    }

    @Override
    public TableViewBuilder<T> withId(String id) {
        this.id = id;
        return this;
    }


    public TableViewBuilder<T> hasPlaceholder(Node node) {
        this.placeholder = node;
        return this;
    }

    public TableViewBuilder<T> addColumn(TableColumnConfig<T, ?> column) {
        this.tableColumnConfigs.add(column);
        return this;
    }

    public TableViewBuilder<T> addColumns(Collection<TableColumnConfig<T, ?>> columns) {
        this.tableColumnConfigs.addAll(columns);
        return this;
    }

    public TableViewBuilder<T> withData(ObservableList<T> data) {
        this.data = data;
        return this;
    }

    public TableViewBuilder<T> onRowClick(Command<T, Void> command) {
        this.rowClickCommand = command;
        return this;
    }

    public TableViewBuilder<T> filter(StringProperty query, BiPredicate<T, String> filterPredicate) {
        this.isFilteringEnabled = true;
        this.filterQuery = query;
        this.filterPredicate = filterPredicate;
        return this;
    }

    public TableViewBuilder<T> sort(Comparator<? super T> comparator) {
        this.isSortingEnabled = true;
        this.comparator = comparator;
        return this;
    }

    public TableViewBuilder<T> sort() {
        this.isSortingEnabled = true;
        this.comparator = null;
        return this;
    }
}
