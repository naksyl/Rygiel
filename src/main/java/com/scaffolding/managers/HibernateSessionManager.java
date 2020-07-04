package com.scaffolding.managers;

import com.scaffolding.factories.DAOFactory;
import com.scaffolding.factories.HibernateSessionFactory;
import com.scaffolding.interfaces.IDatabaseAware;
import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.model.Bill;
import com.scaffolding.model.Contractor;
import com.scaffolding.model.Orders;
import com.scaffolding.model.Report;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class HibernateSessionManager implements IHibernateSessionManager {

    private SessionFactory sessionFactory;
    private List<IDatabaseAware> databaseAwareList;
    private final IGenericDAO<Contractor> contractorDAO;
    private final IGenericDAO<Orders> orderDAO;
    private final IGenericDAO<Report> reportDAO;
    private final IGenericDAO<Bill> billDAO;
    private boolean opened;

    public HibernateSessionManager() {
        contractorDAO = DAOFactory.getContractorDAO(this);
        orderDAO = DAOFactory.getOrderDAO(this);
        reportDAO = DAOFactory.getReportDAO(this);
        billDAO = DAOFactory.getBillDAO(this);
    }

    @Autowired
    public void setDatabaseAwareList(@Lazy List<IDatabaseAware> databaseAwareList) {
        this.databaseAwareList = databaseAwareList;
    }

    @Override
    public boolean openSession(File fileName, String password) {
        closeSession();
        sessionFactory = HibernateSessionFactory.getSessionFactory(fileName, password);
        contractorDAO.setSessionManager(this);
        orderDAO.setSessionManager(this);
        for (IDatabaseAware databaseAware : databaseAwareList)
            databaseAware.onDatabaseOpen(fileName);
        opened = true;
        return true;
    }

    @Override
    public boolean hasOpenedSession() {
        return opened;
    }

    @Override
    public void closeSession() {
        if (opened) {
            sessionFactory.getCurrentSession().beginTransaction();
            sessionFactory.getCurrentSession().flush();
            sessionFactory.getCurrentSession().getTransaction().commit();

            for (IDatabaseAware databaseAware : databaseAwareList)
                databaseAware.onDatabaseClose();
            sessionFactory.close();
        }
        opened = false;
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public IGenericDAO<Contractor> getContractorDAO() {
        return contractorDAO;
    }

    @Override
    public IGenericDAO<Orders> getOrderDAO() {
        return orderDAO;
    }

    @Override
    public IGenericDAO<Report> getReportDAO() {
        return reportDAO;
    }

    @Override
    public IGenericDAO<Bill> getBillDAO() {
        return billDAO;
    }
}
