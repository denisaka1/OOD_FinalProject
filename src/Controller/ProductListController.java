package Controller;

import Exceptions.IllegalInputException;
import Model.Product;
import Model.command.StoreCommand;
import View.HomeScreen;
import View.ProductList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ProductListController extends SecondaryWindowController {
    private ProductList productList;
    private double profit;

    public ProductListController(HomeScreen homeScreenView, StoreCommand storeCommand, ProductList productList) {
        super(homeScreenView, storeCommand, productList);
        this.productList = productList;
        profit = 0;

        setProductInListView();
        addRemoveButtonToTable();
        eventSearchButton();
        setProfit();
    }

    private void setProductInListView() {
        productList.getListView().getItems().clear();
        for (Product p : storeCommand.getAllProductsFromStore()) {
            productList.getListView().getItems().add(p);
            profit += p.getRetailPrice() - p.getWholesalePrice();
        }
    }

    private void setProfit() {
        productList.setProfit(profit);
    }

    private void addRemoveButtonToTable() {
        TableColumn<Product, Void> remCol = new TableColumn("X");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button revBtn = new Button("X");
                    {
                        revBtn.setOnAction((ActionEvent event) -> {
                            storeCommand.removeProductFromStore(getTableView().getItems().get(getIndex()).getSerialNumber());
                            // TODO: THE CALL TO REMOVE FUNCTION HERE !!!!
                            setProductInListView();
                            view.ACTION_TAKEN = true;
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(revBtn);
                    }
                };
                return cell;
            }
        };
        remCol.setCellFactory(cellFactory);
        productList.getListView().getColumns().add(remCol);
    }

    private void eventSearchButton() {
        EventHandler<ActionEvent> eventForSearchButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                try {
                    String searchValue = productList.getSearchValue();
                    if (searchValue.isEmpty()) {
                        setProductInListView();
//                        throw new IllegalInputException("Empty");
                    }

                    productList.getListView().getItems().clear();

                    Product res = storeCommand.getProductFromStore(searchValue);
                    if (res == null)
                        throw new IllegalInputException("Not found"); // todo: msg

                    productList.getListView().getItems().add(res);
                } catch (IllegalInputException i) {
                    setProductInListView();
                    i.showErrorMessage();
                } catch (Exception e) {
                    // todo: msg
                }
            }
        };
        productList.addEventSearchButton(eventForSearchButton);
    }
}
