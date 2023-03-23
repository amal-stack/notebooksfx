package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.nav.PageTreeItemModel;

public class SavePageCommand implements ParameterizedCommand<PageTreeItemModel> {

    private final DataAccessService dataAccessService;
    private final EditorContext context;

    public SavePageCommand(DataAccessService dataAccessService, EditorContext context) {
        this.dataAccessService = dataAccessService;
        this.context = context;
    }

    @Override
    public void execute(PageTreeItemModel page) {
        String editorText = context.getEditorControlProvider()
                .getEditorTextArea()
                .getText();

        if (page.getContent().equals(editorText)) {
            return;
        }

        dataAccessService.pages()
                .setContent(page.getId(), editorText);

        page.setContent(editorText);
    }
}
