package Model.observer;

public abstract class Observer {
    protected Sender sender;
    public abstract String send();
}
