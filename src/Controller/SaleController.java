package Controller;

import Model.command.StoreCommand;
import View.HomeScreen;
import View.Sale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class SaleController extends SecondaryWindowController {
    private Sale saleView;

    public SaleController(HomeScreen homeScreenView, StoreCommand storeCommand, Sale saleView) {
        super(homeScreenView, storeCommand, saleView);
        this.saleView = saleView;
        eventSendButton();
    }

    private void eventSendButton() {
        EventHandler<ActionEvent> eventForSendButton = event -> {

            storeCommand.sendSaleMessageToAllCustomers(saleView.getMsg());
            ArrayList<String> names = storeCommand.getAllCustomerSalesNames();

            new Thread(() ->{
                for(String name: names){
                    Platform.runLater(()->{
                        saleView.addResponse(name);
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        };
        saleView.addEventForSendButton(eventForSendButton);
    }
}

