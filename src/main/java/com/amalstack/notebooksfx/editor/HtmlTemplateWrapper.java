package com.amalstack.notebooksfx.editor;

import org.jetbrains.annotations.NotNull;

public interface HtmlTemplateWrapper {
    void addExternalStylesheet(@NotNull String href);

    void addExternalScript(@NotNull String src);

    void addStyle(@NotNull String css);

    void addScript(@NotNull String script);

    String buildTemplate(String body, String title);
}
