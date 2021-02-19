package View;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddProduct extends SecondaryWindow {
    private TextField nameTF, phoneNumberTF, skuTF, productNameTF, retailPriceTF, wholesalePriceTF;
    private HBox nameHBox, phoneNumberHBox, skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox;

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
        skuTF.setFont(BUTTONS_FONT);
        skuTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        skuHBox.getChildren().addAll(skuTF);
    }

    private void setProductNameField() {
        productNameHBox = new HBox();
        productNameTF = new TextField();
        productNameTF.setPromptText("Enter Product Name");
        productNameTF.setFont(BUTTONS_FONT);
        productNameTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        productNameHBox.getChildren().addAll(productNameTF);
    }

    private void setRetailPriceField() {
        retailPriceHBox = new HBox();
        retailPriceTF = new TextField();
        retailPriceTF.setPromptText("Enter Retail Price");
        retailPriceTF.setFont(BUTTONS_FONT);
        retailPriceTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        retailPriceHBox.getChildren().addAll(retailPriceTF);
    }

    private void setWholesalePriceField() {
        wholesalePriceHBox = new HBox();
        wholesalePriceTF = new TextField();
        wholesalePriceTF.setPromptText("Enter Wholesale Price");
        wholesalePriceTF.setFont(BUTTONS_FONT);
        wholesalePriceTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        wholesalePriceHBox.getChildren().addAll(wholesalePriceTF);
    }

    private void setNameField() {
        nameHBox = new HBox();
        nameTF = new TextField();
        nameTF.setPromptText("Enter Customer Name");
        nameTF.setFont(BUTTONS_FONT);
        nameTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        nameHBox.getChildren().addAll(nameTF);
    }

    private void setPhoneNumberField() {
        phoneNumberHBox = new HBox();
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText("Enter Phone Number");
        phoneNumberTF.setFont(BUTTONS_FONT);
        phoneNumberTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        phoneNumberHBox.getChildren().addAll(phoneNumberTF);
    }

    // Checkbox
    /*private void setPhoneNumberField() {
        phoneNumberHBox = new HBox();
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText("Enter Phone Number");
        phoneNumberTF.setFont(BUTTONS_FONT);
        phoneNumberTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
        phoneNumberHBox.getChildren().addAll(phoneNumberTF);
    }*/

    @Override
    public VBox update() {
//        mainView.getChildren().clear();
        mainView.getChildren().addAll(skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox, nameHBox,phoneNumberHBox, buttonsHBox);
//        mainView.setMargin(submitButton, DEFAULT_INSETS);

        return mainView;
    }

    public String getSKU() {
        return skuTF.getText();
    }

    public String getProductName() {
        return productNameTF.getText();
    }

    public String getRetailPrice() {
        return retailPriceTF.getText();
    }

    public String getWholesalePrice() {
        return wholesalePriceTF.getText();
    }

    public String getCustomerName() {
        return nameTF.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberTF.getText();
    }
}