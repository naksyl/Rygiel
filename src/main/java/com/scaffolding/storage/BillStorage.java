package com.scaffolding.storage;

import com.scaffolding.interfaces.*;
import com.scaffolding.model.Bill;
import com.scaffolding.model.jfx.BillFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class BillStorage implements IStorage<BillFX>, IDatabaseAware {
    private final IGenericDAO<Bill> billDAO;
    private final List<BillFX> billList = new ArrayList<>();
    private ObservableList<BillFX> fxList;
    private boolean loaded;
    private IStorageManager storageManager;

    @Autowired
    public void setStorageManager(@Lazy IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public BillStorage(IHibernateSessionManager sessionManager) {
        billDAO = sessionManager.getBillDAO();
        loaded = false;
    }

    @Override
    public ObservableList<BillFX> getItemList() {
        if (!loaded) {
            updateFromDatabase();
        }

        return fxList;
    }

    @Override
    public void updateItem(BillFX item) {
        billDAO.saveOrUpdate(item.update());
        if (!fxList.contains(item)) fxList.add(item);
    }

    @Override
    public void deleteItem(BillFX item) {
        billDAO.delete(item.update());
        fxList.remove(item);
    }

    @Override
    public BillFX findById(int id) {
        BillFX found = null;
        if(!loaded && storageManager.hasOpenedFile()) updateFromDatabase();
        if (loaded) {
            for (BillFX fx : fxList) {
                if (fx.getBill().getId() == id) {
                    found = fx;
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public void updateFromDatabase() {
        billList.clear();
        List<Bill> bills = billDAO.findAll();
        for (Bill bill : bills) {
            BillFX billFX = new BillFX(bill);

            billFX.setOrder(storageManager.getOrderStorage()
                    .findById(bill.getOrder().getId()));
            billFX.setReport(storageManager.getReportStorage()
                    .findById(bill.getOrder().getReport().getId()));
            billFX.setContractor(storageManager.getContractorStorage()
                    .findById(bill.getOrder().getContractor().getId()));
            billList.add(billFX);

            fxList = FXCollections.observableList(billList);
            loaded = true;
        }
    }

    @Override
    public void onDatabaseOpen(File file) {
        updateFromDatabase();
    }

    @Override
    public void onDatabaseClose() {
        loaded = false;
        billList.clear();
    }
}
