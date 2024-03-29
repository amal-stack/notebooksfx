package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.data.model.NotebookInput;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class NotebookInputDialog extends Dialog<NotebookInput> {

    private final TextField nameField = new TextField();

    private final TextArea descriptionField = new TextArea();

    private final Label nameLabel = new Label();

    private final Label descriptionLabel = new Label();

    public NotebookInputDialog(NotebookInput input) {
        DialogPane dialogPane = getDialogPane();
        dialogPane.setContent(createForm());
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new NotebookInput(nameField.getText(), descriptionField.getText());
            }
            return null;
        });

        if (input != null) {
            nameField.setText(input.name());
            descriptionField.setText(input.description());
        }

    }

    public NotebookInputDialog() {
        this(null);
    }

    public static NotebookInputDialog create(String title, String headerText, Node graphic, ResourceBundle resources, NotebookInput input) {
        NotebookInputDialog createNotebookDialog = new NotebookInputDialog(input);
        createNotebookDialog.setTitle(title);
        createNotebookDialog.setHeaderText(headerText);
        createNotebookDialog.setGraphic(graphic);
        createNotebookDialog.nameLabel.setText(resources.getString("dialog.notebooks_input.label.title"));
        createNotebookDialog.descriptionLabel.setText(resources.getString("dialog.notebooks_input.label.description"));
        return createNotebookDialog;
    }

    public static NotebookInputDialog create(String title, String headerText, Node graphic, ResourceBundle resources) {
        return create(title, headerText, graphic, resources, null);
    }

    private GridPane createForm() {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(25);
        gridPane.setVgap(25);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionField, 1, 1);

        gridPane.setPadding(new Insets(25));

        return gridPane;
    }
}
