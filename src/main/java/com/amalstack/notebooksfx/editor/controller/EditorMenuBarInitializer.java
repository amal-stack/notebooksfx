package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.command.Commands;
import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.command.*;
import com.amalstack.notebooksfx.editor.command.text.*;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext;
import com.amalstack.notebooksfx.util.Initializer;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.controls.builder.MenuItemBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class EditorMenuBarInitializer implements Initializer {
    private final MenuBar menuBar;
    private final ResourceBundle resources;
    private final NotebookTreeViewContext notebookTreeViewContext;
    private final EditorContext editorContext;
    private final Stage stage;
    private final GraphicNodeProvider graphicNodeProvider;

    public EditorMenuBarInitializer(MenuBar menuBar,
                                    ResourceBundle resources,
                                    NotebookTreeViewContext notebookTreeViewContext,
                                    EditorContext editorContext,
                                    Stage stage) {
        this.menuBar = menuBar;
        this.resources = resources;
        this.notebookTreeViewContext = notebookTreeViewContext;
        this.editorContext = editorContext;
        this.stage = stage;
        this.graphicNodeProvider = notebookTreeViewContext.getGraphicNodeProvider();
    }

    private static MenuItem createMenuItem(String text,
                                           Node graphic,
                                           EventHandler<ActionEvent> eventHandler,
                                           KeyCombination keyCombination) {
        MenuItem menuItem = new MenuItem(text, graphic);
        menuItem.setOnAction(eventHandler);
        menuItem.setAccelerator(keyCombination);
        return menuItem;
    }

    @Override
    public void initialize() {
        menuBar.getMenus().addAll(
                fileMenu(),
                editMenu(),
                viewMenu(),
                markdownMenu(),
                helpMenu()
        );
    }

    public Menu fileMenu() {
        Menu fileMenu = new Menu(resources.getString("editor.menu.file"));
        fileMenu.getItems().addAll(
                createMenuItem(resources.getString("editor.menu.file.new"),
                        graphicNodeProvider.getNode(Graphic.CREATE),
                        NotebookTreeViewContext.EventHandlers.createSection(notebookTreeViewContext),
                        KeyCombination.keyCombination("Ctrl+N")),

                createMenuItem(resources.getString("editor.menu.file.import"),
                        graphicNodeProvider.getNode(Graphic.IMPORT),
                        Commands.eventHandler(new ImportTextFileCommand(editorContext,
                                notebookTreeViewContext,
                                stage,
                                resources)),
                        KeyCombination.keyCombination("Ctrl+M")),

                new SeparatorMenuItem(),

                createMenuItem(resources.getString("editor.menu.file.save"),
                        graphicNodeProvider.getNode(Graphic.SAVE),
                        event -> CommandExecutor.execute(new SavePageCommand(
                                        notebookTreeViewContext.getDataAccessService(),
                                        editorContext),
                                notebookTreeViewContext.getCurrentPage()),
                        KeyCombination.keyCombination("Ctrl+S"))
        );
        return fileMenu;
    }

    public Menu editMenu() {
        Menu editMenu = new Menu(resources.getString("editor.menu.edit"));
        editMenu.getItems().addAll(
                createMenuItem(resources.getString("editor.menu.edit.undo"),
                        graphicNodeProvider.getNode(Graphic.UNDO),
                        event -> CommandExecutor.execute(new UndoCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+Z")),

                createMenuItem(resources.getString("editor.menu.edit.redo"),
                        graphicNodeProvider.getNode(Graphic.REDO),
                        event -> CommandExecutor.execute(new RedoCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+Y")),

                new SeparatorMenuItem(),

                createMenuItem(resources.getString("editor.menu.edit.cut"),
                        graphicNodeProvider.getNode(Graphic.CUT),
                        event -> CommandExecutor.execute(new CutCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+X")),

                createMenuItem(resources.getString("editor.menu.edit.copy"),
                        graphicNodeProvider.getNode(Graphic.COPY),
                        event -> CommandExecutor.execute(new CopyCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+C")),

                createMenuItem(resources.getString("editor.menu.edit.paste"),
                        graphicNodeProvider.getNode(Graphic.PASTE),
                        event -> CommandExecutor.execute(new PasteCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+V")),

                new SeparatorMenuItem(),

                createMenuItem(resources.getString("editor.menu.edit.find"),
                        graphicNodeProvider.getNode(Graphic.FIND),
                        event -> CommandExecutor.execute(new FindCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+F")),

                createMenuItem(resources.getString("editor.menu.edit.replace"),
                        graphicNodeProvider.getNode(Graphic.REPLACE),
                        event -> CommandExecutor.execute(new ReplaceCommand(editorContext, graphicNodeProvider)),
                        KeyCombination.keyCombination("Ctrl+R")),
                createMenuItem(resources.getString("editor.menu.edit.select_all"),
                        graphicNodeProvider.getNode(Graphic.SELECT_ALL),
                        event -> CommandExecutor.execute(new SelectAllCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+A"))
        );
        return editMenu;
    }

    public Menu viewMenu() {
        Menu viewMenu = new Menu(resources.getString("editor.menu.view"));

        viewMenu.getItems().addAll(
                createMenuItem(resources.getString("editor.menu.view.preview"),
                        graphicNodeProvider.getNode(Graphic.PREVIEW),
                        event -> CommandExecutor.execute(new PreviewHtmlCommand(editorContext)),
                        KeyCombination.keyCombination("Ctrl+P"))
        );
        return viewMenu;
    }

    public Menu markdownMenu() {
        Menu markdownMenu = new Menu(resources.getString("editor.menu.markdown"));
        CheckMenuItem wrapMenuItem = new CheckMenuItem(resources.getString("editor.menu.markdown.wrap"));
        wrapMenuItem.selectedProperty().bindBidirectional(editorContext.getEditorControlProvider()
                .getEditorTextArea()
                .wrapTextProperty());

        markdownMenu.getItems().addAll(
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.bold"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.BOLD))
                        .performs(commandEventHandler(new BoldTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+B"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.italic"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.EMPHASIZE))
                        .performs(commandEventHandler(new EmphasizeTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+I"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.underline"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.UNDERLINE))
                        .performs(commandEventHandler(new UnderlineTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+U"))
                        .build(),
                new SeparatorMenuItem(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.superscript"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.SUPERSCRIPT))
                        .performs(commandEventHandler(new SuperscriptTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+P"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.subscript"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.SUBSCRIPT))
                        .performs(commandEventHandler(new SubscriptTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"))
                        .build(),
                new SeparatorMenuItem(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.strikethrough"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.STRIKETHROUGH))
                        .performs(commandEventHandler(new StrikethroughTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+T"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.horizontal_rule"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.HORIZONTAL_RULE))
                        .performs(commandEventHandler(new HorizontalRuleTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+R"))
                        .build(),
                new SeparatorMenuItem(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.url"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.URL))
                        .performs(commandEventHandler(new UrlTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+U"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.image"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.IMAGE))
                        .performs(commandEventHandler(new ImageTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+I"))
                        .build(),
                new SeparatorMenuItem(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.ol"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.OL))
                        .performs(commandEventHandler(new OrderedListTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+O"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.ul"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.UL))
                        .performs(commandEventHandler(new UnorderedListTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+L"))
                        .build(),
                new SeparatorMenuItem(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.table"))
                        .withGraphic(graphicNodeProvider.getNode(Graphic.TABLE))
                        .performs(commandEventHandler(new TableTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+T"))
                        .build(),
                new SeparatorMenuItem(),
                headingSubMenu(),
                new SeparatorMenuItem(),
                codeSubMenu(),
                new SeparatorMenuItem(),
                wrapMenuItem
        );
        return markdownMenu;
    }

    public Menu helpMenu() {
        Menu helpMenu = new Menu(resources.getString("editor.menu.help"));
        helpMenu.getItems().addAll(
                createMenuItem(resources.getString("editor.menu.help.about"),
                        graphicNodeProvider.getNode(Graphic.ABOUT),
                        event -> CommandExecutor.execute(new AboutCommand(stage)),
                        KeyCombination.keyCombination("Ctrl+H"))
        );
        return helpMenu;
    }

    private Menu headingSubMenu() {
        return new Menu(resources.getString("editor.menu.markdown.heading"),
                notebookTreeViewContext.getGraphicNode(Graphic.HEADING),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h1"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(1)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+1"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h2"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(2)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+2"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h3"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(3)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+3"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h4"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(4)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+4"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h5"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(5)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+5"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.heading.h6"))
                        .performs(commandEventHandler(new HeadingTextEditorCommand(6)))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+6"))
                        .build()
        );
    }

    public Menu codeSubMenu() {
        return new Menu(resources.getString("editor.menu.markdown.code"),
                notebookTreeViewContext.getGraphicNode(Graphic.CODE),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.code.inline"))
                        .performs(commandEventHandler(new InlineCodeTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+`"))
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("editor.menu.markdown.code.block"))
                        .performs(commandEventHandler(new BlockCodeTextEditorCommand()))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+Shift+`"))
                        .build()
        );
    }

    private EventHandler<ActionEvent> commandEventHandler(TextEditorCommand command) {
        return TextEditorCommand.handleEditorCommand(
                editorContext.getEditorControlProvider()
                        .getEditorTextArea(),
                command,
                resources);
    }
}
