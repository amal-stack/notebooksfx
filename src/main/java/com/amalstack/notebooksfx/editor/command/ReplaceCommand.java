package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.ReplaceDialog;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.event.ActionEvent;
import javafx.stage.Modality;

public class ReplaceCommand implements Command {
    private final EditorContext context;
    private final GraphicNodeProvider graphic;
    private int index = 0;

    public ReplaceCommand(EditorContext context,
                          GraphicNodeProvider graphic) {
        this.context = context;
        this.graphic = graphic;
    }

    @Override
    public void execute() {
        ReplaceDialog dialog = ReplaceDialog.create("Replace",
                "Replace",
                graphic.getNode(Graphic.REPLACE));

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
                    Alerts.showInformationAlert("Replace All", "Replaced " + count + " occurrences");
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
        index = replace(replaceModel, index);
        if (index == -1) {
            Alerts.showInformationAlert("Replace", "No more occurrences found");
            index = 0;
        }
    }
}
