package csc1035.project3;
import javax.persistence.*;

@Entity
@Table(name = "Transaction") // Table name
public class Transaction implements EPOS{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tID", updatable = false, nullable = false)
    private final int id;

    @Column(name = "Cost")
    private float cost;

    @Column(name = "Money Given")
    private float money_given;

    @Column (name = "Change")
    private float change;

    public Transaction(int id, float cost, float money_given, float change){
        this.id = id;
        this.cost = cost;
        this.money_given = money_given;
        this.change = change;
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

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    @Override
    public void countStock() {

    }

    public void customerTransaction(){
        System.out.println();
    }

    @Override
    public void generateReceipt() {

    }

    @Override
    public void updateStock() {

    }
}
