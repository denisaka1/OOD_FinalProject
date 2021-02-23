package Model.command;

import Model.observer.Customer;
import Model.Product;
import Model.observer.Sender;

import java.util.ArrayList;

public class StoreCommand {

    private Store store;
    private Store.Memento previous;
    private ArrayList<String> names;

    public StoreCommand(String filename) {
        store = new Store(filename);
    }

    private boolean addProduct(Product product) {
        Customer customer;
        previous = store.createMemento();

        boolean renewProduct = store.addProduct(product);

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
        store.setStore(previous);
        store.writeAllFromMapToFile();
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