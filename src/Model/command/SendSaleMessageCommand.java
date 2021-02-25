package Model.command;

public class SendSaleMessageCommand implements Command {
    private StoreCommand storeCommand;
    private String msg;

    public SendSaleMessageCommand(StoreCommand storeCommand, String msg) {
        this.storeCommand = storeCommand;
        this.msg = msg;
    }

    @Override
    public void execute() {
        storeCommand.sendSaleMessage(msg);
    }
}
