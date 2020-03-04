package csc1035.project3;

import org.hibernate.Session;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.util.List;

public class UserMenu implements EPOS {

    public void menu() throws IOException {
        String option;
        System.out.println("Welcome to the Tricky Trinkets EPOS system.");
        System.out.println("Please select an option:");
        System.out.println("a - View all current stock.");
        System.out.println("b - Make transaction.");
        System.out.println("c - Produce receipt.");
        System.out.println("d - Update stock.");
        System.out.println("q - Exit the program.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        option = reader.readLine().toLowerCase();

        switch (option) {
            case "a":
                countStock();
                break;
            case "b":
                addCustomerTransaction();
                break;
            case "c":
                generateReceipt();
                break;
            case "d":
                updateStock();
                break;
            case "q":
                break;
            default:
                System.out.println("Error - Please only enter 'a', 'b', 'c' or 'q'.");
                menu();
        }
    }

    @Override
    public void countStock() {

    }

    @Override
    public void addCustomerTransaction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean itemFound = false;
        float cost = 0f; //Dummy value.
        CRUD crud = new CRUD();
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List items = s.createQuery("From Stock").list();
        s.getTransaction().commit();

        System.out.println("Please enter the first item of the transaction.");
        String transactionItemString = reader.readLine();
        for (Stock stock : (Iterable<Stock>) items) {
            if (transactionItemString.equals(stock.getName())) {
                itemFound = true;
                if (stock.getRemaining_stock() > 0) {
                    cost += stock.getSell_price();
                    crud.update(stock.getId(), "stock_remaining_stock", Integer.toString(stock.getRemaining_stock() - 1));

                    System.out.println("Please enter the amount of money given:");
                    String moneyGivenInput = reader.readLine() + "f";
                    float moneyGiven = Float.parseFloat(moneyGivenInput);

                    System.out.println("Please enter the amount of change given back:");
                    String changeGivenInput = reader.readLine() + "f";
                    float changeGiven = Float.parseFloat(changeGivenInput);

                    Transaction newTransaction = new Transaction(cost, moneyGiven, changeGiven);
                    newTransaction.addCustomerTransaction();
                    newTransaction.generateReceipt();
                } else {
                    System.out.println("ITEM " + stock.getName() + " OUT OF STOCK.");
                }
            }
        }

        if (!itemFound) {
            System.out.println("ITEM " + transactionItemString + " NOT FOUND.");
        }
    }
    @Override
    public void generateReceipt() {

    }

    @Override
    public void updateStock() throws IOException{
        String option;
        System.out.println("Please select an option: ");
        System.out.println("a - Add an item to the stock.");
        System.out.println("b - Remove an item from the stock.");
        System.out.println("c - Update an item.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        option = reader.readLine().toLowerCase();
        switch (option) {
            case "a":
                addItem();
                break;
            case "b":
                removeItem();
                break;
            case "c":
                updateItem();
            default:
                System.out.println("Error - Please only enter 'a', 'b' or 'c'.");
                updateStock();
        }
    }

    private void addItem() throws IOException{
        String name;
        String category;
        boolean perishable;
        float cost;
        int remaining_stock;
        float sell_price;
        String perishableString;
        System.out.println("Item's name: ");
        BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
        name = nameReader.readLine();
        System.out.println("Item's category: ");
        BufferedReader categoryReader = new BufferedReader(new InputStreamReader(System.in));
        category = categoryReader.readLine();
        System.out.println("Is the item perishable (true or false): ");
        BufferedReader perishReader = new BufferedReader(new InputStreamReader(System.in));
        perishable = Boolean.parseBoolean(perishReader.readLine());
        System.out.println("Item's cost: ");
        BufferedReader costReader = new BufferedReader(new InputStreamReader(System.in));
        cost = Float.parseFloat(costReader.readLine() + "f");
        System.out.println("Item's stock: ");
        BufferedReader stockReader = new BufferedReader(new InputStreamReader(System.in));
        remaining_stock = Integer.parseInt(stockReader.readLine());
        System.out.println("Item's sell price: ");
        BufferedReader priceReader = new BufferedReader(new InputStreamReader(System.in));
        sell_price = Float.parseFloat(priceReader.readLine() + "f");
        CRUD crud = new CRUD();
        crud.create(name, category, perishable, cost, remaining_stock, sell_price);
    }

    private void removeItem() throws IOException{
        int ID;
        String name;
        String option;
        System.out.println("Do you want to delete an item using name or id?: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        option = reader.readLine().toLowerCase();
        switch (option) {
            case "name":
                System.out.println("Enter the name of an item that you want to remove: ");
                BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
                name = nameReader.readLine();
                CRUD nameCrud = new CRUD();
                nameCrud.deleteByName(name);
                break;
            case "id":
                System.out.println("Enter ID of an item that you want to remove: ");
                BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
                ID = Integer.parseInt(idReader.readLine());
                CRUD idCrud = new CRUD();
                idCrud.deleteById(ID);
                break;
            default:
                System.out.println("You can only choose between name and id");
                removeItem();
        }
    }

    private void updateItem() throws IOException{
        int ID;
        String column;
        String newValue;
        System.out.println("Item's ID: ");
        BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
        ID = Integer.parseInt(idReader.readLine());
        System.out.println("Enter the name of the column that you want to change: ");
        BufferedReader columnReader = new BufferedReader(new InputStreamReader(System.in));
        column = columnReader.readLine().toLowerCase();
        System.out.println("Enter a new value: ");
        BufferedReader newValueReader = new BufferedReader(new InputStreamReader(System.in));
        newValue = newValueReader.readLine();
        CRUD crud = new CRUD();
        crud.update(ID, column, newValue);
    }
}
