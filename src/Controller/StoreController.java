package Controller;

import Model.command.StoreCommand;
import View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StoreController {
    protected StoreCommand storeCommand;
    protected HomeScreen view;
    protected static Runnable checkEnableCancelButton;
    private boolean addedProduct;

    public StoreController(HomeScreen view, StoreCommand storeCommand) {
        this.view = view;
        this.storeCommand = storeCommand;

        assignAllEvents();

        // Check if done some Order
        checkEnableCancelButton = this::enableCancelButton;
    }

    private void assignAllEvents() {
        eventOrderButton();
        eventProductButton();
        eventCancelButton();
        eventSaleButton();
        eventProductListButton();
    }

    private void eventOrderButton() {
        EventHandler<ActionEvent> eventForOrderButton = event -> {
            storeCommand.saveProductByOrderInStore(view.getOrderChoice());
            view.closeDialog();
            view.loadMainProgram();
        };
        view.addEventToOrder(eventForOrderButton);
    }

    private void eventProductButton() {
        EventHandler<ActionEvent> eventForOrderButton = event -> {
            AddProduct addProductView = new AddProduct();
            view.update(addProductView);
            ProductController productController = new ProductController(view, storeCommand, addProductView);
        };
        view.addEventProductButton(eventForOrderButton);
    }

    private void eventCancelButton() {
        EventHandler<ActionEvent> eventForCancelButton = event -> {
            storeCommand.undoStore();
            disableCancelButton();
        };

        view.addEventCancelButton(eventForCancelButton);
    }

    private void eventSaleButton() {
        EventHandler<ActionEvent> eventForSaleButton = event -> {
            Sale saleView = new Sale();
            view.update(saleView);
            SaleController orderController = new SaleController(view, storeCommand, saleView);
        };
        view.addEventSaleButton(eventForSaleButton);
    }

    private void eventProductListButton() {
        EventHandler<ActionEvent> eventForProductListButton = event -> {
            ProductList productList = new ProductList();
            view.update(productList);
            ProductListController productListController = new ProductListController(view, storeCommand, productList);
        };
        view.addEventProductListButton(eventForProductListButton);
    }

    private void enableCancelButton() {
        if (!addedProduct) {
            view.getCancelButton().setDisable(false);
            MainButtons.assignStyleToButton(view.getCancelButton());
            addedProduct = true;
        }
    }

    private void disableCancelButton() {
        MainButtons.assignDisableStyleToButton(view.getCancelButton());
        addedProduct = false;
    }
}

