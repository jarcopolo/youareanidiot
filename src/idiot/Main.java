package idiot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;
    final static double SCENE_WIDTH=800, SCENE_HEIGHT=600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        mainStage = primaryStage;
        primaryStage.setTitle("You are an idiot!");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.show();
        //new Controller().startFun();

    }

    Stage getMainStage()
    {
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
