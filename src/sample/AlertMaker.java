package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AlertMaker {

    private static boolean answer;
    static boolean retry_Connection(){
        answer =gridPaneDialog("Connection Error","Sorry No Internet  Connection, Retry?");

        return answer;
    }

    //DIALOG
    public static boolean gridPaneDialog(String title, String message){

        Stage window = new Stage();
        //this blocks any events from the background windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #009999;");
        Label label1 = new Label(message);
        label1.setStyle("-fx-font-size: 18px;");


        Button yesButton = new Button("YES");
        yesButton.setStyle("-fx-background-color: #16840e;");
        grid.add(yesButton,0,1);


        Button noButton = new Button("NO");
        noButton.setStyle("-fx-background-color: #b90f0f;");


        grid.add(noButton,1,1);

        yesButton.setOnAction(e-> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e-> {
            answer = false;
            window.close();
        });
        layout.getChildren().addAll(label1,grid);
        Scene scene = new Scene(layout);
        window.setResizable(false);
//        EmploymentUtil.setStageIcon(window);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
