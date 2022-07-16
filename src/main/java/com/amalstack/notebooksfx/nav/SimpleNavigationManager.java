package com.amalstack.notebooksfx.nav;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SimpleNavigationManager implements NavigationManager {
    private final Map<String, ParentParameters> parents = new HashMap<>();

    @Override
    public void addParent(ParentParameters parameters) {
        parents.put(parameters.name(), parameters);
    }

    @Override
    public ParentParameters getParameters(String parentName) {
        return parents.get(parentName);
    }

    @Override
    public void navigateTo(String parentName, Stage stage) throws IOException {
        ParentParameters parameters = parents.get(parentName);
        FXMLLoader fxmlLoader = new FXMLLoader(parameters.url());
        fxmlLoader.setControllerFactory(parameters.controllerFactory());
        Scene scene = stage.getScene();
        scene.setRoot(fxmlLoader.load());
        stage.setTitle(parameters.title());
        stage.setScene(scene);
        stage.show();
    }
}