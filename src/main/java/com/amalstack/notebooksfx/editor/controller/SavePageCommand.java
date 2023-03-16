package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.controller.PageTreeItemModel;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.editor.EditorContext;

class SavePageCommand implements ParameterizedCommand<PageTreeItemModel> {

    private final DataAccessService dataAccessService;
    private final EditorContext context;

    SavePageCommand(DataAccessService dataAccessService, EditorContext context) {
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
