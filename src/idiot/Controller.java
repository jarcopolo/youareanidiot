package idiot;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.Random;

public class Controller {

    private String blackBgStyle="-fx-background-image: url('idiot/img/idiot-black.png'); -fx-background-size: "+SCENE_WIDTH+" "+ SCENE_HEIGHT+";";
    //private String blackBgStyle="-fx-background-image: url('idiot/img/idiot-black.png'); -fx-background-size: cover;";
    private String whiteBgStyle="-fx-background-image: url('idiot/img/idiot-white.png'); -fx-background-size: cover;";

    final static double SCENE_WIDTH=300, SCENE_HEIGHT=230; //pixels
    private final int MAX_MOVE_VALUE = 20; //pixels
    private final int BGCHANGER_SLEEP_DURATION = 800; //millis
    private final int MOVER_SLEEP_DURATION = 50; //millis

    private double rightXborder, lowYborder, screenWidth, screenHeight;
    private static Stage parentStage;

    public void initialize() {

        createParentStage();
        //for (int i=0;i<15;i++)
        createNewWindow();

    }

    //creates invisible parent stage
    private void createParentStage()
    {
        parentStage = new Stage();
        parentStage.setScene(new Scene(new Pane(), 1, 1));
        parentStage.initStyle(StageStyle.UTILITY);
        parentStage.setOpacity(0);
        parentStage.show();
    }

    //create new window and start needed threads
    private void createNewWindow()
    {
        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.setStyle(blackBgStyle);

        stage.initOwner(parentStage);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(pane,SCENE_WIDTH,SCENE_HEIGHT));
        //stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();


        //start background-changing thread for new window
        new Thread(new BackgroundChanger(pane)).start();
        //start stage-moving thread for new window
        new Thread(new StageMover(stage)).start();


        //prevent window from being closed
        stage.setOnCloseRequest(event -> {
            event.consume();

            //and also start a new one :-)
            createNewWindow();
        });

        //start new window if clicked :-)
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createNewWindow();
            }
        });

    }


    class BackgroundChanger implements Runnable
    {
        BackgroundChanger(Pane pane)
        {
            this.pane=pane;
        }

        private final Pane pane;
        boolean bgIsBlack=true;

        public void run()
        {
            for(;;)
            {
                try {
                    Thread.sleep(BGCHANGER_SLEEP_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(bgIsBlack)
                {
                    pane.setStyle(whiteBgStyle);
                }
                else
                {
                    pane.setStyle(blackBgStyle);
                }
                bgIsBlack^=true;
            }
        }
    }

    class StageMover implements Runnable
    {
        StageMover(Stage stage)
        {
            this.stage = stage;
        }

        private boolean movingRight =true, movingDown =true;
        private Stage stage;
        private int xMoveValue,yMoveValue;


        public void run() {
            screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            xMoveValue = new Random().nextInt(MAX_MOVE_VALUE-5)+5;
            yMoveValue = new Random().nextInt(MAX_MOVE_VALUE-5)+5;


            for (;;)
            {
                try {
                    Thread.sleep(MOVER_SLEEP_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() ->
                {
                    checkAvailableMoves();
                    if(movingRight)
                    {
                        stage.setX(stage.getX() + xMoveValue);
                    }
                    else
                    {
                        stage.setX(stage.getX() - xMoveValue);
                    }

                    if(movingDown)
                    {
                        stage.setY(stage.getY() + yMoveValue);
                    }
                    else
                    {
                        stage.setY(stage.getY() - yMoveValue);
                    }

                });
            }
        }

        void checkAvailableMoves()
        {
            rightXborder=stage.getX()+stage.getWidth();
            if(rightXborder>screenWidth)
            {
                movingRight =false;
            }
            if(stage.getX()<0)
            {
                movingRight =true;
            }
            lowYborder=stage.getY()+stage.getHeight();
            if(lowYborder>screenHeight)
            {
                movingDown =false;
            }
            if(stage.getY()<0)
            {
                movingDown =true;
            }
        }


    }
}