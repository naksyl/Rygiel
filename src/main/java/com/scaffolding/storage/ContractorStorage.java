package com.scaffolding.storage;

import com.scaffolding.interfaces.IDatabaseAware;
import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.interfaces.IStorage;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.jfx.ContractorFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContractorStorage implements IStorage<ContractorFX>, IDatabaseAware {

    private final IGenericDAO<Contractor> contractorDAO;
    private final List<ContractorFX> contractorList = new ArrayList<>();
    private ObservableList<ContractorFX> fxList;
    private boolean loaded;

    public ContractorStorage(IHibernateSessionManager sessionManager) {
        contractorDAO = sessionManager.getContractorDAO();
        loaded = false;
    }

    @Override
    public ObservableList<ContractorFX> getItemList() {
        if (!loaded) {
            updateFromDatabase();
        }

        return fxList;
    }

    @Override
    public void updateItem(ContractorFX item) {
        contractorDAO.saveOrUpdate(item.update());
        if (!fxList.contains(item)) fxList.add(item);
    }

    @Override
    public void deleteItem(ContractorFX item) {
        contractorDAO.delete(item.update());
        fxList.remove(item);
    }

    @Override
    public void updateFromDatabase() {
        contractorList.clear();
        List<Contractor> contractors = contractorDAO.findAll();
        for (Contractor contractor : contractors) {
            ContractorFX contractorFX = new ContractorFX(contractor);
            contractorList.add(contractorFX);
        }
        fxList = FXCollections.observableList(contractorList);
        loaded = true;
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
