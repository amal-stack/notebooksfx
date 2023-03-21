package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.DataAccessService;

class DeleteNotebookCommand implements Command {
    private final Long notebookId;

    private final String notebookName;

    private final DataAccessService dataAccessService;

    public DeleteNotebookCommand(Long notebookId, String notebookName, DataAccessService dataAccessService) {
        this.notebookId = notebookId;
        this.notebookName = notebookName;
        this.dataAccessService = dataAccessService;
    }

    @Override
    public void execute() {
        Alerts.showConfirmationAlert("Delete Notebook",
                "Are you sure you want to delete the notebook \"" + notebookName + "\"?",
                this::deleteNotebook);
    }

    protected void deleteNotebook() {
        dataAccessService.notebooks().delete(notebookId);
    }
}

