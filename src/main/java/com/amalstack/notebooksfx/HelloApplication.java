package com.amalstack.notebooksfx;

import com.amalstack.notebooksfx.controller.EditorController;
import com.amalstack.notebooksfx.editor.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editor-view.fxml"));
        fxmlLoader.setControllerFactory(x -> new EditorController(new Configuration.DefaultEditorContextFactory()));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Notebooks");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}