package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.editor.command.text.CommandCode;
import com.amalstack.notebooksfx.util.HtmlTemplateWrapper;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class EditorContext {
    private final DataHolder options;
    private final HtmlRenderer htmlRenderer;
    private final Parser parser;
    private final EditorControlProvider editorControlProvider;
    private final HtmlTemplateWrapper htmlTemplateWrapper;

    private EditorContext(
            EditorControlProvider editorControlProvider,
            Parser parser,
            HtmlRenderer htmlRenderer,
            HtmlTemplateWrapper htmlTemplateWrapper,
            DataHolder options) {
        this.options = options;
        this.htmlRenderer = htmlRenderer;
        this.parser = parser;
        this.editorControlProvider = editorControlProvider;
        this.htmlTemplateWrapper = htmlTemplateWrapper;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String toHtml() {
        return toHtml(editorControlProvider
                        .getEditorTextArea()
                        .getText(),
                "Title");
    }

    public String toHtml(String markdown, String title) {
        return htmlTemplateWrapper.buildTemplate(
                htmlRenderer.render(
                        parser.parse(markdown)
                ), title
        );
    }

    public Node getControl(CommandCode command) {
        return editorControlProvider.getControl(command);
    }

    public EditorControlProvider getEditorControlProvider() {
        return editorControlProvider;
    }

    public HtmlRenderer getHtmlRenderer() {
        return htmlRenderer;
    }

    public Parser getParser() {
        return parser;
    }

    public HtmlTemplateWrapper getHtmlTemplateWrapper() {
        return htmlTemplateWrapper;
    }

    public DataHolder getOptions() {
        return options;
    }

    public static class Builder {
        private final List<Extension> extensions = new ArrayList<>();
        private HtmlTemplateWrapper wrapper;
        private EditorControlProvider editorControlProvider;
        private MutableDataSet options = new MutableDataSet();
        private Consumer<HtmlRenderer.Builder> htmlRendererConfiguration;
        private Consumer<Parser.Builder> parserConfiguration;

        public Builder htmlRenderer(Consumer<HtmlRenderer.Builder> consumer) {
            htmlRendererConfiguration = consumer;
            return this;
        }

        public Builder parser(Consumer<Parser.Builder> consumer) {
            parserConfiguration = consumer;
            return this;
        }

        public Builder options(MutableDataSet options) {
            this.options = options;
            return this;
        }

        public Builder controlProvider(EditorControlProvider provider) {
            editorControlProvider = provider;
            return this;
        }

        public Builder templateWrapper(HtmlTemplateWrapper wrapper) {
            this.wrapper = wrapper;
            return this;
        }

        public Builder addExtension(Extension extension) {
            this.extensions.add(extension);
            return this;
        }

        public Builder addExtensions(Extension... extensions) {
            this.extensions.addAll(Arrays.asList(extensions));
            return this;
        }

        public EditorContext build() {
            // Set extensions
            var extensions = Collections.unmodifiableList(this.extensions);
            options.set(Parser.EXTENSIONS, extensions);

            // Finalize options
            var options = this.options.toImmutable();

            // Build parser
            var parserBuilder = Parser.builder(options);
            if (parserConfiguration != null) {
                parserConfiguration.accept(parserBuilder);
            }
            var parser = parserBuilder.build();

            // Build HTML renderer
            var htmlRendererBuilder = HtmlRenderer.builder(options);
            if (htmlRendererConfiguration != null) {
                htmlRendererConfiguration.accept(htmlRendererBuilder);
            }
            var htmlRenderer = htmlRendererBuilder.build();

            return new EditorContext(
                    editorControlProvider,
                    parser,
                    htmlRenderer,
                    wrapper,
                    options);
        }
    }
}