package com.amalstack.notebooksfx.data.model;

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
        if (error.fieldErrors() != null) {
            for (ErrorResponse.FieldError fieldError : error.fieldErrors()) {
                errorEntries.add(new ErrorEntry(fieldError.field(), fieldError.message()));
            }
        }

        if (error.timestamp() != null) {
            errorEntries.add(new ErrorEntry("timestamp", error.timestamp()));
        }
        errorEntries.add(new ErrorEntry("status", String.valueOf(error.status())));
        if (error.error() != null) {
            errorEntries.add(new ErrorEntry("error", error.error()));
        }
        errorEntries.add(new ErrorEntry("message", error.message()));
        if (error.path() != null) {
            errorEntries.add(new ErrorEntry("path", error.path()));
        }

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
