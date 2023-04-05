package com.amalstack.notebooksfx.notebook.command;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.views.ViewNames;

public class OpenNotebookCommand implements ParameterizedCommand<NotebookViewModel> {
    private final NavigationManager navigationManager;

    public OpenNotebookCommand(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    @Override
    public void execute(NotebookViewModel notebook) {
        long id = notebook.getId();
        ControllerParameters parameters = new ControllerParameters();
        parameters.set("notebook.id", id);
        navigationManager.navigateTo(ViewNames.EDITOR, parameters);
    }
}
