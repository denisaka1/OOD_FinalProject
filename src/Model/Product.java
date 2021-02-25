package Model;

import Exceptions.IllegalInputException;
import Model.observer.Customer;

import java.io.*;

public class Product implements Serializable {

    // 0 - default
    private static final long serialVersionUID = 7526472295622776141L;
    private String sku;
    private String productName;
    private int retailPrice; // for customer
    private int wholesalePrice; // for store

    private Customer customer;

    public Product(String sku, String name, int retailPrice, int wholesalePrice, Customer customer) {
//        this.sku = sku;
        setSku(sku);
        setRetailPrice(retailPrice);
        setWholesalePrice(wholesalePrice);
        this.productName = name;
        this.customer = customer;
    }

    public Product(String sku) {
        this(sku, null, 0, 0, null);
    }

    /************ Get Functions ***********/
    public String getSku() {
        return sku;
    }

    public String getProductName() {
        return productName;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public int getWholesalePrice() {
        return wholesalePrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public static Product deserialize(byte[] byteArray)
        throws IOException,
            ClassNotFoundException {
            /*
            read size of the Product Object
            read the Product Object
             */
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));
        Product temp = (Product)ois.readObject();
        ois.close();

        return temp;
    }

    public static byte[] serialize(Product product)
            throws IOException {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos)
        ){

            oos.writeObject(product);
            oos.flush();

            return baos.toByteArray();
        }
    }

    /************ Set Functions ***********/
    private void setSku(String sku){
        if(sku.length() >= 1)
            this.sku = sku;
        else{
            new IllegalInputException("SKU length can't be less than 1!")
                    .showErrorMessage();
        }
    }

    private void setRetailPrice(int retailPrice) {
        this.retailPrice = Math.max(retailPrice, 0);
    }

    private void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = Math.max(wholesalePrice, 0);
    }

    @Override
    public String toString() {
        return "Product" +
                "\nSKU: " + sku +
                "\nProduct Name: " + productName +
                "\nRetail Price: " + retailPrice +
                "\nWholesale Price: " + wholesalePrice +
                "\n\n" + customer;
    }
}