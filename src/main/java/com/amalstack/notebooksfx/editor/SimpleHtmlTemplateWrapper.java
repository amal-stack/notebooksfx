package com.amalstack.notebooksfx.editor;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class SimpleHtmlTemplateWrapper implements HtmlTemplateWrapper {
    public static final String DTD = "<!DOCTYPE html>";

    public static final String HTML_START = "<html lang=\"en\">";

    public static final String HTML_END = "</html>";

    public static final String HEAD_START = """
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            """;

    public static final String HEAD_END = "</head>";

    public static final String TITLE_START = "<title>";

    public static final String TITLE_END = "</title>";

    public static final String BODY_START = "<body>";

    public static final String BODY_END = "</body>";

    public static final String STYLE_START = "<style>";

    public static final String STYLE_END = "</style>";

    public static final String SCRIPT_START = "<script>";

    public static final String SCRIPT_END = "</script>";

    public static final String LINK_FORMAT = "<link rel=\"stylesheet\" href=\"{0}\" crossorigin=\"anonymous\"></link>";

    public static final String SCRIPT_FORMAT = "<script src=\"{0}\" crossorigin=\"anonymous\"></script>";

    public static final String INDENT = "   ";

    private final List<String> externalStylesheets;

    private final List<String> styles;
    private final List<String> scripts;
    private final List<String> externalScripts;

    public SimpleHtmlTemplateWrapper() {
        externalStylesheets = new ArrayList<>(2);
        externalScripts = new ArrayList<>(2);
        styles = new ArrayList<>(2);
        scripts = new ArrayList<>(2);
    }

    @Override
    public void addExternalStylesheet(@NotNull String href) {
        externalStylesheets.add(href.trim());
    }

    @Override
    public void addExternalScript(@NotNull String src) {
        externalScripts.add(src.trim());
    }

    @Override
    public void addStyle(@NotNull String css) {
        styles.add(css.trim());
    }

    @Override
    public void addScript(@NotNull String script) {
        scripts.add(script.trim());
    }

    @Override
    public String buildTemplate(String body, String title) {
        var builder = new StringBuilder();
        builder.append(DTD).append('\n');
        builder.append(HTML_START).append('\n');


        builder.append(HEAD_START).append('\n');
        builder.append(INDENT).append(TITLE_START);
        builder.append(title);
        builder.append(TITLE_END).append('\n');

        for (var stylesheet : externalStylesheets) {
            builder.append(INDENT).append(MessageFormat.format(LINK_FORMAT, stylesheet)).append('\n');
        }

        builder.append(INDENT).append(STYLE_START).append('\n');
        for (var style : styles) {
            builder.append(INDENT).append(style).append('\n');
        }
        builder.append(INDENT).append(STYLE_END).append('\n');
        builder.append(HEAD_END).append('\n');

        builder.append(BODY_START).append('\n');
        builder.append(body).append('\n');

        for (var script : externalScripts) {
            builder.append(INDENT).append(MessageFormat.format(SCRIPT_FORMAT, script)).append('\n');
        }

        builder.append(INDENT).append(SCRIPT_START).append('\n');
        for (var script : scripts) {
            builder.append(INDENT).append(script).append('\n');
        }
        builder.append(INDENT).append(SCRIPT_END).append('\n');
        builder.append(BODY_END).append('\n');
        builder.append(HTML_END).append('\n');
        return builder.toString();
    }
}

