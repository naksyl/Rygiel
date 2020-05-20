package com.scaffolding.model;

import com.scaffolding.factories.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Test {
    public static void main(String[] args) {
        SessionFactory factory = HibernateSessionFactory.getSessionFactory(null, "admin");

        try (Session session = factory.getCurrentSession()) {
            Contractor nowy = new Contractor("Rusztmont", "Płochocińska 65", "Tomasz Wójcik", "krzysztof@rusztmont.vp.pl", "55342647589");
            session.beginTransaction();
            session.save(nowy);
            session.getTransaction().commit();
        }
        factory.close();
    }
}
