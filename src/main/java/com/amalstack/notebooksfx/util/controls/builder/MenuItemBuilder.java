package com.amalstack.notebooksfx.util.controls.builder;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.command.Commands;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.function.Consumer;

public class MenuItemBuilder implements ControlBuilder<MenuItem, MenuItemBuilder> {
    private String id;
    private Consumer<MenuItem> config;
    private String text;
    private Node graphic;
    private Command command;


    public MenuItemBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public MenuItemBuilder withGraphic(Node graphic) {
        this.graphic = graphic;
        return this;
    }

    public MenuItemBuilder performs(Command command) {
        this.command = command;
        return this;
    }

    @Override
    public MenuItemBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public MenuItemBuilder configure(Consumer<MenuItem> config) {
        this.config = config;
        return this;
    }


    public MenuItem build() {
        var menuItem = new MenuItem(text, graphic);
        menuItem.setId(id);
        menuItem.setOnAction(Commands.eventHandler(command));
        if (config != null) {
            config.accept(menuItem);
        }
        return menuItem;
    }

}
