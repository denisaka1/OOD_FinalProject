package Controller;

import Exceptions.IllegalInputException;
import Model.Product;
import Model.command.AddProductCommand;
import Model.command.Store;
import Model.observer.Customer;
import View.AddProduct;
import View.HomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ProductController extends BackButtonController {
    private AddProduct addProductView;
//    private StoreCommand storeCommand;
    private Store store;

    public ProductController(HomeScreen homeScreenView, Store store, AddProduct addProductView) {
        super(homeScreenView, store, addProductView);
        this.addProductView = addProductView;
        this.store = store;

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
                    String retailPrice = addProductView.getRetailPrice(); // to double
                    String wholesalePrice = addProductView.getWholesalePrice(); // to double
                    String customerName = addProductView.getCustomerName();
                    String phoneNumber = addProductView.getPhoneNumber();
                    boolean promNotification = addProductView.getPromotionNotification();

                    int doubleRetailPrice = 0, doubleWholesalePrice = 0;

                    if (promNotification && (phoneNumber.isEmpty() || customerName.isEmpty()))
                        throw new IllegalInputException("To receive notifications about promotions please enter a Customer");

                    if (!customerName.isEmpty() && !customerName.matches("^[a-zA-Z\\s]+"))
                        throw new IllegalInputException("Please fill a legal name");

                    if (!phoneNumber.isEmpty() && !phoneNumber.matches("(05[0-9]|0[12346789])([0-9]{7})"))
                        throw new IllegalInputException("Please fill a legal israeli cell phone number without hyphens");

                    if (!retailPrice.isEmpty()) {
                        if (!retailPrice.matches("[0-9]*"))
                            throw new IllegalInputException("Please fill a legal retail price");
                        else
                            doubleRetailPrice = Integer.parseInt(retailPrice);
                    }

                    if (!wholesalePrice.isEmpty()) {
                        if (!wholesalePrice.matches("[0-9]*"))
                            throw new IllegalInputException("Please fill a wholesale retail price");
                        else
                            doubleWholesalePrice = Integer.parseInt(wholesalePrice);
                    }

                    Customer customer = new Customer(customerName, phoneNumber, promNotification);
                    Product product = new Product(sku,
                            productName,
                            doubleRetailPrice,
                            doubleWholesalePrice,
                            customer);

                    AddProductCommand addProductCommand = new AddProductCommand(store, product);
                    addProductCommand.execute();

//                    boolean renewProduct = storeCommand.addProductToStore(product);
                    boolean renewProduct = addProductCommand.isRenew();

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
        addProductView.eventOrderButton(eventForOrderButton);
    }
}
