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
        EventHandler<ActionEvent> eventForSendButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {

                storeCommand.sendSaleMessageToAllCustomers(saleView.getMsg());
                ArrayList<String> names = storeCommand.getAllCustomerSalesNames();

                Thread thread = new Thread(new Runnable() {
                    int index = 0;

                    @Override
                    public void run() {
                        Runnable updater = new Runnable() {
                            @Override
                            public void run() {
                                saleView.addResponse(names.get(index));
                                index++;
                            }
                        };

                        while (index < names.size() - 1) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {

                            } catch (Exception e) {
                                System.out.println(e);
                            }

                            Platform.runLater(updater);
                        }
                    }
                });
                thread.setDaemon(true); // a low priority thread
                thread.start();
            }
        };
        saleView.addEventForSendButton(eventForSendButton);
    }
}

