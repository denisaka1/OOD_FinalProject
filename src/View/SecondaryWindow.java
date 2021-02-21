package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class SecondaryWindow {
    protected Button backToMainButton;
    protected Button submitButton;
    protected HBox buttonsHBox;
    protected VBox mainView, backToMainVBox, submitVBox;
    protected final double MIN_BUTTON_WIDTH_VALUE = 30.d;
    protected final Font BUTTONS_FONT = Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14);
    protected final double TEXT_INPUT_WIDTH_VALUE = 300.d;

    public SecondaryWindow() {
        mainView = new VBox();
        assignAll();
    }

    private void assignAll() {
        buttonsHBox = new HBox();
        backToMainVBox = new VBox();
        submitVBox = new VBox();

        assignSubmitButton();
        assignBackToMainButton();

        buttonsHBox.getChildren().addAll(submitVBox, backToMainVBox);
    }

    private void assignBackToMainButton() {
        backToMainButton = new Button("Back");
        backToMainButton.setMinWidth(MIN_BUTTON_WIDTH_VALUE);
        backToMainButton.setFont(BUTTONS_FONT);
        backToMainVBox.getChildren().add(backToMainButton);
//        backToMainHBox.getChildren().add(backToMainButton);
    }

    private void assignSubmitButton() {
        submitButton = new Button("Submit");
        submitButton.setMinWidth(MIN_BUTTON_WIDTH_VALUE);
        submitButton.setFont(BUTTONS_FONT);
        submitVBox.getChildren().add(submitButton);
    }

    public void eventBackToMainButton(EventHandler<ActionEvent> event) {
        backToMainButton.setOnAction(event);
    }

    public void eventSubmitButton(EventHandler<ActionEvent> event) {
        submitButton.setOnAction(event);
    }

    public VBox update() {
        mainView.getChildren().clear();

        return mainView;
    }

}
