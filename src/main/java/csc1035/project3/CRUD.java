package csc1035.project3;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CRUD {

    public void create(String name, String category, boolean perishable, float cost, int remaining_stock, float sell_price) {
        Stock stockToAdd = new Stock(name, category, perishable, cost, remaining_stock, sell_price);
        Session session = HibernateUtil.getSessionFactory().openSession();
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

        for (Iterator<Stock> iterator = names.iterator(); iterator.hasNext();) {
            Stock item = (Stock) iterator.next();
            if (checkedNames.contains(item.getName())) {
                return true;
            }
            checkedNames.add(item.getName());

        }
        return false;


    }




}
