package Model.observer;

import java.util.ArrayList;
import java.util.List;

public class Sender {

    private static Sender sender = null;
    private List<Observer> observers = new ArrayList<Observer>();
    private ArrayList<String> names;

    private String msg;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public ArrayList<String> SendAll() {
        String name;
        names = new ArrayList<>();
        for(Observer observer : observers) {
            name = observer.send();
            if(name != null)
                names.add(name);
        }
        return names;
    }

    public static Sender getInstance() {
        if(sender == null)
            sender = new Sender();
        return sender;
    }

}