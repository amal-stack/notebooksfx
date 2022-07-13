package com.amalstack.notebooksfx;

import com.amalstack.notebooksfx.controller.EditorController;
import com.amalstack.notebooksfx.editor.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false");
        loadScene(
                stage,
                HelloApplication.class.getResource("editor-view.fxml"),
                "Notebooks",
                x -> new EditorController(new Configuration.DefaultEditorContextFactory(), new DefaultGraphicNodeProvider())
        );
//        loadScene(stage,
//                HelloApplication.class.getResource("notebooks-view.fxml"),
//                "Notebooks",
//                x -> new NotebooksController(new NotebookRepository() {
//                    final Random random = new Random();
//                    @Override
//                    public Collection<Notebook> findByUserId(Long userId) {
//                        return List.of(
//                                new Notebook(1L,
//                                        "My First Notebook",
//                                        LocalDateTime.now(),
//                                        random.nextInt(),
//                                        random.nextInt()),
//                                new Notebook(2L,
//                                        "My Second Notebook",
//                                        LocalDateTime.now(),
//                                        random.nextInt(),
//                                        random.nextInt())
//                        );
//                    }
//                },
//                        new DefaultNotebookTableViewFactory(),
//                        new User(0L, "example@example.com")));
    }

    private void loadScene(
            Stage stage,
            URL url,
            String title,
            Callback<Class<?>, Object> controllerFactory)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setControllerFactory(controllerFactory);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(getClass().getResource("appstyle.css").toString());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class SceneManager {
    private final Map<String, ParentInitParameters> parents = new HashMap<>();
    public void addParent(String name, ParentInitParameters parameters) {
        parents.put(name, parameters);
    }

    public void loadParent(String name, Stage stage) throws IOException {
        ParentInitParameters parameters = parents.get(name);
        FXMLLoader fxmlLoader = new FXMLLoader(parameters.url());
        fxmlLoader.setControllerFactory(parameters.controllerFactory());
        Scene scene = stage.getScene();
        scene.setRoot(fxmlLoader.load());
        stage.setTitle(parameters.title());
        stage.setScene(scene);
    }
}

record ParentInitParameters (URL url, String title, Callback<Class<?>, Object> controllerFactory) {
}

