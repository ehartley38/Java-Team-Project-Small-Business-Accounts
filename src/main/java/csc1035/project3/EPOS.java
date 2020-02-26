package csc1035.project3;

interface EPOS {

    /**
     * Displays all stock to the user.
     */
    void countStock();

    /**
     * Allows for a customer transaction of stock.
     */
    void customerTransaction();

    /**
     * Produces a receipt of a transaction.
     */
    void generateReceipt();

    /**
     * Allows stock to be updated outside of a transaction.
     */
    void updateStock();

}
