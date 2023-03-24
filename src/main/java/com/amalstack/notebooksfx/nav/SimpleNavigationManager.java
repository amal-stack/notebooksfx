package com.amalstack.notebooksfx.nav;

import com.amalstack.notebooksfx.NotebooksFxApplication;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.util.ParameterizedController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleNavigationManager implements NavigationManager {
    private final Map<String, ParentParameters> parents = new HashMap<>();
    private final Stage stage;

    private final Callback<Class<?>, Object> defaultControllerFactory;

    public SimpleNavigationManager(Stage stage) {
        this(stage, null);
    }

    public SimpleNavigationManager(Stage stage, Callback<Class<?>, Object> defaultControllerFactory) {
        this.stage = stage;
        this.defaultControllerFactory = defaultControllerFactory;
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
        FXMLLoader fxmlLoader = createFXMLLoader(parentName);
        Scene scene = stage.getScene();
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new NavigationException(e);
        }
        //TODO: Provide alternative way to configure stylesheets
        scene.getStylesheets().add(NotebooksFxApplication.class.getResource("appstyle.css").toString());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void navigateTo(String parentName, ControllerParameters parameters, Stage stage) {
        FXMLLoader fxmlLoader = createFXMLLoader(parentName);
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new NavigationException(e);
        }
        Scene scene = stage.getScene();
        supplyParameters(parameters, fxmlLoader);
        scene.setRoot(root);
        scene.getStylesheets().add(NotebooksFxApplication.class.getResource("appstyle.css").toString());
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

    private FXMLLoader createFXMLLoader(String parentName) {
        ParentParameters parameters = parents.get(parentName);
        FXMLLoader fxmlLoader = new FXMLLoader(parameters.fxmlUrl(), parameters.resourceBundle());
        fxmlLoader.setControllerFactory(parameters.controllerFactory());
        stage.setTitle(parameters.title());
        return fxmlLoader;
    }
}

