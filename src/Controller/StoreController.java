package Controller;

import Model.command.StoreCommand;
import View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StoreController {
    protected StoreCommand storeCommand;
    protected HomeScreen view;
//    private Stage dialog;
//    private VBox dialogVBox;
    protected static Runnable checkEnableCancelButton;
    private boolean addedProduct;

    public StoreController(HomeScreen view, StoreCommand storeCommand) {
//    public StoreController(ModelStore model, HomeScreen view) {
        this.view = view;
        this.storeCommand = storeCommand;

        assignAllEvents();

        // Check if done some Order
        checkEnableCancelButton = this::enableCancelButton;
    }

    private void assignAllEvents() {
        eventProductButton();
        eventCancelButton();
        eventSaleButton();
        eventProductListButton();
    }

    private void eventProductButton() {
        EventHandler<ActionEvent> eventForOrderButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                AddProduct addProductView = new AddProduct();
                view.update(addProductView);
                ProductController productController = new ProductController(view, storeCommand, addProductView);
            }
        };
        view.addEventProductButton(eventForOrderButton);
    }

    private void eventCancelButton() {
        EventHandler<ActionEvent> eventForCancelButton = e -> storeCommand.undoStore();
        // todo: Disable button
        view.addEventCancelButton(eventForCancelButton);
    }

    private void eventSaleButton() {
        EventHandler<ActionEvent> eventForSaleButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                Sale saleView = new Sale();
                view.update(saleView);
//                SaleController orderController = new SaleController(saleView);
//                SaleController orderController = new SaleController(model, saleView);
            }
        };
        view.addEventSaleButton(eventForSaleButton);
    }

    private void eventProductListButton() {
        EventHandler<ActionEvent> eventForProductListButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                ProductList productList = new ProductList();
                view.update(productList);
                ProductListController productListController = new ProductListController(view, storeCommand, productList);
            }
        };
        view.addEventProductListButton(eventForProductListButton);
    }

    private void enableCancelButton() {
        if (!addedProduct) {
            view.getCancelButton().setDisable(false);
            view.assignStyleToButton(view.getCancelButton());
            addedProduct = true;
        }
    }
}

