package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import javafx.scene.control.Label;
import org.controlsfx.control.MasterDetailPane;

public class UpdateDetailPaneCommand implements ParameterizedCommand<NotebookViewModel> {
    private final Context context;

    public UpdateDetailPaneCommand(Context context) {
        this.context = context;
    }

    public void execute(NotebookViewModel model) {
        if (model == null) {
            context.masterDetailPane().setShowDetailNode(false);
            return;
        }

        context.notebookTitleLabel().setText(model.getName());
        context.notebookDescLabel().setText(model.getDescription());
        context.masterDetailPane().setShowDetailNode(true);
    }

    public record Context(
            MasterDetailPane masterDetailPane,
            Label notebookTitleLabel,
            Label notebookDescLabel
    ) {
    }
}
