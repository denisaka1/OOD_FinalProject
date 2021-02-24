package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Sale extends SecondaryWindow  {
    private HBox sendHBox, responsesHBox, headerHBox;
    private Button sendButton;
    private TextArea msgTA, responsesTA;
    private Text saleTitle;
    private String responseTest = "";
    private final Insets SEND_INSETS = new Insets(10, 0, 0, 10);
    private final Insets BACK_INSETS = new Insets(0, 0, 0, -120);
    private final Insets SALE_INSETS = new Insets(0, 0, 0, 120);
    private final Insets TOP_INSETS = new Insets(10, 0, 0, 0);

    public Sale() {
        assignAll();
    }

    private void assignAll() {
        setSaleTitle();
        assignSend();
        assignResponsesArea();
    }

    private void setSaleTitle() {
        headerHBox = new HBox();
        saleTitle = new Text();
        saleTitle.setText("Notification for NEW SALE");
        saleTitle.getStyleClass().add("text-header");

        headerHBox.getChildren().addAll(backToMainVBox, saleTitle);
        headerHBox.setMargin(saleTitle, SALE_INSETS);
        headerHBox.setMargin(backToMainVBox, BACK_INSETS);
        headerHBox.setAlignment(Pos.CENTER);
    }

    private void assignSend() {
        sendHBox = new HBox();
        assignSendButton();
        assignMsgTextArea();
        sendHBox.getChildren().addAll(msgTA, sendButton);
        sendHBox.setMargin(sendButton, SEND_INSETS);
        sendHBox.setMargin(msgTA, TOP_INSETS);
        sendHBox.setAlignment(Pos.CENTER);
    }

    private void assignResponsesArea() {
        responsesHBox = new HBox();
        responsesTA = new TextArea();
        responsesTA.setEditable(false);
        responsesTA.getStyleClass().add("text-area-response");
        responsesHBox.getChildren().add(responsesTA);
        responsesHBox.setMargin(responsesTA, TOP_INSETS);
        responsesHBox.setAlignment(Pos.CENTER);
    }

    private void assignMsgTextArea() {
        msgTA = new TextArea();
        msgTA.getStyleClass().add("text-area-msg");
    }

    private void assignSendButton() {
        sendButton = new Button("Send");
        sendButton.getStyleClass().add("button-send");
    }

    public String getMsg() {
        return msgTA.getText();
    }

    public void addEventForSendButton(EventHandler<ActionEvent> event) {
        sendButton.setOnAction(event);
    }

    public void addResponse(String s, int status) {
        responseTest += s + "\n";
        responsesTA.setText(responseTest);
        if (status != -1)
            loadingBar(status);
    }

    public void loadingBar(int status) {
        if (status == -1)
            responsesTA.setText(responseTest + "\nSending completed successfully");
        else {
            String dots = "";
            for (int i = 0; i < status; i++)
                dots += ".";
            responsesTA.setText(responseTest + "\nWaiting for response" + dots);
        }
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();
        mainView.getChildren().addAll(headerHBox, sendHBox, responsesHBox);

        return mainView;
    }
}
