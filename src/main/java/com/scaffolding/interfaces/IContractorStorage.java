package com.scaffolding.interfaces;

import com.scaffolding.model.jfx.ContractorFX;
import javafx.collections.ObservableList;

import java.io.Serializable;

public interface IContractorStorage {

    ObservableList<ContractorFX> getContractorList();
    void updateDatabase();
}
