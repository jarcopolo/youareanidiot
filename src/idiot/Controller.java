package idiot;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class Controller {

    public Pane mainPane;
    private static Stage mainStage;
    private String blackBgStyle="-fx-background-image: url('idiot/img/idiot-black.png')";
    private String whiteBgStyle="-fx-background-image: url('idiot/img/idiot-white.png')";

    public void initialize() {
        new Thread(new BackgroundChanger()).start();
        new Thread(new StageMover()).start();

    }




    class BackgroundChanger implements Runnable
    {
        boolean bgIsBlack=true;

        public void run()
        {
            for(;;)
            {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(bgIsBlack)
                {
                    mainPane.setStyle(whiteBgStyle);
                }
                else
                {
                    mainPane.setStyle(blackBgStyle);
                }
                bgIsBlack^=true;
            }
        }
    }

    class StageMover implements Runnable
    {
        private double rightXborder, lowYborder, screenWidth, screenHeight;
        private final int MOVE_VALUE = 20;
        private boolean moveXright=true,moveYdown=true;

        public void run() {
            mainStage = new Main().getMainStage();
            screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();


            for (;;)
            {

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() ->
                {
                    calculate();
                    if(moveXright)
                    {
                        mainStage.setX(mainStage.getX() + MOVE_VALUE);
                    }
                    else
                    {
                        mainStage.setX(mainStage.getX() - MOVE_VALUE);
                    }

                    if(moveYdown)
                    {
                        mainStage.setY(mainStage.getY() + MOVE_VALUE);
                    }
                    else
                    {
                        mainStage.setY(mainStage.getY() - MOVE_VALUE);
                    }

                });


            }
        }

        void calculate()
        {
            rightXborder=mainStage.getX()+mainStage.getWidth();
            if(rightXborder>screenWidth)
            {
                moveXright=false;
            }
            if(mainStage.getX()<0)
            {
                moveXright=true;
            }
            lowYborder=mainStage.getY()+mainStage.getHeight();
            if(lowYborder>screenHeight)
            {
                moveYdown=false;
            }
            if(mainStage.getY()<0)
            {
                moveYdown=true;
            }
        }


    }
}