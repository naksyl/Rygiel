package com.scaffolding.factories;

import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.model.Contractor;

public class DAOFactory {

    public static IGenericDAO<Contractor> getContractorDAO(IHibernateSessionManager sessionManager) {
        IGenericDAO<Contractor> dao = new AbstractIGenericDAO<>(Contractor.class) {};
        dao.setSessionManager(sessionManager);
        return dao;
    }
}
