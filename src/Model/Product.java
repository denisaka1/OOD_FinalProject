package Model;

import Exceptions.IllegalInputException;
import Model.observer.Customer;

import java.io.Serializable;

public class Product implements Serializable {

    // 0 - default

    private String serialNumber; // makat
    private String productName;
    private int retailPrice; // for customer
    private int wholesalePrice; // for store

    private Customer customer;

    public Product (String serialNumber) {
        this(serialNumber, null, 0, 0, null);
    }

    private Product(String serialNumber, String name,
                   int retailPrice, int wholesalePrice,
                   Customer customer) {
        this.serialNumber = serialNumber;
        setSerialNumber(serialNumber);
        setRetailPrice(retailPrice);
        setWholesalePrice(wholesalePrice);
        this.productName = name;
        this.customer = customer;
    }

    /************ Get Functions ***********/
    public String getSerialNumber() {
        return serialNumber;
    }

    protected String getProductName() {
        return productName;
    }

    protected int getRetailPrice() {
        return retailPrice;
    }

    protected int getWholesalePrice() {
        return wholesalePrice;
    }

    public Customer getCustomer() {
        return customer;
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

    private void setRetailPrice(int retailPrice) {
        this.retailPrice = Math.max(retailPrice, 0);
    }

    private void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = Math.max(wholesalePrice, 0);
    }
}
