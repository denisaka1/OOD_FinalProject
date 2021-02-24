package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddProduct extends SecondaryWindow {
    private TextField nameTF, phoneNumberTF, skuTF, productNameTF, retailPriceTF, wholesalePriceTF;
    private HBox headerHBox, orderHBox, nameHBox, phoneNumberHBox, skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox, saleHBox;
    private CheckBox saleCB;
    private Button orderButton;
    private Text orderTitle;
    private final Insets TEXT_FIELD_INSETS = new Insets(0, 0, 5, 0);
    private final Insets ORDER_INSETS = new Insets(0, 0, 0, 120);
    private final Insets BACK_INSETS = new Insets(0, 0, 0, -170);
    private final Insets SALE_INSETS = new Insets(0, 0, 0, 70);


    public AddProduct() {
        super();
        assignAll();
    }

    private void assignAll() {
        setPhoneNumberField();
        setNameField();
        setWholesalePriceField();
        setSKUField();
        setProductNameField();
        setRetailPriceField();
        setSaleField();
        assignOrderButton();
        setOrderTitle();
    }


   private void setOrderTitle() {
       headerHBox = new HBox();
       orderTitle = new Text();
       orderTitle.setText("New Order");
       orderTitle.getStyleClass().add("text-header");

       headerHBox.getChildren().addAll(backToMainVBox, orderTitle);
       headerHBox.setMargin(orderTitle, ORDER_INSETS);
       headerHBox.setMargin(backToMainVBox, BACK_INSETS);
       headerHBox.setAlignment(Pos.CENTER);
   }

/*    private void setProductTitle() {
        skuHBox = new HBox();
        skuTF = new TextField();
        skuTF.setPromptText("Enter SKU");
        skuTF.setFont(BUTTONS_FONT);
        skuTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        skuHBox.getChildren().addAll(skuTF);
    }

    private void setCustomerTitle() {
        skuHBox = new HBox();
        skuTF = new TextField();
        skuTF.setPromptText("Enter SKU");
        skuTF.setFont(BUTTONS_FONT);
        skuTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        skuHBox.getChildren().addAll(skuTF);
    }*/

    private void setSKUField() {
        skuHBox = new HBox();
        skuTF = new TextField();
        skuTF.setPromptText("Enter SKU");
//        skuTF.setFont(BUTTONS_FONT);
//        skuTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        skuHBox.getChildren().addAll(skuTF);
        skuHBox.setAlignment(Pos.CENTER);
    }

    private void setProductNameField() {
        productNameHBox = new HBox();
        productNameTF = new TextField();
        productNameTF.setPromptText("Enter Product Name");
//        productNameTF.setFont(BUTTONS_FONT);
//        productNameTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        productNameHBox.getChildren().addAll(productNameTF);
        productNameHBox.setAlignment(Pos.CENTER);

    }

    private void setRetailPriceField() {
        retailPriceHBox = new HBox();
        retailPriceTF = new TextField();
        retailPriceTF.setPromptText("Enter Retail Price");
//        retailPriceTF.setFont(BUTTONS_FONT);
//        retailPriceTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        retailPriceHBox.getChildren().addAll(retailPriceTF);
        retailPriceHBox.setAlignment(Pos.CENTER);

    }

    private void setWholesalePriceField() {
        wholesalePriceHBox = new HBox();
        wholesalePriceTF = new TextField();
        wholesalePriceTF.setPromptText("Enter Wholesale Price");
//        wholesalePriceTF.setFont(BUTTONS_FONT);
//        wholesalePriceTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        wholesalePriceHBox.getChildren().addAll(wholesalePriceTF);
        wholesalePriceHBox.setAlignment(Pos.CENTER);

    }

    private void setNameField() {
        nameHBox = new HBox();
        nameTF = new TextField();
        nameTF.setPromptText("Enter Customer Name");
//        nameTF.setFont(BUTTONS_FONT);
//        nameTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        nameHBox.getChildren().addAll(nameTF);
        nameHBox.setAlignment(Pos.CENTER);

    }

    private void setPhoneNumberField() {
        phoneNumberHBox = new HBox();
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText("Enter Phone Number");
//        phoneNumberTF.setFont(BUTTONS_FONT);
//        phoneNumberTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        phoneNumberHBox.getChildren().addAll(phoneNumberTF);
        phoneNumberHBox.setAlignment(Pos.CENTER);

    }

    private void assignOrderButton() {
        orderHBox = new HBox();
        orderButton = new Button("Add!");
        orderButton.getStyleClass().add("button-order");
        orderHBox.getChildren().add(orderButton);
        orderHBox.setAlignment(Pos.CENTER);
    }

    private void setSaleField() {
        saleHBox = new HBox();
        saleCB = new CheckBox();
        saleCB.setText("Notification on promotions?");
//        saleCB.setFont(BUTTONS_FONT);
//        saleCB.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        saleHBox.getChildren().addAll(saleCB);
        saleHBox.setMargin(saleCB, SALE_INSETS);
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();
        mainView.getChildren().addAll(headerHBox, skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox, nameHBox, phoneNumberHBox, saleHBox, orderHBox);
        mainView.setMargin(skuHBox, TEXT_FIELD_INSETS);
        mainView.setMargin(productNameHBox, TEXT_FIELD_INSETS);
        mainView.setMargin(retailPriceHBox, TEXT_FIELD_INSETS);
        mainView.setMargin(wholesalePriceHBox, TEXT_FIELD_INSETS);
        mainView.setMargin(nameHBox, TEXT_FIELD_INSETS);

        return mainView;
    }

    public String getSKU() {
        return skuTF.getText();
    }

    public String getProductName() {
        return productNameTF.getText();
    }

    public double getRetailPrice() {
        if (!retailPriceTF.getText().isEmpty())
            return Double.parseDouble(retailPriceTF.getText());
        return 0.0;
    }

    public double getWholesalePrice() {
        if (!wholesalePriceTF.getText().isEmpty())
            return Double.parseDouble(wholesalePriceTF.getText());
        return 0.0;
    }

    public String getCustomerName() {
        return nameTF.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberTF.getText();
    }

    public Boolean getPromotionNotification() {
        return saleCB.isSelected();
    }

    public void eventOrderButton(EventHandler<ActionEvent> event) {
        orderButton.setOnAction(event);
    }

}