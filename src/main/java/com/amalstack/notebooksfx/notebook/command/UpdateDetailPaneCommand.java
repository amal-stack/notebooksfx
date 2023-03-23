package com.amalstack.notebooksfx.notebook.command;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
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
            context.notebookTitleLabel().textProperty().unbind();
            context.notebookDescLabel().textProperty().unbind();
            return;
        }

        context.notebookTitleLabel().textProperty().bind(model.nameProperty());
        context.notebookDescLabel().textProperty().bind(model.descriptionProperty());
        context.masterDetailPane().setShowDetailNode(true);
    }

    public record Context(
            MasterDetailPane masterDetailPane,
            Label notebookTitleLabel,
            Label notebookDescLabel
    ) {
    }
}
