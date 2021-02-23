package Controller;

import Exceptions.IllegalInputException;
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
                String sku = addProductView.getSKU();

                try {
                    if (sku.isEmpty())
                        throw new IllegalInputException("Please fill in the SKU field");

                    String productName = addProductView.getProductName();
                    double retailPrice = addProductView.getRetailPrice();
                    double wholesalePrice = addProductView.getWholesalePrice();
                    String customerName = addProductView.getCustomerName();
                    String phoneNumber = addProductView.getPhoneNumber();
                    boolean promNotification = addProductView.getPromotionNotification();

                    if (promNotification && (phoneNumber.isEmpty() || customerName.isEmpty()))
                        throw new IllegalInputException("To receive notifications about promotions please enter a Customer");

                    if (!customerName.matches("^[a-zA-Z\\s]+") && !customerName.isEmpty())
                        throw new IllegalInputException("Please fill a legal name");

                    if (!phoneNumber.matches("(05[0-9]|0[12346789])([0-9]{7})") && !phoneNumber.isEmpty())
                        throw new IllegalInputException("Please fill a legal israeli cell phone number without hyphens");

                    Customer customer = new Customer(customerName, phoneNumber, promNotification);
                    Product product = new Product(sku, productName, retailPrice, wholesalePrice, customer);
                    boolean renewProduct = storeCommand.addProductToStore(product);

                    StoreController.checkEnableCancelButton.run();
                    // todo: Undo on replaced products ?

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    if (!renewProduct)
                        alert.setContentText("Product Added!");
                    else
                        alert.setContentText("Product Replaced!");
                    alert.showAndWait();

                    view.loadMain();
                } catch (IllegalInputException i) {
                    i.showErrorMessage();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();
                }
            }
        };
        addProductView.eventSubmitButton(eventForOrderButton);
    }
}
