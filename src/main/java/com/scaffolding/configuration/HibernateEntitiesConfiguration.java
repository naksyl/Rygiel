package com.scaffolding.configuration;

import com.scaffolding.model.Contractor;
import org.hibernate.cfg.Configuration;

public class HibernateEntitiesConfiguration extends Configuration {

    public HibernateEntitiesConfiguration() {
        addAnnotatedClass(Contractor.class);
    }
}
