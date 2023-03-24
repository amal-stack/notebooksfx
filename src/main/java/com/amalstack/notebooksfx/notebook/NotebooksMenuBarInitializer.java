package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.util.Initializer;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.builder.MenuItemBuilder;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCombination;

import java.util.ResourceBundle;

public class NotebooksMenuBarInitializer implements Initializer {

    private final MenuBar menuBar;
    private final NotebookTableViewContext context;
    private final ResourceBundle resources;


    public NotebooksMenuBarInitializer(MenuBar menuBar,
                                       NotebookTableViewContext context,
                                       ResourceBundle resources) {
        this.menuBar = menuBar;
        this.context = context;
        this.resources = resources;
    }

    @Override
    public void initialize() {
        menuBar.getMenus().addAll(
                fileMenu()
        );
    }

    public Menu fileMenu() {
        Menu fileMenu = new Menu(resources.getString("notebooks.menu.file"));
        fileMenu.getItems().addAll(
                new MenuItemBuilder()
                        .withText(resources.getString("notebooks.menu.file.new"))
                        .withGraphic(context.getGraphicNode(Graphic.CREATE))
                        .withAccelerator(KeyCombination.keyCombination("Ctrl+N"))
                        .performs(context.eventHandlers().create())
                        .build(),
                new MenuItemBuilder()
                        .withText(resources.getString("notebooks.menu.file.exit"))
                        .withGraphic(context.getGraphicNode(Graphic.EXIT))
                        .performs(event -> Platform.exit())
                        .build()
        );
        return fileMenu;
    }
}
