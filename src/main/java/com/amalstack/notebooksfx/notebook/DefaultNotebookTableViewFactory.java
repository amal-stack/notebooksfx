package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.controls.builder.TableColumnConfig;
import com.amalstack.notebooksfx.util.controls.builder.TableViewBuilder;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiPredicate;

public class DefaultNotebookTableViewFactory implements NotebookTableViewFactory {
    private static final BiPredicate<NotebookViewModel, String> SEARCH_PREDICATE = (notebook, query) -> notebook
            .getName()
            .toLowerCase()
            .contains(query);

    private static List<TableColumnConfig<NotebookViewModel, ?>> getColumns(ResourceBundle resources, GraphicNodeProvider graphic) {
        return List.of(
                new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>(resources.getString("notebooks.list.table.column.name")),
                        model -> model.getValue().nameProperty(),
                        graphic.getNode(Graphic.NOTEBOOK)),

                new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>(resources.getString("notebooks.list.table.column.username")),
                        model -> model.getValue().usernameProperty(),
                        graphic.getNode(Graphic.USER)),

                new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>(resources.getString("notebooks.list.table.column.created")),
                        model -> model.getValue().creationTimeProperty(),
                        graphic.getNode(Graphic.CLOCK)),

                new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>(resources.getString("notebooks.list.table.column.sections")),
                        model -> model.getValue().sectionCountProperty().asString(),
                        graphic.getNode(Graphic.SECTION)),

                new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>(resources.getString("notebooks.list.table.column.pages")),
                        model -> model.getValue().pageCountProperty().asString(),
                        graphic.getNode(Graphic.PAGE))
        );
    }

    private static Node getPlaceHolder(ResourceBundle resources) {
        return new Label(resources.getString("notebooks.list.empty.prompt"));
    }

    @Override
    public TableView<NotebookViewModel> create(
            ObservableList<NotebookViewModel> data,
            TextField searchTextField,
            ParameterizedCommand<NotebookViewModel> rowClickCommand,
            ResourceBundle resources,
            GraphicNodeProvider graphicNodeProvider) {
        return new TableViewBuilder<NotebookViewModel>()
                .withId("notebooksTableView").hasPlaceholder(getPlaceHolder(resources))
                .addColumns(getColumns(resources, graphicNodeProvider)).withData(data)
                .onRowClick(rowClickCommand)
                .filter(searchTextField.textProperty(), SEARCH_PREDICATE)
                .sort()
                .configure(tv -> tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN))
                .build();
    }
}
