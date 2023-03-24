package com.amalstack.notebooksfx.util.controls.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

import java.util.function.Consumer;

public class MenuItemBuilder implements ControlBuilder<MenuItem, MenuItemBuilder> {
    private String id;
    private Consumer<MenuItem> config;
    private String text;
    private Node graphic;
    private KeyCombination keyCombination;
    private EventHandler<ActionEvent> eventHandler;


    public MenuItemBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public MenuItemBuilder withGraphic(Node graphic) {
        this.graphic = graphic;
        return this;
    }

    public MenuItemBuilder withAccelerator(KeyCombination keyCombination) {
        this.keyCombination = keyCombination;
        return this;
    }

    public MenuItemBuilder performs(EventHandler<ActionEvent> eventHandler) {
        this.eventHandler = eventHandler;
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
        if (id != null) {
            menuItem.setId(id);
        }
        menuItem.setOnAction(eventHandler);
        if (keyCombination != null) {
            menuItem.setAccelerator(keyCombination);
        }
        if (config != null) {
            config.accept(menuItem);
        }
        return menuItem;
    }

}
