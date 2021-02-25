package Controller;

import Exceptions.IllegalInputException;
import Model.Product;
import Model.command.GetAllProductsCommand;
import Model.command.GetProductCommand;
import Model.command.RemoveProductCommand;
import Model.command.Store;
import View.HomeScreen;
import View.ProductList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductListController extends SecondaryWindowController {
    private ProductList productList;
    private Stage quickStage;
    private Scene quickShowScene;
    private VBox quickVBox;
    private double profit;

    public ProductListController(HomeScreen homeScreenView, Store store, ProductList productList) {
        super(homeScreenView, store, productList);
        this.productList = productList;

        setProductInListView();
        addRemoveButtonToTable();
        eventSearchButton();
        eventProductShow();
        eventForBackToMainButton();
    }

    private void setProductInListView() {
        productList.getTableView().getItems().clear();
        profit = 0;
        GetAllProductsCommand allProductsCommand = new GetAllProductsCommand(store);
        allProductsCommand.execute();

        for (Product p : allProductsCommand.get()) {
            productList.getTableView().getItems().add(p);
            profit += p.getRetailPrice() - p.getWholesalePrice();
        }
        setProfit();
    }

    private void setProfit() {
        productList.setProfit(profit);
    }

    private void addRemoveButtonToTable() {
        TableColumn<Product, Void> remCol = new TableColumn("X");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button revBtn = new Button("");
                    {
                        revBtn.setOnAction((ActionEvent event) -> {
                            closeQuick();
//                            storeCommand.removeProductFromStore(storeCommand.removeProductFromStore());
                            new RemoveProductCommand(store, getTableView().getItems().get(getIndex()).getSerialNumber()).execute();
                            setProductInListView();
                            HomeScreen.ACTION_TAKEN = true;
                        });
                        revBtn.getStyleClass().add("button-remove");
                        setStyle("-fx-alignment: center;");
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
            }
        };
        remCol.setCellFactory(cellFactory);
        productList.getTableView().getColumns().add(remCol);
    }

    private void eventSearchButton() {
        EventHandler<ActionEvent> eventForSearchButton = event -> {
            try {
                closeQuick();

                String searchValue = productList.getSearchValue();
                if (searchValue.isEmpty()) {
                    setProductInListView();
                    throw new NullPointerException();
                }

                productList.getTableView().getItems().clear();

                GetProductCommand getProductCommand = new GetProductCommand(store, searchValue);
                getProductCommand.execute();

                Product res = getProductCommand.get();
//                Product res = storeCommand.getProductFromStore();
                if (res == null)
                    throw new IllegalInputException("Not found"); // todo: msg

                productList.getTableView().getItems().add(res);
            } catch (IllegalInputException i) {
                setProductInListView();
                i.showErrorMessage();
            } catch (NullPointerException npe) {
                // Nothing
            } catch (Exception e) {
                // todo: msg
            }
        };
        productList.addEventSearchButton(eventForSearchButton);
    }

    private void eventProductShow() {
        productList.getTableView().setRowFactory(tr -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                closeQuick();

                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    quickVBox = productList.productShowDialog(rowData);

                    quickStage = new Stage();
                    quickStage.setTitle("Quick Show");
                    quickShowScene = new Scene(quickVBox);
                    quickShowScene.getStylesheets().add(view.css);

                    quickStage.setScene(quickShowScene);
                    quickStage.setResizable(false);
                    quickStage.show();

                    eventQuickCloseButton();
                }
            });
            return row ;
        });
    }

    private void eventQuickCloseButton() {
        EventHandler<ActionEvent> eventForQuickCloseButton = event -> closeQuick();
        productList.addEventToQuickCloseButton(eventForQuickCloseButton);
    }

    private void closeQuick() {
        if (quickStage != null)
            quickStage.close();
    }

    private void eventForBackToMainButton() {
        EventHandler<ActionEvent> eventForBackToMainButton = (e -> {
            closeQuick();
            view.loadMain();
        });
        productList.eventBackToMainButton(eventForBackToMainButton);
    }
}
