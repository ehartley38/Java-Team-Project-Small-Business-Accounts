package csc1035.project3.CRUD;
import org.hibernate.Session;

import csc1035.project3.HibernateUtil;
import csc1035.project3.Stock;

public class Create {

    Stock fortnite;
    Stock apex;

    public Create() {

        fortnite = new Stock(1, "Fortnite", "Video_Games", false, 99.99f, 10,
                12.99f);

        apex = new Stock(2, "Apex", "Video_Games", false, 89.99f, 8,
                9.99f);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(fortnite);
        session.save(apex);
        session.getTransaction().commit();
        session.close();
    }

    public Stock getFortnite() {
        return fortnite;
    }

}
