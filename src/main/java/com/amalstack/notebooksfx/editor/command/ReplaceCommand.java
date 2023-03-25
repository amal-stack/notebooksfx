package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.ReplaceDialog;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.event.ActionEvent;
import javafx.stage.Modality;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ReplaceCommand implements Command {
    private final EditorContext context;
    private final GraphicNodeProvider graphic;
    private final ResourceBundle resources;
    private int index = 0;

    public ReplaceCommand(EditorContext context,
                          GraphicNodeProvider graphic) {
        this.context = context;
        this.graphic = graphic;
        this.resources = context.getResources();
    }

    @Override
    public void execute() {
        ReplaceDialog dialog = ReplaceDialog.create(resources.getString("editor.command.replace.dialog.title"),
                resources.getString("editor.command.replace.dialog.header_text"),
                graphic.getNode(Graphic.REPLACE),
                context.getResources());

        dialog.initModality(Modality.NONE);

        dialog.getDialogPane()
                .lookupButton(ReplaceDialog.REPLACE_BUTTON)
                .addEventFilter(ActionEvent.ACTION, event -> {
                    replaceNext(dialog.getModel());
                    event.consume();
                });

        dialog.getDialogPane()
                .lookupButton(ReplaceDialog.REPLACE_ALL_BUTTON)
                .addEventFilter(ActionEvent.ACTION, event -> {
                    int count = replaceAll(dialog.getModel());
                    Alerts.showInformationAlert(resources.getString("editor.command.replace.all.title"),
                            MessageFormat.format(resources.getString("editor.command.replace.all.message"), count));
                    event.consume();
                });

        dialog.showAndWait();
    }

    public int replace(ReplaceDialog.ReplaceModel replaceModel, int start) {
        var textArea = context.getEditorControlProvider().getEditorTextArea();
        String content = textArea.getText();
        int index = content.indexOf(replaceModel.find(), start);
        int end = -1;
        if (index != -1) {
            end = index + replaceModel.find().length();
            textArea.selectRange(index, end);
            end = index + replaceModel.replace().length();
            textArea.replaceSelection(replaceModel.replace());
            textArea.selectRange(index, end);
        }
        return end;
    }

    public int replaceAll(ReplaceDialog.ReplaceModel replaceModel) {
        if (replaceModel.find() == null || replaceModel.find().isBlank()) {
            return 0;
        }
        int count = 0;
        while (true) {
            index = replace(replaceModel, index);
            if (index == -1) {
                break;
            }
            context.getEditorControlProvider().getEditorTextArea().replaceSelection(replaceModel.replace());
            count++;
        }
        index = 0;
        return count;
    }

    protected void replaceNext(ReplaceDialog.ReplaceModel replaceModel) {
        if (replaceModel.find() == null || replaceModel.find().isBlank()) {
            return;
        }
        index = replace(replaceModel, index);
        if (index == -1) {
            Alerts.showInformationAlert(resources.getString("editor.command.replace.title"),
                    resources.getString("editor.command.replace.no_more_occurrences"));
            index = 0;
        }
    }
}
