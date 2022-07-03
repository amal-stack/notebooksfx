package com.amalstack.notebooksfx.editor;

import com.vladsch.flexmark.ext.tables.TableBlock;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;


public class BootstrapAttributeProvider implements AttributeProvider {

    @Override
    public void setAttributes(@NotNull Node node,
                              @NotNull AttributablePart attributablePart,
                              @NotNull MutableAttributes mutableAttributes) {
        if (node instanceof TableBlock) {
            mutableAttributes.addValue("class", "table table-bordered");
        }
//        if (node instanceof Image) {
//            mutableAttributes.addValue("class", "mx-auto d-block");
//        }
    }

    public static class Factory extends IndependentAttributeProviderFactory {

        @Override
        public @NotNull AttributeProvider apply(@NotNull LinkResolverContext linkResolverContext) {
            return new BootstrapAttributeProvider();
        }
    }
}
