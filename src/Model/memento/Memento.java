package Model.memento;

import Model.Product;

public class Memento {
    private Product product;

    Memento(Product product) {
        this.product = product;
    }

    public Product getSavedProduct() {
        return product;
    }
}