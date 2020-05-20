package com.scaffolding.interfaces;

import com.scaffolding.model.Contractor;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.List;

public interface IStorageManager {

    ObservableList<Contractor> getContractorList();

}
