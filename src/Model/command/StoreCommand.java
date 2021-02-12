package Model.command;

import Model.Product;

import java.util.Stack;

public class StoreCommand {

    private Store store = new Store();
    private Store.Memento previous;

    private void addProduct(Product product) {
        previous = store.createMemento();
        store.addProduct(product);

        //todo: add to binary file
    }

    private Product getProduct(String serialNumber) {
        return store.getProduct(serialNumber);
    }

    /*
        ASC - 0
        DESC - 1
        ByInsert - 2
    */
    private void saveProductByOrder(int order){
        store.setSaveAndPrintAs(order);
    }

    private void showProduct(String serialNumber) {
        // show product by its serialNumber
        // todo

    }

    private void undo() {
        if(previous != null) {
            store.setStore(previous);
            previous = null;
            // todo: remove last item from binary file

        }

    }

    public void addProductToStore(Product product){
        addProduct(product);
    }

    public Product getProductFromStore(String serialNumber){
        return getProduct(serialNumber);
    }

    public void saveProductByOrderInStore(int order) {
        saveProductByOrder(order);
    }

    public void showProductInStore(String serialNumber) {
        showProduct(serialNumber);
    }

    public void undoStore() {
        undo();
    }

}
