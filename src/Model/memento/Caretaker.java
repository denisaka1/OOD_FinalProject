package Model.memento;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> savedProducts = new ArrayList<>();

    public void addMemento(Memento m) {
        savedProducts.add(m);
    }

    public Memento getMemento(int index) {
        return savedProducts.get(index);
    }
}
