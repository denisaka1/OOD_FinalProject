package Model.command;

public class RemoveProductCommand implements Command {
    private StoreCommand storeCommand;
    private String sku;
    private boolean isRemoved;

    public RemoveProductCommand(StoreCommand newStoreCommand, String sku) {
        this.storeCommand = newStoreCommand;
        this.sku = sku;
        isRemoved = false;
    }

    @Override
    public void execute() {
        isRemoved = storeCommand.removeProduct(sku);
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}