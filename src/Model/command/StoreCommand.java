package Model.command;

import Model.memento.Caretaker;
import Model.memento.Originator;
import Model.observer.Customer;
import Model.Product;
import Model.observer.Sender;

import java.util.ArrayList;

public class StoreCommand {

    private Store store = new Store();
//    private Store.Memento previous;
    private int currProduct;
    private Caretaker caretaker;
    private Originator originator;
    private Sender sender = Sender.getInstance();
    private ArrayList<String> names;

    public StoreCommand() {
        caretaker = new Caretaker();
        originator = new Originator();
        currProduct = 0;
    }

    private void addProduct(Product product) {
        Customer customer;
//        previous = store.createMemento();

        originator.set(product); // Set the value for the current Memento
        caretaker.addMemento(originator.storeInMemento()); // Add new product to the ArrayList
        currProduct++;

        store.addProduct(product);

        customer = product.getCustomer();

        if(customer != null) {
            if(customer.getEventOnSales())
                sender.attach(customer);
        }
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
        ByInsert - 2
    */
    private void saveProductByOrder(int order){
        store.setSaveAndPrintAs(order);
    }

    private void undo() {
        if (currProduct >= 1) {
            currProduct--;

            Product lastAddedProduct = originator.restoreFromMemento(caretaker.getMemento(currProduct));
            store.removeProductFromFile(lastAddedProduct.getSerialNumber());
        }

//        if(previous != null) {
//            store.setStore(previous);
////            previous = null;
//            store.writeAllFromMapToFile();
//        }
    }

    private void sendSaleMessage(String msg) {
        sender.setMessage(msg);
        names = sender.SendAll();
    }


    public void addProductToStore(Product product){
        addProduct(product);
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

    public void sendSaleMessageToAllCustomers(String msg) {
        sendSaleMessage(msg);
    }

    public ArrayList<String> getAllCustomerSalesNames() {
        return names;
    }
}