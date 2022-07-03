package com.amalstack.notebooksfx.editor;

import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import org.fxmisc.richtext.StyleClassedTextArea;

public class Configuration {

    public static class DefaultEditorContextFactory implements EditorContextFactory {
        @Override
        public EditorContext create(StyleClassedTextArea editorTextArea) {
            var htmlWrapper = new SimpleHtmlTemplateWrapper();
            htmlWrapper.addExternalStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css");
            htmlWrapper.addExternalScript("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js");
            htmlWrapper.addStyle(AdmonitionExtension.getDefaultCSS());
            htmlWrapper.addScript(AdmonitionExtension.getDefaultScript());
            return EditorContext.builder()
                    .addExtensions(
                            TablesExtension.create(),
                            AdmonitionExtension.create(),
                            InsExtension.create(),
                            StrikethroughSubscriptExtension.create(),
                            SuperscriptExtension.create()
                    )
                    .htmlRenderer(builder -> builder
                            .escapeHtml(true)
                            .attributeProviderFactory(new BootstrapAttributeProvider.Factory())
                    )
                    .controlProvider(new DefaultEditorControlProvider(editorTextArea))
                    .templateWrapper(htmlWrapper)
                    .build();
        }
    }
}