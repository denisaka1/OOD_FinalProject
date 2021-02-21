package View;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductList extends SecondaryWindow {
    // Show all products
    // Search product by sku
    // Order by
    private ListView listView;
    private HBox listViewHBox, backToMainHBox;

    public ProductList() {
        listView = new ListView();
        assignProductList();
    }

    private void assignProductList() {
        listViewHBox = new HBox();
        listViewHBox.getChildren().add(listView);
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();
        backToMainHBox = new HBox();
        backToMainHBox.getChildren().add(backToMainVBox);
        mainView.getChildren().addAll(listViewHBox, backToMainHBox);

        return mainView;
    }
}
