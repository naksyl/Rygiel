package com.scaffolding.factories;

import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.managers.HibernateSessionManager;
import com.scaffolding.model.Bill;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.Orders;
import com.scaffolding.model.Report;

public class DAOFactory {

    public static IGenericDAO<Contractor> getContractorDAO(IHibernateSessionManager sessionManager) {
        IGenericDAO<Contractor> dao = new AbstractIGenericDAO<>(Contractor.class) {};
        dao.setSessionManager(sessionManager);
        return dao;
    }

    public static IGenericDAO<Orders> getOrderDAO(IHibernateSessionManager hibernateSessionManager) {
        IGenericDAO<Orders> dao = new AbstractIGenericDAO<>(Orders.class) {};
        dao.setSessionManager(hibernateSessionManager);
        return dao;
    }

    public static IGenericDAO<Report> getReportDAO(IHibernateSessionManager hibernateSessionManager) {
        IGenericDAO<Report> dao = new AbstractIGenericDAO<>(Report.class) {};
        dao.setSessionManager(hibernateSessionManager);
        return dao;
    }

    public static IGenericDAO<Bill> getBillDAO(IHibernateSessionManager hibernateSessionManager) {
        IGenericDAO<Bill> dao = new AbstractIGenericDAO<>(Bill.class) {};
        dao.setSessionManager(hibernateSessionManager);
        return dao;
    }
}
