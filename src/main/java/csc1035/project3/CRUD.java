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
        if (!checkDuplicates()) {
            session.save(stockToAdd);
            session.getTransaction().commit();
            session.close();
        } else {
          System.out.println("Item already in stock");
        }
    }

    public boolean checkDuplicates() {

        List stock = session.createQuery("FROM Stock").list();
        ArrayList<String> checkedNames = new ArrayList<String>();

        for (Iterator<Stock> iterator = stock.iterator(); iterator.hasNext();) {
            Stock item = (Stock) iterator.next();
            if (checkedNames.contains(item.getName())) {
                return true;
            }
            checkedNames.add(item.getName());
        }
        return false;

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

}
