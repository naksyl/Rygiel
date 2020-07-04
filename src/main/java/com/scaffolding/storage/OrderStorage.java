package com.scaffolding.storage;

import com.scaffolding.interfaces.*;
import com.scaffolding.model.Orders;
import com.scaffolding.model.jfx.OrderFX;
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
public class OrderStorage implements IStorage<OrderFX>, IDatabaseAware {

    private final IGenericDAO<Orders> orderDAO;
    private final List<OrderFX> orderList = new ArrayList<>();
    private ObservableList<OrderFX> fxList = FXCollections.emptyObservableList();
    private boolean loaded;
    private IStorageManager storageManager;

    @Autowired
    public void setStorageManager(@Lazy IStorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public OrderStorage(IHibernateSessionManager sessionManager) {
        orderDAO = sessionManager.getOrderDAO();
        loaded = false;
    }

    @Override
    public ObservableList<OrderFX> getItemList() {
        if (!loaded) {
            updateFromDatabase();
        }

        return fxList;
    }

    @Override
    public void updateItem(OrderFX item) {
        orderDAO.saveOrUpdate(item.update());
        if (!fxList.contains(item)) fxList.add(item);
    }

    @Override
    public void deleteItem(OrderFX item) {
        orderDAO.delete(item.update());
        fxList.remove(item);
    }

    @Override
    public OrderFX findById(int id) {
        OrderFX found = null;
        if(!loaded && storageManager.hasOpenedFile()) updateFromDatabase();
        if (loaded) {
            for (OrderFX fx : fxList) {
                if (fx.getOrder().getId() == id) {
                    found = fx;
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public void updateFromDatabase() {
        orderList.clear();
        List<Orders> orders = orderDAO.findAll();
        for (Orders order : orders) {
            OrderFX orderFX = new OrderFX(order);
            orderFX.setContractorFX(storageManager.getContractorStorage()
                    .findById(order.getId()));
            if (order.getReport() != null) {
                orderFX.setReportFX(storageManager.getReportStorage()
                        .findById(order.getReport().getId()));
            }
            if (order.getBill() != null) {
                orderFX.setBillFX(storageManager.getBillStorage()
                        .findById(order.getBill().getId()));
            }
            orderList.add(orderFX);
        }
        fxList = FXCollections.observableList(orderList);
        loaded = true;
    }

    @Override
    public void onDatabaseOpen(File file) {
        updateFromDatabase();
    }

    @Override
    public void onDatabaseClose() {
        loaded = false;
        orderList.clear();
    }
}
