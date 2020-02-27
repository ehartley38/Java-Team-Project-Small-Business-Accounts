package csc1035.project3;

import csc1035.project3.CRUD.Create;

public class Main {

    public static void main(String[] args) {

        Create create = new Create();

        create.add("CS:GO", "Video_Games", false, 99.99f, 10,
                12.99f);

        create.add("The-Witcher-3", "Video_Games", false, 99.99f, 10,
                12.99f);

    }

}
