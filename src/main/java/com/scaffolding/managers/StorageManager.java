package com.scaffolding.managers;

import com.scaffolding.interfaces.IStorageManager;
import com.scaffolding.interfaces.IContractorStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageManager implements IStorageManager {


    private final IContractorStorage contractorStorage;

    @Autowired
    public StorageManager(IContractorStorage contractorStorage) {
        this.contractorStorage = contractorStorage;
    }

    @Override
    public IContractorStorage getContractorStorage() {
        return contractorStorage;
    }
}
