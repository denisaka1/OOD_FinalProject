package Controller;

import Model.command.StoreCommand;
import View.HomeScreen;
import View.SecondaryWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SecondaryWindowController extends StoreController {
    private SecondaryWindow secondaryWindow;

    public SecondaryWindowController(HomeScreen homeScreenView, StoreCommand storeCommand, SecondaryWindow secondaryWindow) {
        super(homeScreenView, storeCommand);
        this.secondaryWindow = secondaryWindow;

        eventForBackToMainButton();
    }

    private void eventForBackToMainButton() {
        EventHandler<ActionEvent> eventForBackToMainButton = e -> view.loadMain();
        secondaryWindow.eventBackToMainButton(eventForBackToMainButton);
    }
}
