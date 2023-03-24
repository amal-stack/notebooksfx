package com.amalstack.notebooksfx.nav;

import com.amalstack.notebooksfx.NotebooksFxApplication;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.util.ParameterizedController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleNavigationManager implements NavigationManager {
    private final Map<String, ParentParameters> parents = new HashMap<>();
    private final Stage stage;


    public SimpleNavigationManager(Stage stage) {

        this.stage = stage;
    }

    private static void supplyParameters(ControllerParameters parameters, FXMLLoader fxmlLoader) {
        boolean set = false;
        if (fxmlLoader.getController() instanceof ParameterizedController controller) {
            controller.setParameters(parameters);
            set = true;
        }

        // Set parameters for all controllers in the namespace
        for (var value : fxmlLoader.getNamespace().values()) {
            if (value instanceof ParameterizedController controller) {
                controller.setParameters(parameters);
                set = true;
            }
        }
        if (!set) {
            throw new NavigationException("ControllerParameters instance was passed but target controller does not implement ParameterizedController");
        }
    }

    @Override
    public void addParent(ParentParameters parameters) {
        parents.put(parameters.name(), parameters);
    }

    @Override
    public ParentParameters getParameters(String parentName) {
        var parameters = parents.get(parentName);
        if (parameters == null) {
            throw new NavigationException("Parent with name " + parentName + " not found");
        }
        return parameters;
    }

    @Override
    public void navigateTo(String parentName, Stage stage) {
        ParentParameters parameters = parents.get(parentName);
        FXMLLoader fxmlLoader = new FXMLLoader(parameters.url());
        fxmlLoader.setControllerFactory(parameters.controllerFactory());
        Scene scene = stage.getScene();
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new NavigationException(e);
        }
        //TODO: Provide alternative way to configure stylesheets
        scene.getStylesheets().add(NotebooksFxApplication.class.getResource("appstyle.css").toString());
        stage.setTitle(parameters.title());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void navigateTo(String parentName, ControllerParameters parameters, Stage stage) {
        ParentParameters parentParameters = parents.get(parentName);
        FXMLLoader fxmlLoader = new FXMLLoader(parentParameters.url());
        fxmlLoader.setControllerFactory(parentParameters.controllerFactory());
        Scene scene = stage.getScene();
        try {

            Parent root = fxmlLoader.load();
            supplyParameters(parameters, fxmlLoader);
            scene.setRoot(root);
        } catch (IOException e) {
            throw new NavigationException(e);
        }
        scene.getStylesheets().add(NotebooksFxApplication.class.getResource("appstyle.css").toString());
        stage.setTitle(parentParameters.title());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void navigateTo(String parentName) {
        navigateTo(parentName, stage);
    }

    @Override
    public void navigateTo(String parentName, ControllerParameters parameters) {
        navigateTo(parentName, parameters, stage);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

}

