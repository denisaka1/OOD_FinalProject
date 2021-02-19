package Model.command;

import Model.Product;
import Model.iterator.FileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Store {

    public final static int ASC = 0;
    public final static int DESC = 1;
    public final static int BY_INSERT = 2;

    private final static int DEFAULT = -1;

    private Map<String, Product> allProducts;
    private FileHandler binaryFileManager;
    private int saveAndPrintAs;
    private final String fileName = "data/products.txt";

    public Store() {
        allProducts = new HashMap<>();
        binaryFileManager = new FileHandler(fileName);
        saveAndPrintAs = DEFAULT;
    }

    protected void addProduct(Product product){
        String serialNumber = product.getSerialNumber();

        if(allProducts.containsKey(serialNumber)){ // todo: replace or error
            allProducts.replace(serialNumber, product);
            binaryFileManager.replaceProductBySerialNumber(serialNumber, product);
        }else{
            allProducts.put(product.getSerialNumber(), product);
            binaryFileManager.writeProduct(product);
        }
    }

    protected Product getProduct(String serialNumber) {
        return allProducts.get(serialNumber);
    }

    protected void setSaveAndPrintAs(int order) {
        if(saveAndPrintAs == DEFAULT)
            saveAndPrintAs = order;
    }

//    protected void setStore(Memento m) {
//        allProducts = new HashMap<>(m.getProducts());
//    }

//    protected Memento createMemento() {
//        return new Memento(allProducts);
//    }

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

/*    protected static class Memento {
        private Map<String, Product> products;

        private Memento(Map<String, Product> products){
            this.products = products;
        }

        private Map<String, Product> getProducts() {
            return products;
        }
    }*/


/*    @Override
    public Iterator<Product> iterator() {
        return new BinaryFileIterator(fileName);
    }
    // TODO
    private class BinaryFileIterator implements Iterator<Product>{
        private ObjectInputStream read;
        private ObjectOutputStream write;
        public BinaryFileIterator(String fileName) {
            try {
                read = new ObjectInputStream(new FileInputStream(fileName));
                write = new ObjectOutputStream(new FileOutputStream(fileName));
            } catch (IOException e) {
                // todo: info
                e.printStackTrace();
            }
        }
        @Override
        public boolean hasNext() {
            try {
                if(read.available() != 0)
                    return true;
            } catch (IOException e) {
                // handle
                e.printStackTrace();
            }
            return false;
        }
        @Override
        public Product next() {
            return null;
        }
    }*/
}