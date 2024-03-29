package Controller;

import Exceptions.AlertUserException;
import Model.command.GetAllConfirmedCustomerNamesCommand;
import Model.command.SendSaleMessageCommand;
import Model.command.StoreCommand;
import View.HomeScreen;
import View.Sale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class SaleController extends BackButtonController {
    private static final String FILL_MSG_BOX = "Please fill in the message box";
    private static final String SEND_RESPONSE = "Sending Messages:";
    private static final String ACCEPT_RESPONSE = " Accepted";

    private static Runnable checkEnableSendButton;
    private boolean sending;
    private final Sale saleView;
    private Thread loadingThread, sendingThread;
    private boolean shuttingDown;
    private int status, nameCounter;

    public SaleController(HomeScreen homeScreenView, StoreCommand store, Sale saleView) {
        super(homeScreenView, store, saleView);
        this.saleView = saleView;
        eventSendButton();

        checkEnableSendButton = this::enableSendButton;
    }

    private void eventSendButton() {
        EventHandler<ActionEvent> eventForSendButton = event -> {

            try {
                String msg = saleView.getMsg();
                if (msg.isEmpty())
                    throw new AlertUserException(FILL_MSG_BOX);

                new SendSaleMessageCommand(store, saleView.getMsg()).execute();

                nameCounter = 0;
                shuttingDown = false;

                disableSendButton();
                saleView.clearResponsesArea();

                GetAllConfirmedCustomerNamesCommand confirmedCommand = new GetAllConfirmedCustomerNamesCommand(store);
                confirmedCommand.execute();
                ArrayList<String> names = confirmedCommand.get();

                saleView.addResponse(SEND_RESPONSE, -1);

                if (names.size() > 0)
                    loadingBar();

                sendingThread = new Thread(() -> {
                    for (String name : names) {
                        Platform.runLater(() -> {
                            saleView.addResponse(name + ACCEPT_RESPONSE, status);

                            nameCounter++;
                            System.out.println( names.size());
                            if (nameCounter >= names.size()) {
                                shuttingDown = true;
                                loadingThread.interrupt();
                                sendingThread.interrupt();
                                saleView.loadingBar(-1);
                                enableSendButton();
                            }
                        });

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            sendingThread.interrupt();
                        }
                    }
                });
                sendingThread.start();

            } catch (AlertUserException i) {
                i.showErrorMessage();
            }
        };
        saleView.addEventForSendButton(eventForSendButton);
    }

    private void loadingBar() {
        status = 0;

        loadingThread = new Thread(() -> {
            while (!shuttingDown && !Thread.interrupted()) {
                Platform.runLater(() -> {
                    if (status == 4)
                        status = 0;
                    status++;

                    saleView.loadingBar(status);
                });

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        loadingThread.start();
    }

    private void enableSendButton() {
        if (!sending) {
            saleView.getSendingButton().setDisable(false);
            saleView.getSendingButton().getStyleClass().remove("disable");
            saleView.getSendingButton().getStyleClass().addAll("enable");
            sending = true;
        }
    }

    private void disableSendButton() {
        sending = false;
        saleView.getSendingButton().setDisable(true);
        saleView.getSendingButton().getStyleClass().remove("enable");
        saleView.getSendingButton().getStyleClass().addAll("disable");
    }

}

