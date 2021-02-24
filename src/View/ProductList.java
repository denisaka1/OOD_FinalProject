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
    private VBox quickShowVBox;
    private TextField searchTF;
    private Button searchBtn, quickCloseButton;
    private Text profit, profitLabel, quickSortText;
    private final Insets SEARCHBAR_INSETS = new Insets(0, 10, 0, 0);
    private final Insets TABLE_INSETS = new Insets(10, 0, 5, 0);
    private final Insets PROFIT_INSETS = new Insets(0, 0, 0, 15);

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
//        searchTF.setFont(BUTTONS_FONT);
//        searchTF.setMinWidth(TEXT_INPUT_WIDTH_VALUE);
    }

    private void assignSearchButton() {
        searchBtn = new Button();
        searchBtn.getStyleClass().add("button-search");
//        searchBtn.setMinWidth(MIN_BUTTON_WIDTH_VALUE);
//        searchBtn.setFont(BUTTONS_FONT);
    }

    private void assignProfit() {
        profitLabel = new Text("Total Profit: ");
        profit = new Text("0");
        profitHBox = new HBox();
        profitHBox.getChildren().addAll(profitLabel, profit);
        profitHBox.setMargin(profitLabel, PROFIT_INSETS);
    }

    private void centerColumn(TableColumn<Product, String> tc) {
        tc.setStyle("-fx-alignment: center;");
    }

    private void assignProductList() {
        listViewHBox = new HBox();
        tableView = new TableView<Product>();
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
        TableColumn<Product, String> cusPromotions    = new TableColumn<>("Sale?");

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
        listViewHBox.setMargin(tableView, TABLE_INSETS);
    }

    public void setProfit(double newProfit) {
        profit.setText(Double.toString(newProfit));
    }
    public TableView getTableView() {
        return tableView;
    }

    public Button getSearchBtn() {
        return searchBtn;
    }

    public String getSearchValue() {
        return searchTF.getText();
    }

    public VBox productShowDialog(Product p) {
        quickShowVBox = new VBox();
        quickSortText = new Text();
        quickCloseButton = new Button("Close");

        quickSortText.setText(p.toString());
        quickShowVBox.getChildren().addAll(quickSortText, quickCloseButton);
        quickShowVBox.getStyleClass().add("quick-show");
        quickCloseButton.getStyleClass().add("button-quick-close");
        quickShowVBox.setAlignment(Pos.CENTER);

        return quickShowVBox;
    }

    public void addEventSearchButton(EventHandler<ActionEvent> event) {
        searchBtn.setOnAction(event);
    }

    public void addEventToQuickCloseButton(EventHandler<ActionEvent> event) {
        quickCloseButton.setOnAction(event);
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
