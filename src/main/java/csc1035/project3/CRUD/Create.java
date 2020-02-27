package csc1035.project3.CRUD;
import org.hibernate.Session;

import csc1035.project3.HibernateUtil;
import csc1035.project3.Stock;

public class Create {

    public void add(String name, String category, boolean perishable, float cost, int remaining_stock, float sell_price) {
        Stock stockToAdd = new Stock(name, category, perishable, cost, remaining_stock, sell_price);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(stockToAdd);
        session.getTransaction().commit();
        session.close();

    }


}
