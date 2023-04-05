package com.amalstack.notebooksfx.nav;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class SimpleNavigationManager implements NavigationManager {
    private final Map<String, ParentParameters> parents = new HashMap<>();
    private final Stage stage;

    private Callback<Class<?>, Object> defaultControllerFactory;
    private Object controller;
    private ResourceBundleFactory resourceBundleFactory;

    public SimpleNavigationManager(Stage stage) {
        this(stage, null);
    }

    public SimpleNavigationManager(Stage stage, Callback<Class<?>, Object> defaultControllerFactory) {
        this.stage = stage;
        this.defaultControllerFactory = defaultControllerFactory;
    }

    private static void setControllerParameters(ControllerParameters parameters, FXMLLoader fxmlLoader) {
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
        controller = fxmlLoader.getController();
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
        controller = fxmlLoader.getController();
        Scene scene = stage.getScene();
        setControllerParameters(parameters, fxmlLoader);
        scene.setRoot(root);
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

    @Override
    public Optional<?> getController() {
        return Optional.ofNullable(controller);
    }

    public void setResourceBundleFactory(ResourceBundleFactory factory) {
        this.resourceBundleFactory = factory;
    }

    public void setDefaultControllerFactory(Callback<Class<?>, Object> defaultControllerFactory) {
        this.defaultControllerFactory = defaultControllerFactory;
    }

    private FXMLLoader createFXMLLoader(String parentName) {
        ParentParameters parameters = parents.get(parentName);
        FXMLLoader fxmlLoader = new FXMLLoader(parameters.fxmlUrl(),
                createResourceBundle(parameters.resourceBundleBaseName()));
        setControllerFactory(parameters, fxmlLoader);
        stage.setTitle(parameters.title());
        return fxmlLoader;
    }

    private void setControllerFactory(ParentParameters parameters, FXMLLoader fxmlLoader) {
        if (parameters.controllerFactory() != null) {
            fxmlLoader.setControllerFactory(parameters.controllerFactory());
            return;
        }
        if (defaultControllerFactory != null) {
            fxmlLoader.setControllerFactory(defaultControllerFactory);
            return;
        }
        throw new NavigationException("No controller factory was provided for controller of parent " + parameters.name() + " and no default controller factory was set");
    }

    private ResourceBundle createResourceBundle(String resourceBundleBaseName) {
        if (resourceBundleBaseName == null) {
            return null;
        }
        if (resourceBundleFactory == null) {
            resourceBundleFactory = ResourceBundleFactory.defaultFactory();
        }
        return resourceBundleFactory.createResourceBundle(resourceBundleBaseName);
    }
}

