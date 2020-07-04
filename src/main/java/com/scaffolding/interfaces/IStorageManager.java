package com.scaffolding.interfaces;

import com.scaffolding.model.jfx.BillFX;
import com.scaffolding.model.jfx.ContractorFX;
import com.scaffolding.model.jfx.OrderFX;
import com.scaffolding.model.jfx.ReportFX;

public interface IStorageManager {

    IStorage<OrderFX> getOrderStorage();

    IStorage<ContractorFX> getContractorStorage();

    IStorage<ReportFX> getReportStorage();

    IStorage<BillFX> getBillStorage();

    boolean hasOpenedFile();
}
