package Program1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawFill extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group group = new Group();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("CSCI 5631 - Program #1");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
        controller.setData();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
