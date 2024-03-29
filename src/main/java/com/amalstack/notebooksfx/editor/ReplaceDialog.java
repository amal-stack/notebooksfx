package com.amalstack.notebooksfx.editor;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class ReplaceDialog extends Dialog<ReplaceDialog.ReplaceModel> {

    public static final ButtonType REPLACE_BUTTON = new ButtonType("Replace", ButtonBar.ButtonData.NEXT_FORWARD);

    public static final ButtonType REPLACE_ALL_BUTTON = new ButtonType("Replace All", ButtonBar.ButtonData.NEXT_FORWARD);

    private final TextField findField = new TextField();

    private final TextField replaceField = new TextField();

    private final Label findLabel = new Label();

    private final Label replaceLabel = new Label();


    public ReplaceDialog(ReplaceModel model) {
        DialogPane dialogPane = getDialogPane();
        dialogPane.setContent(createForm());
        dialogPane.getButtonTypes().addAll(
                REPLACE_BUTTON,
                REPLACE_ALL_BUTTON,
                ButtonType.CANCEL);

        setResultConverter(buttonType -> new ReplaceModel(findField.getText(),
                replaceField.getText(),
                buttonType));

        if (model != null) {
            findField.setText(model.find());
            replaceField.setText(model.replace());
        }

    }

    public ReplaceDialog() {
        this(null);
    }

    public static ReplaceDialog create(String title, String headerText, Node graphic, ResourceBundle resources, ReplaceModel model) {
        ReplaceDialog dialog = new ReplaceDialog(model);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setGraphic(graphic);
        dialog.findLabel.setText(resources.getString("dialog.replace.label.find"));
        dialog.replaceLabel.setText(resources.getString("dialog.replace.label.replace"));
        return dialog;
    }

    public static ReplaceDialog create(String title, String headerText, Node graphic, ResourceBundle resources) {
        return create(title, headerText, graphic, resources, null);
    }

    public ReplaceModel getModel() {
        return new ReplaceModel(findField.getText(), replaceField.getText(), null);
    }

    private GridPane createForm() {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(findLabel, 0, 0);
        gridPane.add(findField, 1, 0);
        gridPane.add(replaceLabel, 0, 1);
        gridPane.add(replaceField, 1, 1);

        gridPane.setPadding(new Insets(10));

        return gridPane;
    }

    public record ReplaceModel(String find, String replace, ButtonType buttonType) {
    }
}
