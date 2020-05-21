package com.scaffolding.factories;

import com.scaffolding.interfaces.IGenericDAO;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.model.Contractor;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractIGenericDAO<E> implements IGenericDAO<E> {

    private final Class<E> entityClass;

    public AbstractIGenericDAO(Class<E> type) {
        this.entityClass = type;
    }

    private IHibernateSessionManager sessionManager;

    protected Session getSession() {
        return this.sessionManager.getCurrentSession();
    }

    @Override
    public E findById(final Serializable id) {
        getSession().beginTransaction();
        E entity = getSession().get(this.entityClass, id);
        getSession().getTransaction().commit();
        return entity;
    }

    @Override
    public Serializable save(E entity) {
        getSession().beginTransaction();
        Serializable id = getSession().save(entity);
        getSession().getTransaction().commit();
        return id;
    }

    @Override
    public void saveOrUpdate(E entity) {
        getSession().beginTransaction();
        getSession().saveOrUpdate(entity);
        getSession().getTransaction().commit();
    }

    @Override
    public void saveOrUpdate(List<E> entities) {
        getSession().beginTransaction();
        for(E entity: entities) {
            getSession().saveOrUpdate(entity);
        }
        getSession().getTransaction().commit();
    }

    @Override
    public void delete(E entity) {
        getSession().beginTransaction();
        getSession().delete(entity);
        getSession().getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        List<E> entities = findAll();
        getSession().beginTransaction();
        for (E entity : entities) {
            getSession().delete(entity);
        }
        getSession().getTransaction().commit();
    }

    @Override
    public List<E> findAll() {
        getSession().beginTransaction();
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<E> cq = builder.createQuery(entityClass);
        Root<E> rootEntry = cq.from(entityClass);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = getSession().createQuery(all);
        List<E> from =allQuery.getResultList();
        getSession().getTransaction().commit();
        return from;

    }

    @Override
    public void setSessionManager(IHibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}