package sample;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * @author Paulous
 * Controller class to handle requests from the view which is sample.fxml
 * Logic for internet checking connectivity is in this class
 */



public class Controller implements Initializable {

    public Label xamppConnector;
    public Label internetConnection;
    public Button retry;
    private Connection con;
    private ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();
    private boolean stopThread = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            con = DatabaseClassConnectForXampp.getCon();



        int check = checkOnline();
        if (check == 0) {
            internetConnection.setText("Connected to Google successfully");
        }

        if (con == null){
            xamppConnector.setText("We are not connect to the  xampp local server");
            retry.setVisible(true);

        } else {
            xamppConnector.setText("We are connect to the  xampp local server");
            retry.setVisible(false);

        }



    }



   public void onTry(){
        if (con == null) {


            boolean answer = AlertMaker.retry_Connection();

            if (answer) {
                con = DatabaseClassConnectForXampp.getCon();
                //Run the threads

                threadRun();
                onTry();
            }
            else
                System.out.println("Don't try i guess");
        }
    }

    private void threadRun() {
        Runnable runnable = () -> {

        };
        if (con != null) {
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            xamppConnector.setText("Connection Established");
            xamppConnector.setStyle("-fx-text-fill: #0f0; -fx-font-size: 24px;");
            retry.setVisible(true);

            final ScheduledFuture updateLog = service.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.SECONDS);
            service.schedule(() -> {
                updateLog.cancel(true);
                System.out.println("Connection Created");


                service.shutdown();
                System.out.println(service.isShutdown());
            }, 9, TimeUnit.SECONDS);
        } else {
            onTry();
        }


    }

    private int checkOnline(){
        int returnValue = 1;


        try {
            Process process = Runtime.getRuntime().exec("ping www.google.com");
            int x = process.waitFor();

            if (x == 0) {
                System.out.println("Connection Successful, "
                        + "Output was " + x);
                returnValue = 0;
                checkOnlinePresence();
            } else {
                System.out.println("Internet Not Connected, "
                        + "Output was " + x);
                returnValue =1;
                checkOnlinePresence();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }


    //Let's try to do an auto connection here

    private void checkOnlinePresence(){



        Runnable periodicTask = () -> {


        };

        executor.scheduleAtFixedRate(periodicTask, 5, 5, TimeUnit.SECONDS);
        executor.schedule(() -> {


                if (checkOnline() == 0) {
                    callMaybe("Connected to Google successfully");
                    System.out.println("Am still working?");
                } else {
                    callMaybe("Not connected to Google successfully");
                    System.out.println("Am still working?1");

                }
            if (stopThread){
                executor.shutdown();
                System.exit(0);
            }

        }, 5, TimeUnit.SECONDS);


    }

    /**
     * This method runs the Platform thread to upaate the check of internet connect every time connection to the web is made.
     * @param messageHere
     * receives a parameter called messageHere from the calling method
     * Without Platform.runLater you can't update JavaFX contents dynamically in a situation a thread is being used.
     * Currently using Lambda function which comes with java 8
     */
    private void callMaybe(String messageHere){
        final String callMeMaybeString = messageHere;
                Platform.runLater(
                () -> internetConnection.setText(callMeMaybeString)

        );
    }

    /**
     * This method is used to kill thread before the application closes to release all the resources being used
     */

    boolean killTheThreads(){
        executor.shutdown();
        stopThread = true;


        return executor.isShutdown();
    }


}
