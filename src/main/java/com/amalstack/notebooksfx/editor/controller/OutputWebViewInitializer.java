package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.Commands;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebView;


class OutputWebViewInitializer implements Initializer {
    private final WebView outputWebView;
    private final EditorContext editorContext;
    private final Button refreshButton;
    private final GraphicNodeProvider graphic;
    private final ProgressBar webViewProgress;

    public OutputWebViewInitializer(WebView outputWebView,
                                    EditorContext editorContext,
                                    Button refreshBtn,
                                    ProgressBar webViewProgress,
                                    GraphicNodeProvider graphic) {
        this.outputWebView = outputWebView;
        this.editorContext = editorContext;
        this.refreshButton = refreshBtn;
        this.webViewProgress = webViewProgress;
        this.graphic = graphic;
    }

    public void initialize() {
        refreshButton.setOnAction(Commands.eventHandler(new PreviewHtmlCommand(editorContext, outputWebView)));
        refreshButton.setGraphic(graphic.getNode(Graphic.REFRESH));

        var progressProperty = webViewProgress.progressProperty();

        // Bind progress to web engine load worker's progress
        progressProperty.bind(outputWebView
                .getEngine()
                .getLoadWorker()
                .progressProperty());

        // Show progress only if value is greater than 0 and less than 1
        webViewProgress.visibleProperty()
                .bind(Bindings
                        .when(progressProperty.lessThan(0)
                                .or(progressProperty.isEqualTo(1)))
                        .then(false)
                        .otherwise(true));
    }

}
