package csc1035.project3;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.*;

@Entity

public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", updatable = false, nullable = false)
    private int id;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "stock_category")
    private String category;

    @Column(name = "stock_perishable")
    private boolean perishable;

    @Column(name = "stock_cost")
    private float cost;

    @Column(name = "stock_remaining_stock")
    private int remaining_stock;

    @Column(name = "stock_sell_price")
    private float sell_price;

    /**
     * The constructor for a stock item
     * @param category Category of the item you wish to add
     * @param cost cost of the item you wish to add
     * @param name name of the item you wish to add
     * @param perishable true if the item is perishable, false if it is not
     * @param remaining_stock the amount of remaining stock left for the item
     * @param sell_price The price you wish to sell the item for
     * @return Nothing.
     */
    public Stock(String name, String category,
                 boolean perishable, float cost, int remaining_stock, float sell_price) {

        this.name = name;
        this.category = category;
        this.perishable = perishable;
        this.cost = cost;
        this.remaining_stock = remaining_stock;
        this.sell_price = sell_price;
    }

    public Stock() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getRemaining_stock() {
        return remaining_stock;
    }

    public void setRemaining_stock(int remaining_stock) {
        this.remaining_stock = remaining_stock;
    }

    public float getSell_price() {
        return sell_price;
    }

    public void setSell_price(float sell_price) {
        this.sell_price = sell_price;
    }
}
