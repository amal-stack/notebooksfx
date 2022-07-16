package com.amalstack.notebooksfx.nav;

import javafx.stage.Stage;

import java.io.IOException;

public interface NavigationManager {
    void addParent(ParentParameters parameters);

    ParentParameters getParameters(String parentName);

    void navigateTo(String parentName, Stage stage) throws IOException;
}
