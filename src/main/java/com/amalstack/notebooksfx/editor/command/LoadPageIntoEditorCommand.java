package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.nav.PageTreeItemModel;
import org.fxmisc.richtext.StyleClassedTextArea;

/**
 * Sets the text of the editor to the content of a page and enables the editor.
 *
 * @author Amal Krishna
 */
public class LoadPageIntoEditorCommand implements Command {

    private final StyleClassedTextArea editorTextArea;
    private final PageTreeItemModel page;

    public LoadPageIntoEditorCommand(StyleClassedTextArea editorTextArea, PageTreeItemModel page) {
        this.editorTextArea = editorTextArea;
        this.page = page;
    }

    @Override
    public void execute() {
        editorTextArea.replaceText(page.getContent());
        editorTextArea.setEditable(true);
    }
}
