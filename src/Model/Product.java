package Model;

import Exceptions.IllegalInputException;
import Model.observer.Customer;

import java.io.*;

public class Product implements Serializable {

    // 0 - default
    private static final long serialVersionUID = 7526472295622776141L;
    private String serialNumber; // SKU
    private String productName;
    private double retailPrice; // for customer
    private double wholesalePrice; // for store

    private Customer customer;

    public Product(String serialNumber, String name, double retailPrice, double wholesalePrice, Customer customer) {
        this.serialNumber = serialNumber;
        setSerialNumber(serialNumber);
        setRetailPrice(retailPrice);
        setWholesalePrice(wholesalePrice);
        this.productName = name;
        this.customer = customer;
    }

    public Product(String serialNumber) {
        this(serialNumber, null, 0, 0, null);
    }

    /************ Get Functions ***********/
    public String getSerialNumber() {
        return serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public double getWholesalePrice() {
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
    private void setSerialNumber(String serialNumber){
        if(serialNumber.length() >= 1)
            this.serialNumber = serialNumber;
        else{
            new IllegalInputException("Serial number length can't be less than 1!")
                    .showErrorMessage();
        }
    }

    private void setRetailPrice(double retailPrice) {
        this.retailPrice = Math.max(retailPrice, 0);
    }

    private void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = Math.max(wholesalePrice, 0);
    }

    @Override
    public String toString() {
        return "Product" +
                "\nSKU: " + serialNumber +
                "\nProduct Name: " + productName +
                "\nRetail Price: " + retailPrice +
                "\nWholesale Price: " + wholesalePrice +
                "\n\n" + customer;
    }
}