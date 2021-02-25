package Controller;

import Model.command.Store;
import View.HomeScreen;
import View.SecondaryWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SecondaryWindowController extends StoreController {
    private SecondaryWindow secondaryWindow;

    public SecondaryWindowController(HomeScreen homeScreenView, Store store, SecondaryWindow secondaryWindow) {
        super(homeScreenView, store);
        this.secondaryWindow = secondaryWindow;

        eventForBackToMainButton();
    }

    private void eventForBackToMainButton() {
        EventHandler<ActionEvent> eventForBackToMainButton = e -> view.loadMain();
        secondaryWindow.eventBackToMainButton(eventForBackToMainButton);
    }
}
