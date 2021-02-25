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
    public static final String css = HomeScreen.class.getResource("/Assets/stylesheet.css").toExternalForm();


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
        scene.getStylesheets().add(css);
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
        dialogButton.getStyleClass().add("button-order");

        dialogVBox = new VBox(new Text("Order Product By"), dialogCB, dialogButton);
        VBox.setMargin(dialogCB, new Insets(10));
        VBox.setMargin(dialogButton, new Insets(5));
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(30));

        Scene dialogScene = new Scene(dialogVBox);
        dialogScene.getStylesheets().add(css);

        dialog.setScene(dialogScene);
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

    public void update(BackButtonView newMainView) {
        borderPane.getChildren().clear();
        borderPane.setCenter(newMainView.update());
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void addEventToOrder(EventHandler<ActionEvent> event) {
        dialogButton.setOnAction(event);
    }

}
