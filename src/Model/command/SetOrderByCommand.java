package Model.command;

public class SetOrderByCommand implements Command {
    private StoreCommand storeCommand;
    private int order;

    public SetOrderByCommand(StoreCommand storeCommand, int order) {
        this.storeCommand = storeCommand;
        this.order = order;
    }

    @Override
    public void execute() {
        storeCommand.setOrderBy(order);
    }
}
