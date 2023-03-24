package com.amalstack.notebooksfx.nav;

import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public record ParentParameters(
        String name,
        URL fxmlUrl,
        String title,
        Callback<Class<?>, Object> controllerFactory,
        ResourceBundle resourceBundle
) {
    public static ParentParametersBuilder builder() {
        return new ParentParametersBuilder();
    }

    public static class ParentParametersBuilder {
        private String name;
        private URL fxmlUrl;
        private String title;
        private Callback<Class<?>, Object> controllerFactory;
        private ResourceBundle resourceBundle;

        ParentParametersBuilder() {
        }

        public ParentParametersBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ParentParametersBuilder fxmlUrl(URL url) {
            this.fxmlUrl = url;
            return this;
        }

        public ParentParametersBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ParentParametersBuilder controllerFactory(Callback<Class<?>, Object> controllerFactory) {
            this.controllerFactory = controllerFactory;
            return this;
        }

        public ParentParametersBuilder resourceBundle(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
            return this;
        }

        public ParentParameters build() {
            return new ParentParameters(name, fxmlUrl, title, controllerFactory, resourceBundle);
        }

    }
}
