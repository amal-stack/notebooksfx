package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.NotebookInput;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;

class CreateNotebookCommand implements Command {

    private final NotebookTableViewContext context;

    public CreateNotebookCommand(NotebookTableViewContext context) {
        this.context = context;
    }


    @Override
    public void execute() {
        NotebookInputDialog
                .create("Create Notebook",
                        "Create a new notebook",
                        context.getGraphicNode(Graphic.CREATE))
                .showAndWait()
                .ifPresent(this::createNotebook);
    }


    private void createNotebook(NotebookInput input) {
        var notebook = context.getDataAccessService().notebooks().create(input);
        NotebookViewModel notebookViewModel = NotebookViewModel.fromNotebook(notebook);
        context.getNotebooks().add(notebookViewModel);
        context.getTableView().getSelectionModel().select(notebookViewModel);
    }
}

