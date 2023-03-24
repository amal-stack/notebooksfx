package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.nav.*;
import com.amalstack.notebooksfx.editor.nav.command.CreatePageCommand;
import com.amalstack.notebooksfx.editor.nav.command.CreateSectionCommand;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;

public class ImportTextFileCommand implements Command {

    private final EditorContext editorContext;
    private final NotebookTreeViewContext notebookTreeViewContext;
    private final ResourceBundle resources;
    private final Stage stage;

    public ImportTextFileCommand(EditorContext editorContext,
                                 NotebookTreeViewContext notebookTreeViewContext,
                                 Stage stage,
                                 ResourceBundle resources) {
        this.editorContext = editorContext;
        this.notebookTreeViewContext = notebookTreeViewContext;
        this.resources = resources;
        this.stage = stage;
    }

    @NotNull
    private static String readFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute() {
        openFileChooser().ifPresent(file -> {
            String content = readFile(file);
            TreeItemModel page = getOrCreatePage();
            editorContext.getEditorControlProvider().getEditorTextArea().appendText(content);
        });

    }

    private Optional<File> openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resources.getString("editor.menu.file.import.file_chooser.title"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return Optional.of(fileChooser.showOpenDialog(stage));
    }

    private TreeItemModel getOrCreatePage() {
        var selectedItem = notebookTreeViewContext.getTreeView().getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.getValue() instanceof NotebookTreeItemModel) {
            CommandExecutor.execute(new CreateSectionCommand(notebookTreeViewContext));
            var newSection = notebookTreeViewContext.getTreeView().getSelectionModel().getSelectedItem();
            CommandExecutor.execute(new CreatePageCommand(newSection, notebookTreeViewContext));
            return notebookTreeViewContext.getTreeView().getSelectionModel().getSelectedItem().getValue();
        } else if (selectedItem.getValue() instanceof SectionTreeItemModel) {
            CommandExecutor.execute(new CreatePageCommand(selectedItem, notebookTreeViewContext));
            return notebookTreeViewContext.getTreeView().getSelectionModel().getSelectedItem().getValue();
        } else if (selectedItem.getValue() instanceof PageTreeItemModel) {
            return selectedItem.getValue();
        }
        return null;
    }
}
