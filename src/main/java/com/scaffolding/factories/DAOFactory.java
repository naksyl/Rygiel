package com.scaffolding.factories;

import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.managers.HibernateSessionManager;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.Orders;

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
}
