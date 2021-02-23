package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Sale extends SecondaryWindow  {
    private HBox sendHBox, responsesHBox;
    private Button sendButton; // todo: Check duplicate
    private TextArea msgTA, responsesTA;

    public Sale() {
        assignAll();
    }

    private void assignAll() {
        assignSend();
        assignResponsesArea();
    }

    private void assignSend() {
        sendHBox = new HBox();
        assignSendButton();
        assignMsgTextArea();
        sendHBox.getChildren().addAll(msgTA, sendButton);
    }

    private void assignResponsesArea() {
        responsesHBox = new HBox();
        responsesTA = new TextArea();
        responsesTA.setDisable(true);
        responsesHBox.getChildren().add(responsesTA);
    }

    private void assignMsgTextArea() {
        msgTA = new TextArea();
    }

    private void assignSendButton() {
        // todo: add image
        sendButton = new Button("Send");
    }

    public String getMsg() {
        return msgTA.getText();
    }

    public void addEventForSendButton(EventHandler<ActionEvent> event) {
        sendButton.setOnAction(event);
    }

    public void addResponse(String s) {
        responsesTA.setText(responsesTA.getText() + "\n" + s);
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();
        mainView.getChildren().addAll(sendHBox, responsesHBox, backToMainButton);
//        mainView.setMargin(ViewGUI.DEFAULT_INSETS);

        return mainView;
    }
}
