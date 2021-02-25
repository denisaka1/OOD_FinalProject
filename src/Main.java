import Controller.StoreController;
import Model.command.Store;
import javafx.application.Application;
import javafx.stage.Stage;
import View.HomeScreen;

public class Main extends Application {

    public static final String FILENAME = "data/products.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
//        StoreCommand storeCommand = new StoreCommand(filename);
        Store store = new Store();
        HomeScreen view = new HomeScreen(stage);
        StoreController controller = new StoreController(view, store);
//        StoreController controller = new StoreController(view, storeCommand);
    }
}