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

    opens com.amalstack.notebooksfx to javafx.fxml;
    opens com.amalstack.notebooksfx.controller to javafx.fxml;
    exports com.amalstack.notebooksfx;
    opens com.amalstack.notebooksfx.editor.command to javafx.fxml;
    opens com.amalstack.notebooksfx.editor.builder to javafx.fxml;
}