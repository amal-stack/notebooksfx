package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.data.DataAccessService;

public class RenameTreeItemCommand implements ParameterizedCommand<TreeItemModel> {

    private final DataAccessService dataAccessService;

    public RenameTreeItemCommand(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    @Override
    public void execute(TreeItemModel model) {

        if (model instanceof NotebookTreeItemModel notebook) {
            dataAccessService.notebooks()
                    .rename(notebook.getId(),
                            notebook.getName());

        } else if (model instanceof SectionTreeItemModel section) {
            dataAccessService.sections()
                    .rename(section.getId(),
                            section.getName());

        } else if (model instanceof PageTreeItemModel page) {
            dataAccessService.pages()
                    .rename(page.getId(),
                            page.getName());

        } else {
            throw new IllegalArgumentException("Unknown model type: " + model.getClass());
        }
    }
}
