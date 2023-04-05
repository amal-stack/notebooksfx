package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.data.model.ErrorEntry;
import com.amalstack.notebooksfx.util.controls.IdUtil;
import com.amalstack.notebooksfx.util.controls.builder.TableColumnConfig;
import com.amalstack.notebooksfx.util.controls.builder.TableViewBuilder;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

// Not used anymore
public class ErrorTableViewFactory {

    private static List<TableColumnConfig<ErrorEntry, ?>> getColumns() {
        return List.of(
                new TableColumnConfig<ErrorEntry, String>(new TableColumn<>("Key"), c -> c.getValue().keyProperty(), null),
                new TableColumnConfig<ErrorEntry, String>(new TableColumn<>("Details"), c -> c.getValue().valueProperty(), null)
        );
    }

    public static TableView<ErrorEntry> create(ObservableList<ErrorEntry> errorEntries, String parentId) {
        return new TableViewBuilder<ErrorEntry>()
                .withId(IdUtil.combine(parentId, "errorTableView"))
                .addColumns(getColumns())
                .withData(errorEntries)
                .configure(tv -> {
                    tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
                    tv.setPadding(new javafx.geometry.Insets(25));
                })
                .build();
    }
}
