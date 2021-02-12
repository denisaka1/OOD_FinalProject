package Model.command;

import Model.Product;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Store implements Iterable<Product>{

    public final static int ASC = 0;
    public final static int DESC = 1;
    public final static int BY_INSERT = 2;

    private final static int DEFAULT = -1;

    private Map<String, Product> allProducts;
    private int saveAndPrintAs;
    private String fileName = "products.txt";

    public Store() {
        allProducts = new HashMap<String, Product>();
        saveAndPrintAs = DEFAULT;
    }

    protected void addProduct(Product product){
        String serialNumber = product.getSerialNumber();

        if(allProducts.containsKey(serialNumber)){
            allProducts.replace(serialNumber, product);
        }else
            allProducts.put(product.getSerialNumber(), product);
    }

    protected Product getProduct(String serialNumber) {
        return allProducts.get(serialNumber);
    }

    protected void setSaveAndPrintAs(int order) {
        if(saveAndPrintAs == DEFAULT)
            saveAndPrintAs = order;
    }

    protected void setStore(Memento m) {
        allProducts = new HashMap<>(m.getProducts());
    }

    protected Memento createMemento() {
        return new Memento(allProducts);
    }

    protected static class Memento {
        private Map<String, Product> products;

        private Memento(Map<String, Product> products){
            this.products = products;
        }

        private Map<String, Product> getProducts() {
            return products;
        }
    }

    public void removeProductFromFile(String productName) {

    }




    @Override
    public Iterator<Product> iterator() {
        return new BinaryFileIterator();
    }

    // TODO
    private class BinaryFileIterator implements Iterator<Product> {

        private int cur = 0; // next will return the element at this index
        private int last = -1; // element to be removed


        public BinaryFileIterator() {

        }

        @Override
        public boolean hasNext() {
            if(cur < last) {

            }
            return cur < last;
        }

        @Override
        public Product next() {
            if(!hasNext())
                return null;
            return null;
        }

        @Override
        public void remove() {

        }

        public String getCurrentString() {
            return null;
        }
    }
}
