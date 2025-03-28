package hexstrategy.group51;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class HexGame extends Application {

    private ComboBox<Integer> sizeCB;
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        gameController = new GameController();

        //boyut seç
        sizeCB = new ComboBox<>();
        for (int i = 5; i <= 17; i++)  // Maksimum 17 | istenirse arttırılabilir fakat ekrana sığmama yada ortalı olmama gibi problemler yaşatabiliyor.
            sizeCB.getItems().add(i);

        sizeCB.setValue(11);
        sizeCB.setStyle("-fx-font-size: 16px;");

        //start butonu
        Button startButton = new Button("Oyunu Başlat");
        startButton.setOnAction(e -> gameController.startGame(sizeCB.getValue(), primaryStage));
        startButton.setStyle("-fx-font-size: 16px;");

        //buton ve cbox ortalama
        VBox controls = new VBox(15, sizeCB, startButton);
        controls.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(controls);
        Scene controlScene = new Scene(stackPane, 300, 200);
        primaryStage.setScene(controlScene);
        primaryStage.setTitle("Hex Game | Group 51");
        primaryStage.show();
    }
}
