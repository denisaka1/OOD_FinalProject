package Model.memento;

import Model.Product;

public class Originator {
    private Product product;

    public void set(Product newProduct) {
        this.product = newProduct;
    }

    public Memento storeInMemento() {
        return new Memento(product);
    }

    public Product restoreFromMemento(Memento memento) {
        product = memento.getSavedProduct();

        return product;
    }
}
