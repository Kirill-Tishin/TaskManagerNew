package StartApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainVisual extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(MainVisual.class.getResource("/FormInput.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("PojoClass.Task Manager");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}