package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.util.SimpleHtmlTemplateWrapper;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.jetbrains.annotations.NotNull;

public class DefaultEditorContextFactory implements EditorContextFactory {
    public static final String BOOTSTRAP_STYLESHEET = "https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css";
    public static final String BOOTSTRAP_SCRIPT = "https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js";

    protected static void buildHtmlRenderer(HtmlRenderer.Builder builder) {
        builder
                .escapeHtml(true)
                .attributeProviderFactory(new BootstrapAttributeProvider.Factory());
    }

    @NotNull
    protected static SimpleHtmlTemplateWrapper createHtmlTemplateWrapper() {
        var htmlWrapper = new SimpleHtmlTemplateWrapper();
        htmlWrapper.addExternalStylesheet(BOOTSTRAP_STYLESHEET);
        htmlWrapper.addExternalScript(BOOTSTRAP_SCRIPT);
        htmlWrapper.addStyle(AdmonitionExtension.getDefaultCSS());
        htmlWrapper.addScript(AdmonitionExtension.getDefaultScript());
        return htmlWrapper;
    }

    @Override
    public EditorContext create(StyleClassedTextArea editorTextArea) {

        return EditorContext.builder()
                .addExtensions(
                        TablesExtension.create(),
                        AdmonitionExtension.create(),
                        InsExtension.create(),
                        StrikethroughSubscriptExtension.create(),
                        SuperscriptExtension.create()
                )
                .htmlRenderer(DefaultEditorContextFactory::buildHtmlRenderer
                )
                .controlProvider(new DefaultEditorControlProvider(editorTextArea))
                .templateWrapper(createHtmlTemplateWrapper())
                .build();
    }


}
