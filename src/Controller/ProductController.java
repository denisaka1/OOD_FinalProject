package Controller;

import Model.Product;
import Model.command.StoreCommand;
import Model.observer.Customer;
import View.AddProduct;
import View.HomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ProductController extends SecondaryWindowController {
    private AddProduct addProductView;
    private StoreCommand storeCommand;

    public ProductController(HomeScreen homeScreenView, StoreCommand storeCommand, AddProduct addProductView) {
        super(homeScreenView, storeCommand, addProductView);
        this.addProductView = addProductView;
        this.storeCommand = storeCommand;

        eventForOrderButton();
    }

    private void eventForOrderButton() {
        EventHandler<ActionEvent> eventForOrderButton = new EventHandler<ActionEvent>() {
            Alert alert;

            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String sku = addProductView.getSKU();
                    if (sku.isEmpty())
                        throw new NullPointerException();

                    String productName = addProductView.getProductName();
                    double retailPrice = Double.parseDouble(addProductView.getRetailPrice());
                    double wholesalePrice = Double.parseDouble(addProductView.getWholesalePrice());
                    String customerName = addProductView.getCustomerName();
                    String phoneNumber = addProductView.getPhoneNumber();

                    Customer customer = new Customer(customerName, phoneNumber, false); // todo: make checkbox
                    Product product = new Product(sku, productName, retailPrice, wholesalePrice, customer);
                    storeCommand.addProductToStore(product);

                    StoreController.checkEnableCancelButton.run();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Product Added");
                    alert.showAndWait();

                    view.loadMain();

                } catch(NullPointerException npe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter SKU");
                    alert.showAndWait();
                } catch(Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();
                }
            }
        };
        addProductView.eventSubmitButton(eventForOrderButton);
    }
}
