package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CRUD {

    Session session;

    public void create(String name, String category, boolean perishable, float cost, int remaining_stock, float sell_price) {
        Stock stockToAdd = new Stock(name, category, perishable, cost, remaining_stock, sell_price);
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        if (!checkDuplicates(name)) {
            session.save(stockToAdd);
            session.getTransaction().commit();
            session.close();
        } else {
          System.out.println("Item already in stock");
        }
    }

    public void read(String name) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List items = session.createQuery("FROM Stock").list();
            for (Iterator<Stock> iterator = items.iterator(); iterator.hasNext();){
                Stock stock = iterator.next();
                if (stock.getName().equals(name))
                    System.out.println("Remaining Stock: " + stock.getRemaining_stock());
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private boolean checkDuplicates(String name) {

        List stock = session.createQuery("FROM Stock").list();

        for (Stock item : (Iterable<Stock>) stock) {
            if (item.getName().equals(name)) {
                return true;
            }

        }
        return false;
    }

}
