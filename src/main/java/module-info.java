module com.amalstack.notebooksfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.fxmisc.flowless;
    requires org.fxmisc.richtext;
    requires javafx.web;
    requires flexmark;
    requires flexmark.util.ast;
    requires flexmark.util.data;
    requires flexmark.ext.tables;
    requires flexmark.ext.admonition;
    requires flexmark.ext.ins;
    requires flexmark.ext.gfm.strikethrough;
    requires flexmark.ext.superscript;
    requires flexmark.util.misc;
    requires annotations;
    requires flexmark.util.html;
    requires java.net.http;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    opens com.amalstack.notebooksfx to javafx.fxml;
    opens com.amalstack.notebooksfx.controller to javafx.fxml;
    exports com.amalstack.notebooksfx;
    exports com.amalstack.notebooksfx.editor.command;
    opens com.amalstack.notebooksfx.editor.command to javafx.fxml;
    opens com.amalstack.notebooksfx.editor.builder to javafx.fxml;
    opens com.amalstack.notebooksfx.editor to javafx.fxml;
    opens com.amalstack.notebooksfx.notebook to javafx.fxml;
    exports com.amalstack.notebooksfx.editor.builder;
    exports com.amalstack.notebooksfx.builder;
    opens com.amalstack.notebooksfx.builder to javafx.fxml;
    exports com.amalstack.notebooksfx.di;
    opens com.amalstack.notebooksfx.di to javafx.fxml;
    exports com.amalstack.notebooksfx.nav;
    opens com.amalstack.notebooksfx.nav to javafx.fxml;
    opens com.amalstack.notebooksfx.util to javafx.fxml;
    exports com.amalstack.notebooksfx.data.model;
    opens com.amalstack.notebooksfx.data.model to com.fasterxml.jackson.databind;
    exports com.amalstack.notebooksfx.util.http;
    opens com.amalstack.notebooksfx.editor.controller to javafx.fxml;
}