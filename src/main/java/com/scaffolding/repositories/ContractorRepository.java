package com.scaffolding.repositories;

import com.scaffolding.model.Contractor;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;


public interface ContractorRepository<P> extends JpaRepository {
    
}
