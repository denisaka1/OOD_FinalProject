package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainButtons {
    private Button productButton, cancelButton, saleButton, productListButton;
    private Text productText, cancelText, saleText, productListText;
    private VBox mainView, productListVBox, productVBox, cancelVBox, saleVBox;
    private HBox bottomHBox, topHBox;
    public static final Font BUTTONS_FONT = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 13);
    public static final Insets BUTTONS_INSETS = new Insets(0, 40, 20, 0);
    public static final Insets HEADER_INSETS = new Insets(20, 0, 0, 0);
//    public static final String HOVERED_BUTTON_STYLE  = "-fx-background-color: transparent; -fx-opacity : 0.5;";
//    public static final String DISABLE_BUTTON_STYLE  = "-fx-background-color: transparent; -fx-opacity : 0.3;";
//    public static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent; -fx-opacity : 1;";
//    public static final String PRESSED_BUTTON_STYLE = "-fx-background-color: transparent; -fx-opacity : 0;";

    public MainButtons() {
        // Assign main buttons
        topHBox = new HBox();
        bottomHBox = new HBox();
        assignMainButtons();

        // Main view
        mainView = new VBox();
        mainView.getChildren().addAll(bottomHBox, topHBox);
        mainView.setAlignment(Pos.CENTER);
    }

    private void assignMainButtons() {
        assignNewProductButton();
        assignCancelButton();
        assignSaleButton();
        assignProductListButton();
        bottomHBox.getChildren().addAll(productVBox, cancelVBox);
        topHBox.getChildren().addAll(productListVBox, saleVBox);
        bottomHBox.setAlignment(Pos.CENTER);
        topHBox.setAlignment(Pos.CENTER);
        HBox.setMargin(productVBox, BUTTONS_INSETS);
        HBox.setMargin(productListVBox, BUTTONS_INSETS);
    }

    public static void assignStyleToButton(Button button)  {
        button.getStyleClass().clear();
        button.getStyleClass().addAll("button-main", "button-cancel");
    }

    public static void assignDisableStyleToButton(Button button) {
        button.getStyleClass().clear();
        button.setDisable(true);
        button.getStyleClass().addAll("disable", "button-main", "button-cancel");
    }

/*    public static void assignStyleToButton(Button button)  {
        //todo: OPTIMIZE BARAK
        button.setStyle(IDLE_BUTTON_STYLE);
        button.setOnMouseExited(e -> button.setStyle(IDLE_BUTTON_STYLE));
        button.setOnMouseReleased(e -> button.setStyle(IDLE_BUTTON_STYLE));
        button.setOnMouseEntered(e -> button.setStyle(HOVERED_BUTTON_STYLE ));
        button.setOnMousePressed(e -> button.setStyle(PRESSED_BUTTON_STYLE));
    }

    public static void assignDisableStyleToButton(Button button) {
        //todo: OPTIMIZE BARAK
        button.setDisable(true);
        button.setStyle(DISABLE_BUTTON_STYLE);
        button.setOnMouseExited(e -> button.setStyle(DISABLE_BUTTON_STYLE));
        button.setOnMouseReleased(e -> button.setStyle(DISABLE_BUTTON_STYLE));
        button.setOnMouseEntered(e -> button.setStyle(DISABLE_BUTTON_STYLE ));
        button.setOnMousePressed(e -> button.setStyle(DISABLE_BUTTON_STYLE));
    }

    private Button makeButton(String path) {
        try {
            FileInputStream input = new FileInputStream(path);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            return new Button("", imageView);

        } catch (FileNotFoundException e) {
            // todo: err
            return null;
        }
    }*/

    private void assignNewProductButton() {
        productVBox = new VBox();

        productButton = new Button("");
        productButton.getStyleClass().addAll("button-main", "button-product");
//        productButton = makeButton("src/Images/newProduct.png");
//        assignStyleToButton(productButton);

        productText = new Text("New Purchase");
        productText.setFont(BUTTONS_FONT);

        productVBox = new VBox();
        productVBox.getChildren().addAll(productButton, productText);
        productVBox.setAlignment(Pos.CENTER);
    }

    private void assignCancelButton() {
        cancelButton = new Button("");
        assignDisableStyleToButton(cancelButton);

//        cancelButton = makeButton("src/Images/undo.png");
//        assignStyleToButton(cancelButton);
//        cancelButton.setDisable(true);
//        cancelButton.setStyle(DISABLE_BUTTON_STYLE);

//        assignDisableStyleToButton(cancelButton);

        cancelText = new Text("Cancel Last Order");
        cancelText.setFont(BUTTONS_FONT);

        cancelVBox = new VBox();
        cancelVBox.getChildren().addAll(cancelButton, cancelText);
        cancelVBox.setAlignment(Pos.CENTER);
    }

    private void assignSaleButton() {
//        saleButton = makeButton("src/Images/newSale.png");
//        assignStyleToButton(saleButton);

        saleButton = new Button("");
        saleButton.getStyleClass().addAll("button-main", "button-sale");

        saleText = new Text("New Sale");
        saleText.setFont(BUTTONS_FONT);

        saleVBox = new VBox();
        saleVBox.getChildren().addAll(saleButton, saleText);
        saleVBox.setAlignment(Pos.CENTER);
    }

    private void assignProductListButton() {
        productListButton = new Button("");
        productListButton.getStyleClass().addAll("button-main", "button-list-products");

//        productListButton = makeButton("src/Images/ProductsList.png");
//        assignStyleToButton(productListButton);

        productListText = new Text("Show Product List");
        productListText.setFont(BUTTONS_FONT);

        productListVBox = new VBox();
        productListVBox.getChildren().addAll(productListButton, productListText);
        productListVBox.setAlignment(Pos.CENTER);
    }

    public VBox loadMainButtons() {
        return mainView;
    }

    public void addEventProductButton(EventHandler<ActionEvent> event) {
        productButton.setOnAction(event);
    }

    public void addEventCancelButton(EventHandler<ActionEvent> event) {
        cancelButton.setOnAction(event);
    }

    public void addEventSaleButton(EventHandler<ActionEvent> event) {
        saleButton.setOnAction(event);
    }

    public void addEventProductListButton(EventHandler<ActionEvent> event) {
        productListButton.setOnAction(event);
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}
