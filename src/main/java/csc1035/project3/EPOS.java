package csc1035.project3;

import java.io.IOException;

interface EPOS {

    /**
     * Displays all stock to the user.
     */
    void countStock() throws IOException;

    /**
     * Allows for a customer transaction of stock.
     */
    void addCustomerTransaction() throws IOException;

    /**
     * Produces a receipt of a transaction.
     */
    void generateReceipt() throws IOException;

    /**
     * Allows stock to be updated outside of a transaction.
     */
    void updateStock() throws IOException;

}
