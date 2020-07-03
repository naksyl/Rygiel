package com.scaffolding.storage;

import com.scaffolding.interfaces.IDatabaseAware;
import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.interfaces.IStorage;
import com.scaffolding.model.Orders;
import com.scaffolding.model.jfx.OrderFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderStorage implements IStorage<OrderFX>, IDatabaseAware {

    private final IGenericDAO<Orders> orderDAO;
    private final List<OrderFX> orderList = new ArrayList<>();
    private ObservableList<OrderFX> fxList;
    private boolean loaded;

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
    public void updateFromDatabase() {
        orderList.clear();
        List<Orders> orders = orderDAO.findAll();
        for (Orders order : orders) {
            OrderFX orderFX = new OrderFX(order);
            orderList.add(orderFX);
        }
        fxList = FXCollections.observableList(orderList);
        loaded = true;
    }

    @Override
    public void onDatabaseOpen(File file) {

    }

    @Override
    public void onDatabaseClose() {
        loaded = false;
        orderList.clear();
    }
}
