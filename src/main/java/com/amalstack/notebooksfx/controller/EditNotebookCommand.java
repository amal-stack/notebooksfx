package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.NotebookInput;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;

class EditNotebookCommand implements Command {

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
                .create("Create Notebook",
                        "Create a new notebook",
                        context.getGraphicNode(Graphic.CREATE),
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
