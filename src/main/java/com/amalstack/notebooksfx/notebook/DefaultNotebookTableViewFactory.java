package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.Command;
import com.amalstack.notebooksfx.builder.TableColumnConfig;
import com.amalstack.notebooksfx.builder.TableViewBuilder;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.util.List;
import java.util.function.BiPredicate;

public class DefaultNotebookTableViewFactory implements NotebookTableViewFactory {
    private static final List<TableColumnConfig<NotebookViewModel, ?>> COLUMNS = List.of(
            new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>("Notebook Name"), model -> model.getValue().nameProperty(), new Glyph("FontAwesome", FontAwesome.Glyph.BOOK)),
            new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>("Username"), model -> model.getValue().usernameProperty(), new Glyph("FontAwesome", FontAwesome.Glyph.USER)),
            new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>("Created"), model -> model.getValue().creationTimeProperty(), new Glyph("FontAwesome", FontAwesome.Glyph.CLOCK_ALT)),
            new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>("Sections"), model -> model.getValue().sectionCountProperty().asString(), new Glyph("FontAwesome", FontAwesome.Glyph.SITEMAP)),
            new TableColumnConfig<NotebookViewModel, String>(new TableColumn<>("Pages"), model -> model.getValue().pageCountProperty().asString(), new Glyph("FontAwesome", FontAwesome.Glyph.FILE))
    );

    private static final Node PLACEHOLDER = new Label("No notebooks found");

    private static final BiPredicate<NotebookViewModel, String> SEARCH_PREDICATE = (notebook, query) -> notebook
            .getName()
            .toLowerCase()
            .contains(query);


    @Override
    public TableView<NotebookViewModel> create(
            ObservableList<NotebookViewModel> data,
            TextField searchTextField,
            Command<NotebookViewModel, Void> rowClickCommand) {
        return new TableViewBuilder<NotebookViewModel>()
                .withId("notebooksTableView").hasPlaceholder(PLACEHOLDER)
                .addColumns(COLUMNS).withData(data)
                .onRowClick(rowClickCommand)
                .filter(searchTextField.textProperty(), SEARCH_PREDICATE)
                .sort()
                .configure(tv -> tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY))
                .build();
    }
}
