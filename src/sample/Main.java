package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    Controller_RootStage rootStageController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layout_RootStage.fxml"));
        Parent root = loader.load();
        rootStageController = loader.getController();

        primaryStage.setTitle("LaborApp");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("imgs/check_mark.png")));
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
