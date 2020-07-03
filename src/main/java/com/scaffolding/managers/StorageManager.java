package com.scaffolding.managers;

import com.scaffolding.interfaces.IStorage;
import com.scaffolding.interfaces.IStorageManager;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageManager implements IStorageManager {

    private final IStorage<ContractorFX> contractors;
    private final IStorage<OrderFX> orders;

    @Autowired
    public StorageManager(IStorage<ContractorFX> contractors,
                          IStorage<OrderFX> orders) {
        this.orders = orders;
        this.contractors = contractors;
    }



    @Override
    public IStorage<OrderFX> getOrderStorage() {
        return orders;
    }

    @Override
    public IStorage<ContractorFX> getContractorStorage() {
        return contractors;
    }

}
