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
    private List<ContractorFX> contractorList = new ArrayList<>();
    private List<ListChangeListener.Change<ContractorFX>> changeList = new ArrayList<>();
    private boolean loaded;

    @Autowired
    public ContractorStorage(IHibernateSessionManager sessionManager) {
        this.contractorDAO = sessionManager.getContractorDAO();
        loaded = false;
    }

    @Override
    public ObservableList<ContractorFX> getContractorList() {
        if (!loaded) {
            changeList.clear();
            List<Contractor> contractors = contractorDAO.findAll();
            for (Contractor contractor : contractors) {
                ContractorFX contractorFX = new ContractorFX(contractor);
                contractorList.add(contractorFX);
            }
            loaded = true;
        }
        ObservableList<ContractorFX> fxList = FXCollections.observableList(contractorList);
        fxList.addListener(new ListChangeListener<ContractorFX>() {
            @Override
            public void onChanged(Change<? extends ContractorFX> change) {

            }
        });
        return FXCollections.observableList(contractorList);
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
