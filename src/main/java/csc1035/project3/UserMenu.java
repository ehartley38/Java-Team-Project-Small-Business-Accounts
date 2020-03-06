package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.print.DocFlavor;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class UserMenu implements EPOS {

    Session session;

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
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            CRUD crud = new CRUD();
            List items = session.createQuery("FROM Stock").list();

            String leftAlignFormat = " |%-9s | %-18s | %-8s | %-16s | %-10s | %-9s | %-7s |%n ";

            System.out.format(" +----------+--------------------+----------+------------------+------------+-----------+---------+%n");
            System.out.format(" | Item ID  |      Category      |   Cost   |       Name       | Perishable | Remaining |  Price  |%n");
            System.out.format(" +----------+--------------------+----------+------------------|------------+-----------+---------+%n");
            for (Stock stock : (Iterable<Stock>) items) {
                System.out.format(leftAlignFormat, Integer.toString(stock.getId()), stock.getCategory(),
                        Float.toString(stock.getCost()), stock.getName(), "True",
                        Integer.toString(stock.getRemaining_stock()), Float.toString(stock.getSell_price()));
                System.out.format("+----------+--------------------+----------+------------------|------------+-----------+---------+%n");
            }

        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }





    }

    private static float addItemToTransaction(float cost, List items) throws IOException {
        boolean itemFound = false;
        CRUD crud = new CRUD();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter the next item of the transaction.");
        String transactionItemString = reader.readLine();
        for (Stock stock : (Iterable<Stock>) items) {
            if (transactionItemString.equals(stock.getName())) {
                itemFound = true;
                if (stock.getRemaining_stock() > 0) {
                    cost += stock.getSell_price();
                    crud.update(stock.getId(), "stock_remaining_stock", Integer.toString(stock.getRemaining_stock() - 1));

                } else {
                    System.out.println("ITEM " + stock.getName() + " OUT OF STOCK.");
                }

                if (!itemFound) {
                    System.out.println("ITEM " + transactionItemString + " NOT FOUND.");
                }
            }
        }
        return cost;
    }

    @Override
    public void addCustomerTransaction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean end = false;
        String checkForEnd;
        float cost = 0f; //Dummy value.
        Session s = HibernateUtil.getSessionFactory().openSession();

        s.beginTransaction();
        List items = s.createQuery("From Stock").list();
        while (!end){
            cost = addItemToTransaction(cost, items);
            System.out.println("Would you like to add another item to this transaction? (y/n)");
            checkForEnd = reader.readLine().toLowerCase();
            if (checkForEnd.equals("n")){
                end = true;
            }
        }

        System.out.println("Please enter the amount of money given:");
        String moneyGivenInput = reader.readLine() + "f";
        float moneyGiven = Float.parseFloat(moneyGivenInput);

        System.out.println("Please enter the amount of change given back:");
        String changeGivenInput = reader.readLine() + "f";
        float changeGiven = Float.parseFloat(changeGivenInput);

        Transaction newTransaction = new Transaction(cost, moneyGiven, changeGiven);
        newTransaction.addCustomerTransaction();
        newTransaction.generateReceipt(newTransaction.getId());

        s.getTransaction().commit();
        s.close();
    }
    @Override
    public void generateReceipt() {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the ID of the transaction : ");
        int num = scan.nextInt();
        scan.close();

        if (num > 0 ){
            Transaction receipt = new Transaction();
            receipt.generateReceipt(num);
        }
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
                break;
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
        perishableString = perishReader.readLine();
        while(!perishableString.equals("true") && !perishableString.equals("false")){
            System.out.println("Enter only true or false: ");
            BufferedReader perishStringReader = new BufferedReader(new InputStreamReader(System.in));
            perishableString = perishStringReader.readLine();
        }
        perishable = Boolean.parseBoolean(perishableString);
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
                ID = nameCrud.getSid(name);
                nameCrud.delete(ID);
                break;
            case "id":
                System.out.println("Enter ID of an item that you want to remove: ");
                BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
                ID = Integer.parseInt(idReader.readLine());
                CRUD idCrud = new CRUD();
                idCrud.delete(ID);
                break;
            default:
                System.out.println("You can only choose between name and id");
                removeItem();
        }
    }

    private void updateItem() throws IOException{
        int ID = 0;
        String column;
        String newValue;
        String option;
        String name;
        System.out.println("Do you want to update an item using name or id?: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        option = reader.readLine().toLowerCase();
        if(option.equals("name")){
            System.out.println("Item's name: ");
            BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
            name = nameReader.readLine();
            CRUD crud = new CRUD();
            ID = crud.getSid(name);
        } else if (option.equals("id")){
            System.out.println("Item's ID: ");
            BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
            ID = Integer.parseInt(idReader.readLine());
        } else {
            System.out.println("You can only choose between name and id");
            updateItem();
        }
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
