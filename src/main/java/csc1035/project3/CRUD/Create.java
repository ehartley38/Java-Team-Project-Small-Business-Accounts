package csc1035.project3.CRUD;


import csc1035.project3.Stock;

public class Create {

    Stock fortnite;

    public Create() {
        fortnite = new Stock(1, "fortnite", "videogame", false, 99.99f, 10,
                12.99f);
    }

    public Stock getFortnite() {
        return fortnite;
    }



}
