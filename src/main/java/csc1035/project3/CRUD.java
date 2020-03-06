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
            for (Stock stock : (Iterable<Stock>) items) {
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

    public boolean update(int id, String columnName, String newValue) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean invalidName = false;
        Stock item = (session.get(Stock.class, id));
        switch (columnName) {
            case "stock_category":
                item.setCategory(newValue);
                break;
            case "stock_cost":
                item.setCost(Float.parseFloat(newValue));
                break;
            case "stock_name":
                item.setName(newValue);
                break;
            case "stock_perishable":
                item.setPerishable(Boolean.parseBoolean(newValue));
                break;
            case "stock_remaining_stock":
                item.setRemaining_stock(Integer.parseInt(newValue));
                break;
            case "stock_sell_price":
                item.setSell_price(Float.parseFloat(newValue));
                break;
            default:
                System.out.println("Not a valid column name");
                invalidName = true;
                break;
        }
        session.update(item);
        session.getTransaction().commit();
        return invalidName;
    }

    public void delete(int id) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List items = session.createQuery("FROM Stock").list();
            for (Iterator<Stock> iterator = items.iterator(); iterator.hasNext();){
                Stock stock = iterator.next();
                if (stock.getId() == id)
                    session.delete(stock);
            }
            session.getTransaction().commit();
            System.out.println("Item with an id " + id + " was deleted from the table.");
        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public int getSid(String name) {
        int id = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List items = session.createQuery("FROM Stock").list();
            for (Stock stock : (Iterable<Stock>) items) {
                if (stock.getName().equals(name))
                    id = stock.getId();
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public boolean checkDuplicates(String name) {

        List stock = session.createQuery("FROM Stock").list();
        ArrayList<String> names = new ArrayList<String>();

        for (Stock o : (Iterable<Stock>) stock) {
            Stock item = o;
            names.add(item.getName());
        }

        return names.contains(name);

    }

}
