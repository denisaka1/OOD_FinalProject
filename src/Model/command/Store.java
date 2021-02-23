package Model.command;

import Model.Product;
import Model.comparators.StringAscComparator;
import Model.iterator.FileHandler;
import Model.observer.Customer;
import Model.observer.Sender;

import java.util.*;

public class Store {

    // todo: enum?
    public final static int ASC = 0;
    public final static int DESC = 1;
    public final static int DEFAULT = 2;

    private Map<String, Product> allProducts;
    private FileHandler binaryFileManager;
    private int saveAndPrintAs;
    private Sender sender = Sender.getInstance();

//    private final String fileName = "data/products.txt";

/*    public Store() {
//        allProducts = new HashMap<>();
//        binaryFileManager = new FileHandler(fileName);
        saveAndPrintAs = DEFAULT;
    }*/

    public Store(String filename) {
//        this();
        saveAndPrintAs = DEFAULT;
        binaryFileManager = new FileHandler(filename);
        loadFromFile();
        allProducts = createMap();
    }

    public void setOrderBy(int order) {
        saveAndPrintAs = order;
    }

    private void loadFromFile() {
        allProducts = binaryFileManager.readProducts();
        Customer tmp;
        for (Product p : allProducts.values()) {
            tmp = p.getCustomer();
            if (tmp != null) {
                if (tmp.getEventOnSales())
                    sender.attach(tmp);
            }
        }
    }

    protected boolean addProduct(Product product){
        // todo: add boolean for msg
        String serialNumber = product.getSerialNumber();
        boolean isReplaced = false;

        // True - Renew\Replace
        if(allProducts.containsKey(serialNumber)){
            isReplaced = true;
            allProducts.replace(serialNumber, product);
            binaryFileManager.replaceProductBySerialNumber(serialNumber, product);

        }else{
            allProducts.put(product.getSerialNumber(), product);
            binaryFileManager.writeProduct(product);
        }

        return isReplaced;
    }

    protected Map<String, Product> getCurrentMap() {
        return allProducts;
    }

    protected Product getProduct(String serialNumber) {
        return allProducts.get(serialNumber);
    }

    protected void setSaveAndPrintAs(int order) {
        if(saveAndPrintAs == DEFAULT) {
            saveAndPrintAs = order;

            allProducts = createMap();
        }
    }

    private Comparator<String> getComparatorByOrder(int order) {

        StringAscComparator comparator = new StringAscComparator();

        if (order == ASC)
            return comparator;

        return comparator.reversed();
    }

    private Map<String, Product> createMap() {
        // also used to clone the current Map
        Map<String, Product> currentState;

        switch(saveAndPrintAs){
            case ASC:
            case DESC:
                currentState = new TreeMap<>(getComparatorByOrder(saveAndPrintAs));
                currentState.putAll(allProducts);
                break;
            default:
                currentState = new LinkedHashMap<>(allProducts);
                break;
        }

        return currentState;
    }

    protected void setStore(Memento m) {
        allProducts = new LinkedHashMap<>(m.getProducts());
    }

    protected Memento createMemento() {
        return new Memento(createMap());
    }

    protected void writeAllFromMapToFile() {
        binaryFileManager.clearFile();
        for(Product product : allProducts.values()){
            binaryFileManager.writeProduct(product);
        }
    }

    public ArrayList<Product> getProductList() {
        ArrayList<Product> list = new ArrayList<>();

        for (Product p : allProducts.values())
            list.add(p);

        return list;
    }

    public boolean removeProductFromFile(String serialNumber){
        return binaryFileManager.removeProduct(serialNumber);
    }

    public boolean removeProduct(String serialNumber){
        Boolean succ = false;
        if (allProducts.containsKey(serialNumber)) {
//            succ = true;
            allProducts.remove(serialNumber);
            succ = removeProductFromFile(serialNumber);
        }
        return succ;
    }

    public Sender getSender() {
        return sender;
    }

    protected static class Memento {
        private final Map<String, Product> products;

        private Memento(Map<String, Product> products){
            this.products = products;
        }

        private Map<String, Product> getProducts() {
            return products;
        }
    }
}