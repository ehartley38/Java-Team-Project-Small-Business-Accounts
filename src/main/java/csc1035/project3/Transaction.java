package csc1035.project3;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.*;

@Entity(name = "Transaction") // Table name
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    @Column
    private float cost;

    @Column
    private float money_given;

    @Column
    private float change_given;

    public Transaction(float cost, float money_given, float change_given) {
        this.cost = cost;
        this.money_given = money_given;
        this.change_given = change_given;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getMoney_given() {
        return money_given;
    }

    public void setMoney_given(float money_given) {
        this.money_given = money_given;
    }

    public float getChange_given() {
        return change_given;
    }

    public void setChange_given(float change_given) {
        this.change_given = change_given;
    }

    /**
     * Allows for a customer transaction of stock.
     */
    public void addCustomerTransaction() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(this);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Produces a receipt of a transaction when given a transaction ID.
     * @param id A transaction ID passed into the table.
     */
    public void generateReceipt(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Transaction receipt = session.getReference(Transaction.class, id);
            String id2 = Integer.toString(receipt.getId());
            String cost = String.valueOf(receipt.getCost());
            String moneyGiven = String.valueOf(receipt.getMoney_given());
            String changeGiven = String.valueOf(receipt.getChange_given());


            String leftAlignFormat = " |%-16s | %-8s | %-11s | %-12s | %n ";

            System.out.format(" +-----------------+----------+-------------+--------------+%n");
            System.out.format(" | Transaction ID  | Cost     | Money Given | Change Given |%n");
            System.out.format(" +-----------------+----------+-------------+--------------|%n");
            System.out.format(leftAlignFormat, id2, cost, moneyGiven,
                    changeGiven);
            System.out.format("+-----------------+----------+-------------+--------------+%n");
            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}

