package Model.command;

<<<<<<< HEAD
=======
//import Model.memento.Caretaker;
//import Model.memento.Originator;
>>>>>>> 100e603d03f962cb16fdccb0c8966dc61c3a1bf3
import Model.observer.Customer;
import Model.Product;
import Model.observer.Sender;

import java.util.ArrayList;

public class StoreCommand {

    private Store store;
    private Store.Memento previous;
<<<<<<< HEAD
    private ArrayList<String> names;

    public StoreCommand(String filename) {
        store = new Store(filename);
=======
//    private int currProduct;
//    private Caretaker caretaker;
//    private Originator originator;
    private Sender sender = Sender.getInstance();
    private ArrayList<String> names;

    public StoreCommand(String filename) {

        store = new Store(filename);

//        caretaker = new Caretaker();
//        originator = new Originator();
//        currProduct = 0;
>>>>>>> 100e603d03f962cb16fdccb0c8966dc61c3a1bf3
    }

    private boolean addProduct(Product product) {
        Customer customer;
        previous = store.createMemento();

<<<<<<< HEAD
        boolean renewProduct = store.addProduct(product);
=======
//        originator.set(product); // Set the value for the current Memento
//        caretaker.addMemento(originator.storeInMemento()); // Add new product to the ArrayList
//        currProduct++;

        store.addProduct(product);
>>>>>>> 100e603d03f962cb16fdccb0c8966dc61c3a1bf3

        customer = product.getCustomer();

        if (customer != null) {
            if (customer.getEventOnSales())
                store.getSender().attach(customer);
        }

        return renewProduct;
    }

    public Boolean removeProduct(String serialNumber) {

        return store.removeProduct(serialNumber);
    }

    private Product getProduct(String serialNumber) {
        return store.getProduct(serialNumber);
    }

    private ArrayList<Product> getAllProducts() {
        return store.getProductList();
    }

    /*
        ASC - 0
        DESC - 1
        ByInsert(DEFAULT) - 2
    */
    private void saveProductByOrder(int order){
        store.setSaveAndPrintAs(order);
    }

    private void undo() {
<<<<<<< HEAD
        store.setStore(previous);
        store.writeAllFromMapToFile();
=======
/*        if (currProduct >= 1) {
            currProduct--;

            Product lastAddedProduct = originator.restoreFromMemento(caretaker.getMemento(currProduct));
            store.removeProductFromFile(lastAddedProduct.getSerialNumber());
        }*/

        store.setStore(previous);
        store.writeAllFromMapToFile();

/*        if(previous != null) {
            store.setStore(previous);
//            previous = null;
            store.writeAllFromMapToFile();
        }*/
>>>>>>> 100e603d03f962cb16fdccb0c8966dc61c3a1bf3
    }

    private void sendSaleMessage(String msg) {
        store.getSender().setMessage(msg);
        names = store.getSender().SendAll();
    }

    public boolean addProductToStore(Product product){
        return addProduct(product);
    }

    public Product getProductFromStore(String serialNumber){
        return getProduct(serialNumber);
    }

    public ArrayList<Product> getAllProductsFromStore(){
        return getAllProducts();
    }

    public void saveProductByOrderInStore(int order) {
        saveProductByOrder(order);
    }

    public void undoStore() {
        undo();
    }

    public boolean removeProductFromStore(String serialNumber) { return removeProduct(serialNumber); }

    public void sendSaleMessageToAllCustomers(String msg) {
        sendSaleMessage(msg);
    }

    public ArrayList<String> getAllCustomerSalesNames() {
        return names;
    }

    public void setOrderToStore(int order) { store.setOrderBy(order); }
}