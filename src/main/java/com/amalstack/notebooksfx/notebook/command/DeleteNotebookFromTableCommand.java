package com.amalstack.notebooksfx.notebook.command;

import com.amalstack.notebooksfx.notebook.NotebookTableViewContext;
import com.amalstack.notebooksfx.notebook.NotebookViewModel;

public class DeleteNotebookFromTableCommand extends DeleteNotebookCommand {

    private final NotebookTableViewContext context;

    private final NotebookViewModel notebook;

    public DeleteNotebookFromTableCommand(NotebookTableViewContext context,
                                          NotebookViewModel notebook) {
        super(notebook.getId(), notebook.getName(), context.getDataAccessService(), context.getResources());
        this.context = context;
        this.notebook = notebook;
    }

    @Override
    protected void deleteNotebook() {
        super.deleteNotebook();
        // context.getTableView().getItems() is wrapped by SortedList and FilteredList.
        // Their remove() methods are inherited from AbstractList and throws UnsupportedOperationException.
        // Therefore, removal has to be from the unwrapped underlying list.
        context.getNotebooks().remove(notebook);
    }

}
