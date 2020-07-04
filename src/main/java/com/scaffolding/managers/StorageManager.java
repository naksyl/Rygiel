package com.scaffolding.managers;

import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IStorage;
import com.scaffolding.interfaces.IStorageManager;
import com.scaffolding.model.jfx.BillFX;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import com.scaffolding.model.jfx.ReportFX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class StorageManager implements IStorageManager {

    private final IStorage<ContractorFX> contractors;
    private final IStorage<OrderFX> orders;
    private final IStorage<ReportFX> reports;
    private final IStorage<BillFX> bills;
    private final IApplicationManager applicationManager;

    @Autowired
    public StorageManager(IStorage<ContractorFX> contractors, IStorage<OrderFX> orders,
                          IStorage<ReportFX> reports, IStorage<BillFX> bills,
                          @Lazy IApplicationManager applicationManager) {
        this.orders = orders;
        this.contractors = contractors;
        this.reports = reports;
        this.bills = bills;
        this.applicationManager = applicationManager;
    }



    @Override
    public IStorage<OrderFX> getOrderStorage() {
        return orders;
    }

    @Override
    public IStorage<ContractorFX> getContractorStorage() {
        return contractors;
    }

    @Override
    public IStorage<ReportFX> getReportStorage() {
        return reports;
    }

    @Override
    public IStorage<BillFX> getBillStorage() {
        return bills;
    }

    @Override
    public boolean hasOpenedFile() {
        return applicationManager.hasOpenedFile();
    }

}
