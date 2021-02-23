package Model.observer;

import Model.observer.Receiver;

import java.io.Serializable;

//public class Customer extends Observer implements Serializable{
public class Customer implements Serializable, Observer{

    private static final long serialVersionUID = 7526472295622776147L;
    private String name;
    private String phoneNumber;
    private boolean promotionsNotification;

    public Customer(String name, String phoneNumber, boolean promotionsNotification) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.promotionsNotification = promotionsNotification;
    }

    @Override
    public String send() {
        if(promotionsNotification)
            return name;
        return null;
    }

    public boolean getEventOnSales() {
        return promotionsNotification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", promotionsNotification=" + promotionsNotification +
                '}';
    }
}