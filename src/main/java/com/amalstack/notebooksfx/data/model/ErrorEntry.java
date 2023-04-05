package com.amalstack.notebooksfx.data.model;

import com.amalstack.notebooksfx.util.http.ErrorResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ErrorEntry {
    private final SimpleStringProperty key = new SimpleStringProperty(this, "key");
    private final SimpleStringProperty value = new SimpleStringProperty(this, "value");

    public ErrorEntry(String key, String value) {
        this.key.set(key);
        this.value.set(value);
    }

    public static ObservableList<ErrorEntry> fromError(ErrorResponse error) {
        ObservableList<ErrorEntry> errorEntries = FXCollections.observableArrayList();
        if (error.errors() != null) {
            for (var errorEntry : error.errors().entrySet()) {
                errorEntries.add(new ErrorEntry(errorEntry.getKey(), errorEntry.getValue()));
            }
        }
        errorEntries.add(new ErrorEntry("status", String.valueOf(error.status())));

        if (error.error() != null) {
            errorEntries.add(new ErrorEntry("error", error.error()));
        }

        error.timestamp()
                .ifPresent(ts -> errorEntries.add(new ErrorEntry("timestamp", ts)));
        error.message()
                .ifPresent(m -> errorEntries.add(new ErrorEntry("message", m)));
        error.path()
                .ifPresent(p -> errorEntries.add(new ErrorEntry("path", p)));

        return errorEntries;
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public ObservableValue<String> keyProperty() {
        return key;
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public ObservableValue<String> valueProperty() {
        return value;
    }
}
