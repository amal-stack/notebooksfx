package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.util.controls.Alerts;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

import java.util.ResourceBundle;

public class FindCommand implements Command {
    private final EditorContext context;
    private final ResourceBundle resources;

    protected int index = 0;

    public FindCommand(EditorContext context) {
        this.context = context;
        this.resources = context.getResources();
    }

    @Override
    public void execute() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initModality(Modality.NONE);

        dialog.getDialogPane().getButtonTypes().clear();
        var nextButton = new ButtonType("Next", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(
                nextButton,
                ButtonType.CANCEL
        );

        dialog.setResultConverter(buttonType -> {
            if (buttonType == nextButton) {
                return dialog.getEditor().getText();
            }
            return null;
        });

        dialog.setTitle(resources.getString("editor.command.find.dialog.title"));
        dialog.setHeaderText(resources.getString("editor.command.find.dialog.header_text"));
        dialog.setContentText(resources.getString("editor.command.find.dialog.content_text"));

        dialog.getDialogPane()
                .lookupButton(nextButton)
                .addEventFilter(ActionEvent.ACTION, event -> {
                    findNext(dialog.getEditor().getText());
                    event.consume();
                });

        dialog.showAndWait();
    }

    protected int find(String text, int start) {
        var textArea = context.getEditorControlProvider().getEditorTextArea();
        String content = textArea.getText();
        int index = content.indexOf(text, start);
        int end = -1;
        if (index != -1) {
            end = index + text.length();
            textArea.selectRange(index, end);
        }
        return end;
    }

    protected void findNext(String text) {
        index = find(text, index);
        if (index == -1) {
            Alerts.showInformationAlert(resources.getString("editor.command.find.title"),
                    resources.getString("editor.command.find.no_more_occurrences"));
            index = 0;
        }
    }
}
