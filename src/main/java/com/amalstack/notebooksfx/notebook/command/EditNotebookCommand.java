package com.amalstack.notebooksfx.notebook.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.NotebookInput;
import com.amalstack.notebooksfx.notebook.NotebookInputDialog;
import com.amalstack.notebooksfx.notebook.NotebookTableViewContext;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
import com.amalstack.notebooksfx.util.controls.Graphic;

public class EditNotebookCommand implements Command {

    private final NotebookTableViewContext context;
    private final NotebookViewModel notebook;

    public EditNotebookCommand(NotebookTableViewContext context,
                               NotebookViewModel notebook) {
        this.context = context;
        this.notebook = notebook;
    }

    @Override
    public void execute() {
        NotebookInputDialog
                .create(context.getResources().getString("notebooks.dialog.edit.title"),
                        context.getResources().getString("notebooks.dialog.edit.header_text"),
                        context.getGraphicNode(Graphic.CREATE),
                        context.getResources(),
                        new NotebookInput(notebook.getName(), notebook.getDescription()))
                .showAndWait()
                .ifPresent(this::editNotebook);
    }

    public void editNotebook(NotebookInput input) {
        context.getDataAccessService().notebooks().update(notebook.getId(), input);
        notebook.setName(input.name());
        notebook.setDescription(input.description());
        context.getTableView().getSelectionModel().select(notebook);
    }
}
