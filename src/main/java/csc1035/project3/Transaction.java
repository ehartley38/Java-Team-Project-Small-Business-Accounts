package csc1035.project3;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.*;

@Entity(name = "Transaction") // Table name
public class Transaction implements EPOS{

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

    public Transaction(float cost, float money_given, float change_given){
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

    @Override
    public void countStock() {

    }

    @Override
    public void customerTransaction(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
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

    @Override
    public void generateReceipt() {

    }

    @Override
    public void updateStock() {

    }
}
