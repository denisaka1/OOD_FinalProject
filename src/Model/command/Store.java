package Model.command;

import Model.Product;
import Model.comparators.StringAscComparator;
import Model.iterator.FileHandler;
import Model.memento.Memento;
import Model.observer.Customer;
import Model.observer.Sender;
import java.util.*;

public class Store implements StoreCommand {
    public static final String FILENAME = "data/products.txt";

    public final static int ASC = 0;
    public final static int DESC = 1;
    public final static int DEFAULT = 2;

    private Map<String, Product> allProducts;
    private FileHandler binaryFileManager;
    private int saveAndPrintAs;
    private Memento previous;
    private ArrayList<String> names;

    private Sender sender = Sender.getInstance();

    public Store() {
        saveAndPrintAs = DEFAULT;
        binaryFileManager = new FileHandler(FILENAME);
        names = new ArrayList<>();
        loadFromFile();
        allProducts = createMap();
    }

    @Override
    public boolean isProductEmpty() {
        return allProducts.isEmpty();
    }

    @Override
    public boolean addProduct(Product newProduct) {
        previous = createMemento();

        String serialNumber = newProduct.getSerialNumber();
        boolean isReplaced = false;

        // True - Renew\Replace
        if (allProducts.containsKey(serialNumber)) {
            isReplaced = true;
            allProducts.replace(serialNumber, newProduct);
            binaryFileManager.replaceProductBySerialNumber(serialNumber, newProduct);
        } else {
            Customer customer = newProduct.getCustomer();
            String name;

            if (customer != null) {
                name = customer.getName();
                if (customer.getEventOnSales()){
                    sender.attach(customer);
                    if(!names.contains(name))
                        names.add(name);
                }
            }

            allProducts.put(newProduct.getSerialNumber(), newProduct);
            writeProducts();
        }

        return isReplaced;
    }

    @Override
    public boolean removeProduct(String sku) {
        boolean success = false;
        if (allProducts.containsKey(sku)) {
            String currName = allProducts.get(sku).getCustomer().getName();
            names.remove(currName);
            allProducts.remove(sku);
            success = removeProductFromFile(sku);
        }
        return success;
    }

    private boolean removeProductFromFile(String sku) {
        return binaryFileManager.removeProduct(sku);
    }

    @Override
    public Product getProduct(String sku) {
        return allProducts.get(sku);
    }

    @Override
    public void setOrderBy(int orderBy) {
        if (saveAndPrintAs == DEFAULT) {
            saveAndPrintAs = orderBy;

            allProducts = createMap();
        }
    }

    @Override
    public ArrayList<String> getAllConfirmedCustomerNames() {
        return names;
    }

    @Override
    public void undo() {
        setStore(previous);
        writeAllFromMapToFile();
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<>(allProducts.values());
    }

    @Override
    public void sendSaleMessage(String msg) {
        sender.setMessage(msg);
        sender.SendAll();
    }

    private Map<String, Product> createMap() {
        // also used to clone the current Map
        Map<String, Product> currentState;

        switch (saveAndPrintAs) {
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

    private void writeProducts() {
        binaryFileManager.clearFile();
        for (Product product : allProducts.values())
            binaryFileManager.writeProduct(product);
    }

    private Comparator<String> getComparatorByOrder(int order) {
        StringAscComparator comparator = new StringAscComparator();
        if (order == ASC)
            return comparator;

        return comparator.reversed();
    }

    private void writeAllFromMapToFile() {
        binaryFileManager.clearFile();
        for (Product product : allProducts.values()) {
            binaryFileManager.writeProduct(product);
        }
    }

//    private Map<String, Product> getCurrentMap() {
//        return allProducts;
//    }

    private Memento createMemento() {
        return new Memento(createMap());
    }

    private void setStore(Memento m) {
        allProducts = new LinkedHashMap<>(m.getProducts());
    }

    private void loadFromFile() {
        allProducts = binaryFileManager.readProducts();
        Customer tmp;
        for (Product p : allProducts.values()) {
            tmp = p.getCustomer();
            if (tmp != null) {
                if (tmp.getEventOnSales()) {
                    sender.attach(tmp);
                    names.add(tmp.getName());
                }
            }
        }
    }

/*    private void loadAllPromotionNames() {
        names = new ArrayList<>();
        String name;

        Customer customer;
        for (Product product : getAllProducts()) {
            customer = product.getCustomer();
            name = customer.getName();
            if (customer.getEventOnSales()) {
                if (!names.contains(name))
                    names.add(customer.getName());
            }
        }
    }*/
}
