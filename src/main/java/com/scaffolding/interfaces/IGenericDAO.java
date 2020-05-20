package com.scaffolding.interfaces;

import java.io.Serializable;
import java.util.List;
public interface IGenericDAO<E>
{
    Serializable save(E entity);
    public void saveOrUpdate(E entity);
    void delete( E entity );
    void deleteAll();
    List<E> findAll();
    E findById( Serializable id );
    void setSessionManager(IHibernateSessionManager sessionManager);
}