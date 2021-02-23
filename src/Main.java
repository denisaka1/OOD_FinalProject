import Controller.StoreController;
import Model.command.StoreCommand;
import javafx.application.Application;
import javafx.stage.Stage;
import View.HomeScreen;

public class Main extends Application {

    private final String filename = "data/products.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StoreCommand storeCommand = new StoreCommand(filename);
        HomeScreen view = new HomeScreen(stage);
        StoreController controller = new StoreController(view, storeCommand);
    }
}