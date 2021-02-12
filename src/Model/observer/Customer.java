package Model.observer;

import Model.observer.Receiver;

import java.io.Serializable;

public class Customer extends Observer implements Serializable{

    private String name;
    private String phoneNumber;
    private boolean giveEventOnSales; // TODO: fix \ rename

    @Override
    public String send() {
        if(giveEventOnSales)
            return name;
        return null;
    }

    public boolean getEventOnSales() {
        return giveEventOnSales;
    }
}
