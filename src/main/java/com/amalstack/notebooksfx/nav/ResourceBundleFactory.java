package com.amalstack.notebooksfx.nav;

import java.util.ResourceBundle;

@FunctionalInterface
public interface ResourceBundleFactory {
    static ResourceBundleFactory defaultFactory() {
        return ResourceBundle::getBundle;
    }

    ResourceBundle createResourceBundle(String name);
}
