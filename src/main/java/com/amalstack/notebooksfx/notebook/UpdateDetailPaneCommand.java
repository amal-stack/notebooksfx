package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.Command;
import javafx.scene.control.Label;
import org.controlsfx.control.MasterDetailPane;

public class UpdateDetailPaneCommand implements Command<NotebookViewModel, Void> {
    private final Context context;

    public UpdateDetailPaneCommand(Context context) {
        this.context = context;
    }

    public Void execute(NotebookViewModel model) {
        if (model == null) {
            context.masterDetailPane().setShowDetailNode(false);
            return null;
        }

        context.notebookTitleLabel().setText(model.getName());
        context.notebookDescLabel().setText(model.getDescription());
        context.masterDetailPane().setShowDetailNode(true);
        return null;
    }

    public record Context(
            MasterDetailPane masterDetailPane,
            Label notebookTitleLabel,
            Label notebookDescLabel
    ) {
    }
}
