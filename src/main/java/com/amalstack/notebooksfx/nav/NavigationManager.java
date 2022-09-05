package com.amalstack.notebooksfx.nav;

import javafx.stage.Stage;

public interface NavigationManager {
    void addParent(ParentParameters parameters);

    ParentParameters getParameters(String parentName);

    void navigateTo(String parentName, Stage stage);

    void navigateTo(String parentName);
}

