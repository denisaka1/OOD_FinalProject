package Model.command;

public class UndoCommand implements Command {
    private StoreCommand storeCommand;

    public UndoCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    @Override
    public void execute() {
        storeCommand.undo();
    }

}
