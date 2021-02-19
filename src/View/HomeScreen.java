package View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeScreen extends MainButtons {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    private BorderPane borderPane;

    public HomeScreen(Stage stage) {
        super();
        stage.setTitle("Store");
        borderPane = new BorderPane();
        borderPane.setCenter(loadMainButtons());

        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void loadMain() {
        borderPane.getChildren().clear();
        borderPane.setCenter(loadMainButtons());
    }

    public void update(SecondaryWindow newMainView) {
        borderPane.getChildren().clear();
        borderPane.setCenter(newMainView.update());
    }
}
