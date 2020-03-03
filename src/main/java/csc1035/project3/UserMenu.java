package csc1035.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        switch(option){
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
                System.out.println("Error - Please only enter 'a', 'b', 'c' or 'd'.");
                menu();
        }
    }

    @Override
    public void countStock() {

    }

    @Override
    public void addCustomerTransaction() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter the cost of the product:");
        String costInput = reader.readLine() + "f";
        float cost = Float.parseFloat(costInput);

        System.out.println("Please enter the amount of money given:");
        String moneyGivenInput = reader.readLine() + "f";
        float moneyGiven = Float.parseFloat(moneyGivenInput);

        System.out.println("Please enter the amount of change given back:");
        String changeGivenInput = reader.readLine() + "f";
        float changeGiven = Float.parseFloat(changeGivenInput);

        Transaction newTransaction = new Transaction(cost, moneyGiven, changeGiven);
        newTransaction.addCustomerTransaction();
        newTransaction.generateReceipt();
    }

    @Override
    public void generateReceipt() {

    }

    @Override
    public void updateStock() {

    }
}
