package Model.command;

import Model.observer.Customer;
import Model.Product;
import Model.observer.Sender;

import java.util.ArrayList;

public class StoreCommand {

    private Store store = new Store();
    private Store.Memento previous;
    private Sender sender = Sender.getInstance();
    private ArrayList<String> names;

    private void addProduct(Product product) {
        Customer customer;
        previous = store.createMemento();
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

    /*
        ASC - 0
        DESC - 1
        ByInsert - 2
    */
    private void saveProductByOrder(int order){
        store.setSaveAndPrintAs(order);
    }

    private void showProduct(String serialNumber) {
        // show product by its serialNumber
        // todo

    }

    private void undo() {
        if(previous != null) {
            store.setStore(previous);
            previous = null;
            store.writeAllFromMapToFile();
        }

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

    public void saveProductByOrderInStore(int order) {
        saveProductByOrder(order);
    }

    public void showProductInStore(String serialNumber) {
        showProduct(serialNumber);
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
