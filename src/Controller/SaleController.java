package Controller;

import Exceptions.IllegalInputException;
import Model.command.StoreCommand;
import View.HomeScreen;
import View.Sale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;

public class SaleController extends SecondaryWindowController {
    private Sale saleView;
    private Thread loadingThread;
    private boolean shuttingDown;
    private int status = 0, nameCounter = 0;

    public SaleController(HomeScreen homeScreenView, StoreCommand storeCommand, Sale saleView) {
        super(homeScreenView, storeCommand, saleView);
        this.saleView = saleView;
        eventSendButton();
    }

    private void eventSendButton() {
        EventHandler<ActionEvent> eventForSendButton = event -> {

            try {
                String msg = saleView.getMsg();
                if (msg.isEmpty())
                    throw new IllegalInputException("Please fill in the message box");

                storeCommand.sendSaleMessageToAllCustomers(saleView.getMsg());
                ArrayList<String> names = storeCommand.getAllCustomerSalesNames();

                saleView.addResponse("Sending Messages:", -1);

                if (names.size() > 0)
                    loadingBar();

                new Thread(() -> {
                    for (String name : names) {
                        Platform.runLater(() -> {
                            saleView.addResponse(name + " Accepted", status);

                            nameCounter++;
                            if (nameCounter >= names.size()) {
                                shuttingDown = true;
                                loadingThread.interrupt();
                                saleView.loadingBar(-1);
                                Thread.currentThread().interrupt();
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
//                        e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }).start();
            } catch (IllegalInputException i) {
                i.showErrorMessage();
            } catch (Exception e) {

            }
        };
        saleView.addEventForSendButton(eventForSendButton);
    }

    private void loadingBar() {
        loadingThread = new Thread(() -> {
            while (!shuttingDown && !Thread.interrupted()) {
                Platform.runLater(() -> {
                    if (status == 3)
                        status = 0;
                    status++;

                    saleView.loadingBar(status);
                });

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        loadingThread.start();
    }
}

