package idiot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage parentStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        parentStage.setScene(new Scene(new Pane(), 1, 1));
        parentStage.initStyle(StageStyle.UTILITY);
        //parentStage.setOpacity(0);
        //parentStage.show();
        //new Controller().startFun();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
