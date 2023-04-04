package com.amalstack.notebooksfx.nav;

import com.amalstack.notebooksfx.util.ControllerParameters;
import javafx.stage.Stage;

import java.util.Optional;

public interface NavigationManager {
    void addParent(ParentParameters parameters);

    ParentParameters getParameters(String parentName);

    void navigateTo(String parentName, Stage stage);

    void navigateTo(String parentName, ControllerParameters parameters, Stage stage);

    void navigateTo(String parentName);

    void navigateTo(String parentName, ControllerParameters parameters);

    Stage getStage();

    Optional<?> getController();
}

