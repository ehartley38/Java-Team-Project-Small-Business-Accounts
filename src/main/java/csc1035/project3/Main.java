package csc1035.project3;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean exit = false;
        String option;
        BufferedReader exitReader = new BufferedReader(new InputStreamReader(System.in));
        while(!exit){
            new UserMenu().menu();
            System.out.println("Do you wish to continue? (y/n): ");
            option = exitReader.readLine().toLowerCase();
            if(option.equals("n")){
                exit = true;
            }
        }
    }
}
