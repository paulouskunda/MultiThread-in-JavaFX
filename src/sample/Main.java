package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        window.setOnCloseRequest(e-> {
            e.consume();
            closeProgram();
        });
    }

    private void closeProgram(){
        Controller controller = new Controller();
        boolean answer = AlertMaker.gridPaneDialog("title","You sure?");
        if (answer){
            window.close();
            //run a method in the controller to stop the running threads
            if (controller.killTheThreads())
                System.out.println("Killed");
            else
                System.out.println("You can't kill me!! I'm immortal");
        }


    }
    public static void main(String[] args) {
        launch(args);
    }
}
