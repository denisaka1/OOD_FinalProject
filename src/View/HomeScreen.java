package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Model.command.Store.ASC;

public class HomeScreen extends MainButtons {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 400;
    public static boolean ACTION_TAKEN = false;
    private Scene scene;
    private BorderPane borderPane;
    private Stage dialog, stage;
    private VBox dialogVBox;
    private ChoiceBox dialogCB;
    private Button dialogButton;

    public HomeScreen(Stage stage) {
        super();
        this.stage = stage;

        stage.setTitle("Store");
        borderPane = new BorderPane();
        borderPane.setCenter(loadMainButtons());

        sortDialog();
    }

    public void loadMainProgram() {
        scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void sortDialog() {
        dialog = new Stage();

        dialogCB = new ChoiceBox();
        dialogCB.getItems().addAll("ASC", "DESC", "Income");
        dialogCB.getSelectionModel().select(ASC);

        dialogButton = new Button("Choose");

        dialogVBox = new VBox(new Text("Order Product By"), dialogCB, dialogButton);
        VBox.setMargin(dialogCB, new Insets(10));
        VBox.setMargin(dialogButton, new Insets(5));
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(30));

        dialog.setScene(new Scene(dialogVBox));
        dialog.setResizable(false);
        dialog.show();
    }

    public int getOrderChoice() {
        return dialogCB.getSelectionModel().getSelectedIndex();
    }

    public void closeDialog() {
        dialog.close();
    }

    public void loadMain() {
        borderPane.getChildren().clear();
        borderPane.setCenter(loadMainButtons());
        if (ACTION_TAKEN) {
            assignDisableStyleToButton(getCancelButton());
            ACTION_TAKEN = false;
        }
    }

    public void update(SecondaryWindow newMainView) {
        borderPane.getChildren().clear();
        borderPane.setCenter(newMainView.update());
    }

    public Scene getScene() {
        return scene;
    }

    public void addEventToOrder(EventHandler<ActionEvent> event) {
        dialogButton.setOnAction(event);
    }

}
