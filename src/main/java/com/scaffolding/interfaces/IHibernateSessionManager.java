package com.scaffolding.interfaces;


import com.scaffolding.model.Contractor;
import com.scaffolding.model.Orders;
import org.hibernate.Session;

import java.io.File;

public interface IHibernateSessionManager {

    boolean openSession(File fileName, String password);
    boolean hasOpenedSession();
    void closeSession();
    Session getCurrentSession();

    IGenericDAO<Contractor> getContractorDAO();
    IGenericDAO<Orders> getOrderDAO();
}
