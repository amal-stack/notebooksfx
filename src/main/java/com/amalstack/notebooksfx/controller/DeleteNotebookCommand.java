package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.DataAccessService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete the notebook " + notebookName + "?",
                ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                deleteNotebook();
            }
        });

    }

    protected void deleteNotebook() {
        dataAccessService.notebooks().delete(notebookId);
    }
}
