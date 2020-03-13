package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.*;

public class CRUD {

    Session session;

    /**
     * Creates a new item in the database
     * @param name The name of the item you want to add
     * @param category The category of the item you want to add
     * @param perishable True if the item is perishable, false if it is not
     * @param cost The cost of the item
     * @param remaining_stock How much stock is left of the item
     * @param sell_price How much you are selling the item for
     */
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

    /**
     * Reads an information about an item from the database
     * @param name The name of the item you want to read
     * This code does not have any use currently;
     * It can be used for future development
     */
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

    /**
     * This method updates an item in the database with new information
     * @param id The id of the item you want to update
     * @param columnName The column name of the item you want to update
     * @param newValue The new value you want to update the database with
     * @return boolean invalidName returns true if the given column name doesn't exist
     */
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

    /**
     * Deletes an item from the database
     * @param id of the item you want to delete
     */
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

    /**
     * Returns the id of a given item in the database
     * @param name The name of the item of which you want to find its id
     * @return int Returns the id of the given item
     */
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

    /**
     * Checks if the item you are trying to add already exists in the database
     * @param name The name of the item you want to check duplicates for
     * @return boolean Return true if the item already exists in the database.
     */
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
