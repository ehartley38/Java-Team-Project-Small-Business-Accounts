package csc1035.project3;

public class Main {

    public static void main(String[] args) {

        CRUD crud = new CRUD();

        crud.create("CS:GO", "Video_Games", false, 99.99f, 10,
                12.99f);

        crud.create("The-Witcher-3", "Video_Games", false, 99.99f, 10,
                12.99f);

    }

}
