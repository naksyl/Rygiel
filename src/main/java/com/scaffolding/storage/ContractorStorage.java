package com.scaffolding.storage;

import com.scaffolding.interfaces.IContractorStorage;
import com.scaffolding.interfaces.IDatabaseAware;
import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.jfx.ContractorFX;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContractorStorage implements IContractorStorage, IDatabaseAware {

    private final IGenericDAO<Contractor> contractorDAO;
    private final List<ContractorFX> contractorList = new ArrayList<>();
    private ObservableList<ContractorFX> fxList;
    private boolean loaded;

    @Autowired
    public ContractorStorage(IHibernateSessionManager sessionManager) {
        this.contractorDAO = sessionManager.getContractorDAO();
        loaded = false;
    }

    @Override
    public ObservableList<ContractorFX> getContractorList() {
        if (!loaded) {
            List<Contractor> contractors = contractorDAO.findAll();
            for (Contractor contractor : contractors) {
                ContractorFX contractorFX = new ContractorFX(contractor);
                contractorList.add(contractorFX);
            }
            fxList = FXCollections.observableList(contractorList);
            loaded = true;
        }

        return fxList;
    }

    @Override
    public void updateContractor(ContractorFX contractorFX) {
        contractorDAO.saveOrUpdate(contractorFX.update());
        if(!fxList.contains(contractorFX)) fxList.add(contractorFX);
    }

    @Override
    public void deleteContractor(ContractorFX contractorFX) {
        contractorDAO.delete(contractorFX.update());
        fxList.remove(contractorFX);
    }

    @Override
    public void updateDatabase() {
        List<Contractor> contractors = new ArrayList<>();
        for (ContractorFX contractorFX : contractorList)
            contractors.add(contractorFX.getContractor());
        contractorDAO.saveOrUpdate(contractors);
    }

    @Override
    public void onDatabaseOpen(File file) {

    }

    @Override
    public void onDatabaseClose() {
        loaded = false;
        contractorList.clear();
    }
}
