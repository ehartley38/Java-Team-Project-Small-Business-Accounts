package csc1035.project3;

import javax.persistence.*;

@Entity
@Table(name = "Stock")

public class Stock implements EPOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", updatable = false, nullable = false)
    private final int id;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "stock_category")
    private String category;

    @Column(name = "stock_perishable")
    private String perishable;

    @Column (name = "stock_cost")
    private String cost;

    @Column (name = "stock_remaining_stock")
    private String remaining_stock;

    @Column (name = "stock_sell_price")
    private String sell_price;



    @Override
    public void countStock() {

    }

    @Override
    public void customerTransaction() {

    }

    @Override
    public void generateReceipt() {

    }

    @Override
    public void updateStock() {

    }
}
