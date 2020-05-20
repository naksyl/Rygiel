package com.scaffolding.managers;

import com.scaffolding.factories.DAOFactory;
import com.scaffolding.factories.HibernateSessionFactory;
import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.model.Contractor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class HibernateSessionManager implements IHibernateSessionManager {

    private SessionFactory sessionFactory;
    private final IGenericDAO<Contractor> contractorDAO;

    public HibernateSessionManager() {
        contractorDAO = DAOFactory.getContractorDAO(this);
    }

    @Override
    public boolean openSession(File fileName, String password) {
        sessionFactory = HibernateSessionFactory.getSessionFactory(fileName,password);
        contractorDAO.setSessionManager(this);
        return false;
    }

    @Override
    public void closeSession() {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.close();
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public IGenericDAO<Contractor> getContractorDAO() {
        return contractorDAO;
    }
}
