package com.scaffolding.interfaces;

import com.scaffolding.model.Contractor;
import com.scaffolding.model.jfx.ContractorFX;
import javafx.collections.ObservableList;

import java.io.Serializable;

public interface IContractorStorage {

    ObservableList<ContractorFX> getContractorList();
    void updateContractor(ContractorFX contractorFX);
    void deleteContractor(ContractorFX contractorFX);
    void updateDatabase();
}
