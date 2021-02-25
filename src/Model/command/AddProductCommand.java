package Model.command;

import Model.Product;

public class AddProductCommand implements Command {
    private StoreCommand storeCommand;
    private Product product;
    private boolean renew;

    public AddProductCommand(StoreCommand storeCommand, Product product) {
        this.storeCommand = storeCommand;
        this.product = product;
        renew = false;
    }

    @Override
    public void execute() {
        storeCommand.addProduct(product);
    }

    public boolean isRenew() {
        return renew;
    }
}
