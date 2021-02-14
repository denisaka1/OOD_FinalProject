package Model.observer;

import Model.observer.Receiver;

import java.io.Serializable;

public class Customer extends Observer implements Serializable{

    private static final long serialVersionUID = 7526472295622776147L;
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
