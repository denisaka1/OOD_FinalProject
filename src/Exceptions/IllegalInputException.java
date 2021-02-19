package Exceptions;

import javafx.scene.control.Alert;

public class IllegalInputException extends Exception {

    private String name;
    private Alert alert;

    public IllegalInputException(String name, Alert alert) {
        this.name = name;
        this.alert = alert;
    }

    public IllegalInputException(String name) {
        this.name = name;
    }

    public void showErrorMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(name);
        alert.show();
    }

}