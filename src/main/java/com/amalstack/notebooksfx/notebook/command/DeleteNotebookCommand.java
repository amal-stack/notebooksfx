package com.amalstack.notebooksfx.notebook.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.util.controls.Alerts;

import java.text.MessageFormat;
import java.util.ResourceBundle;

class DeleteNotebookCommand implements Command {
    private final Long notebookId;

    private final String notebookName;

    private final DataAccessService dataAccessService;
    private final ResourceBundle resources;

    public DeleteNotebookCommand(Long notebookId, String notebookName, DataAccessService dataAccessService, ResourceBundle resources) {
        this.notebookId = notebookId;
        this.notebookName = notebookName;
        this.dataAccessService = dataAccessService;
        this.resources = resources;
    }

    @Override
    public void execute() {
        Alerts.showConfirmationAlert(resources.getString("notebooks.command.delete.title"),
                MessageFormat.format(resources.getString("notebooks.command.delete.message"), notebookName),
                this::deleteNotebook);
    }

    protected void deleteNotebook() {
        dataAccessService.notebooks().delete(notebookId);
    }
}

