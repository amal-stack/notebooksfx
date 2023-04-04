package com.amalstack.notebooksfx.nav;

import java.util.ResourceBundle;

@FunctionalInterface
public interface ResourceBundleFactory {
    ResourceBundle createResourceBundle(String name);

    static ResourceBundleFactory defaultFactory() {
        return ResourceBundle::getBundle;
    }
}
