package com.amalstack.notebooksfx.editor.command.text;

import com.amalstack.notebooksfx.command.ParameterizedResultCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.ResourceBundle;

@FunctionalInterface
public interface TextEditorCommand extends ParameterizedResultCommand<CharSequence, CharSequence> {
    static EventHandler<ActionEvent> handleEditorCommand(StyleClassedTextArea textArea,
                                                         TextEditorCommand command,
                                                         ResourceBundle resources) {
        return event -> {
            if (textArea.isDisabled()) {
                return;
            }
            var selection = textArea.getSelection();
            if (selection.getLength() == 0) {
                textArea.appendText(command.execute(resources.getString("editor.command.text.placeholder")).toString());
                return;
            }
            textArea.replaceText(selection, command.execute(textArea.getText(selection)).toString());
        };
    }
}
