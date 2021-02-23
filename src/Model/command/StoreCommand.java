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
        loadAllPromotionNames();
    }

    private void loadAllPromotionNames() {
        names = new ArrayList<>();
        String name;

        if(!store.isEmpty()) {
            Customer customer;
            for(Product product: store.getProductList()){
                customer = product.getCustomer();
                name = customer.getName();
                if(customer.getEventOnSales()) {
                    if(!names.contains(name))
                        names.add(customer.getName());
                }
            }
        }
    }

    private boolean addProduct(Product product) {
        Customer customer;
        previous = store.createMemento();

        boolean renewProduct = store.addProduct(product);

        customer = product.getCustomer();
        String name;

        if (customer != null) {
            name = customer.getName();
            if (customer.getEventOnSales()){
                store.getSender().attach(customer);
                if(!names.contains(name))
                    names.add(name);
            }
        }

        return renewProduct;
    }

    public boolean removeProduct(String serialNumber) {
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
//        names = store.getSender().SendAll();
        store.getSender().SendAll();
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

    public boolean removeProductFromStore(String serialNumber) {
        String customerName = store.getProduct(serialNumber).getCustomer().getName();
        names.remove(customerName);

        return removeProduct(serialNumber);
    }

    public void sendSaleMessageToAllCustomers(String msg) {
        sendSaleMessage(msg);
    }

    public ArrayList<String> getAllCustomerSalesNames() {
        return names;
    }

    public void setOrderToStore(int order) { store.setOrderBy(order); }
}