package View;

import Model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductList extends SecondaryWindow {
    private TableView tableView;
    private HBox listViewHBox, searchBarHBox, profitHBox;
    private TextField searchTF;
    private Button searchBtn;
    private Text profit, profitLabel;
    private final Insets SEARCHBAR_INSETS = new Insets(0, 20, 0, 0);

    public ProductList() {
        assignAll();
    }

    public void assignAll() {
        assignProductList();
        assignSearchField();
        assignSearchButton();
        assignProfit();
    }

    private void assignSearchField() {
        searchTF = new TextField();
        searchTF.setPromptText("Enter SKU to search");
        searchTF.setFont(BUTTONS_FONT);
        searchTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
    }

    private void assignSearchButton() {
        searchBtn = new Button();
        searchBtn.setMinWidth(MIN_BUTTON_WIDTH_VALUE);
        searchBtn.setFont(BUTTONS_FONT);
    }

    private void assignProfit() {
        profitLabel = new Text("Total Profit: ");
        profit = new Text("0");
        profitHBox = new HBox();
//        profitLabel.setFont(BUTTONS_FONT); // todo: font
        profitHBox.getChildren().addAll(profitLabel, profit);
    }

    private void centerColumn(TableColumn<Product, String> tc) {
        tc.setStyle("-fx-alignment: center;");
    }

    private void setColumnSize(TableColumn<Button, String> tc, int size) {
        tc.setMaxWidth(size);
        tc.setMinWidth(size);
    }

    private void assignProductList() {
        listViewHBox = new HBox();
        tableView = new TableView<Product>();

        tableView.setStyle("-fx-alignment: center;");
        tableView.setMaxWidth(520);
        tableView.setMaxHeight(300);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set Columns
        TableColumn<Product, String> product            = new TableColumn<>("Product");
        TableColumn<Product, String> customer           = new TableColumn<>("Customer");
        TableColumn<Product, String> prodSku            = new TableColumn<>("SKU");
        TableColumn<Product, String> prodName           = new TableColumn<>("Name");
        TableColumn<Product, String> prodRetailPrice    = new TableColumn<>("Retail");
        TableColumn<Product, String> prodWholesalePrice = new TableColumn<>("Wholesale");
        TableColumn<Product, String> cusName          = new TableColumn<>("Name");
        TableColumn<Product, String> cusPhone         = new TableColumn<>("Phone");
        TableColumn<Product, String> cusPromotions    = new TableColumn<>("Promotions?");

        product .getColumns().addAll(prodSku, prodName, prodRetailPrice, prodWholesalePrice);
        customer.getColumns().addAll(cusName, cusPhone, cusPromotions);
        tableView.getColumns().addAll(product, customer);

        // Style Columns
        centerColumn(product);
        centerColumn(customer);
        centerColumn(prodSku);
        centerColumn(prodName);
        centerColumn(prodRetailPrice);
        centerColumn(prodWholesalePrice);
        centerColumn(cusName);
        centerColumn(cusPhone);
        centerColumn(cusPromotions);

        // Values
        prodSku             .setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        prodName            .setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodRetailPrice     .setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        prodWholesalePrice  .setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        cusName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        cusPhone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getPhoneNumber()));
        cusPromotions.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getEventOnSales() ? "Yes" : "No"));

        listViewHBox.setAlignment(Pos.CENTER);
        listViewHBox.getChildren().add(tableView);
    }

    public void setProfit(double newProfit) {
        profit.setText(Double.toString(newProfit));
    }
    public TableView getListView() {
        return tableView;
    }

    public Button getSearchBtn() {
        return searchBtn;
    }

    public String getSearchValue() {
        return searchTF.getText();
    }

    public void addEventSearchButton(EventHandler<ActionEvent> event) {
        searchBtn.setOnAction(event);
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();

        searchBarHBox = new HBox();
        searchBarHBox.getChildren().addAll(backToMainVBox, searchTF, searchBtn);
        searchBarHBox.setMargin(backToMainVBox, SEARCHBAR_INSETS);
        searchBarHBox.setMargin(searchTF, SEARCHBAR_INSETS);
        searchBarHBox.setAlignment(Pos.CENTER);

        mainView.getChildren().addAll(searchBarHBox, listViewHBox, profitHBox);
        mainView.setAlignment(Pos.CENTER);
        return mainView;
    }
}
