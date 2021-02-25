package Model.command;

public class IsProductEmpty implements Command {
    private StoreCommand storeCommand;
    private boolean isEmpty;

    public IsProductEmpty(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
        isEmpty = true;
    }

    @Override
    public void execute() {
        isEmpty = storeCommand.isProductEmpty();
    }

    public boolean get() {
        return isEmpty;
    }
}
