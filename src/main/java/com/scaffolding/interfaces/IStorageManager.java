package com.scaffolding.interfaces;

import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;

public interface IStorageManager {

    IStorage<OrderFX> getOrderStorage();
    IStorage<ContractorFX> getContractorStorage();
}
