package com.scaffolding.configuration;

import com.scaffolding.model.*;
import org.hibernate.cfg.Configuration;

public class HibernateEntitiesConfiguration extends Configuration {

    public HibernateEntitiesConfiguration() {
        addAnnotatedClass(Bill.class);
        addAnnotatedClass(Contractor.class);
        addAnnotatedClass(Orders.class);
        addAnnotatedClass(Rates.class);
        addAnnotatedClass(Report.class);
        addAnnotatedClass(ReportItem.class);
    }
}
