package com.scaffolding.storage;

import com.scaffolding.interfaces.*;
import com.scaffolding.model.Orders;
import com.scaffolding.model.Report;
import com.scaffolding.model.jfx.ReportFX;
import com.scaffolding.model.jfx.ReportFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReportStorage implements IStorage<ReportFX>, IDatabaseAware {


    private final IGenericDAO<Report> reportDAO;
    private final List<ReportFX> reportList = new ArrayList<>();
    private ObservableList<ReportFX> fxList;
    private boolean loaded;
    private IStorageManager storageManager;

    @Autowired
    public void setStorageManager(@Lazy IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public ReportStorage(IHibernateSessionManager sessionManager) {
        reportDAO = sessionManager.getReportDAO();
        loaded = false;
    }

    @Override
    public ObservableList<ReportFX> getItemList() {
        if (!loaded) {
            updateFromDatabase();
        }

        return fxList;
    }

    @Override
    public void updateItem(ReportFX item) {
        reportDAO.saveOrUpdate(item.update());
        if (!fxList.contains(item)) fxList.add(item);
    }

    @Override
    public void deleteItem(ReportFX item) {
        reportDAO.delete(item.update());
        fxList.remove(item);
    }

    @Override
    public ReportFX findById(int id) {
        ReportFX found = null;
        if (!loaded && storageManager.hasOpenedFile()) updateFromDatabase();
        if (loaded) {
            for (ReportFX fx : fxList) {
                if (fx.getReport().getId() == id) {
                    found = fx;
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public void updateFromDatabase() {
        reportList.clear();
        List<Report> reports = reportDAO.findAll();
        for (Report report : reports) {
            ReportFX reportFX = new ReportFX(report);
            reportFX.setOrder(storageManager.getOrderStorage()
                    .findById(report.getOrder().getId()));
            reportFX.setContractor(storageManager.getContractorStorage()
                    .findById(report.getOrder().getContractor().getId()));
            reportList.add(reportFX);
        }
        fxList = FXCollections.observableList(reportList);
        loaded = true;
    }

    @Override
    public void onDatabaseOpen(File file) {
        updateFromDatabase();
    }

    @Override
    public void onDatabaseClose() {
        loaded = false;
        reportList.clear();
    }
}
