package Controller;

import Model.Product;
import Model.command.StoreCommand;
import View.HomeScreen;
import View.ProductList;

public class ProductListController extends SecondaryWindowController {
    private ProductList productList;

    public ProductListController(HomeScreen homeScreenView, StoreCommand storeCommand, ProductList productList) {
        super(homeScreenView, storeCommand, productList);
        this.productList = productList;
        setProductInListView();
    }

    private void setProductInListView() {
        for (Product p : storeCommand.getAllProductsFromStore())
             productList.getListView().getItems().add(p);
    }
}
