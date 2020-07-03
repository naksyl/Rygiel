package com.scaffolding.interfaces;

import javafx.collections.ObservableList;

public interface IStorage<T> {
    ObservableList<T> getItemList();
    void updateItem(T item);
    void deleteItem(T item);

    void updateFromDatabase();
}
