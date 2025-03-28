package hexstrategy.group51;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane; // Import Pane
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class UIController {

    //swap rule gösterir-seçtirir
    public static void showSwapOption(GameController gameController, int row, int col, Pane root) {
        VBox swapBox = new VBox(10);
        swapBox.setAlignment(Pos.CENTER);
        swapBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 20px; -fx-border-color: black; -fx-border-width: 2px;");

        Label swapLabel = new Label("Swap yapmak ister misiniz?");
        swapLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button yesButton = new Button("Evet (～￣▽￣)～"); //:D
        Button noButton = new Button("Hayır ＞︿＜");     //:<

        yesButton.setOnAction(e -> {
            gameController.setSwapUsed(true);//swap yapılırsa sıra kırmızıda
            swapBox.setVisible(false);
            gameController.usedSwapRule();
        });

        noButton.setOnAction(e -> {
            swapBox.setVisible(false);
            gameController.setSwapUsed(false);
            gameController.notSwapRule(); //swap yapılmazsa sıra mavide
            gameController.updateLabels();
        });

        swapBox.getChildren().addAll(swapLabel, yesButton, noButton);
        root.getChildren().add(swapBox);
        swapBox.setLayoutX(root.getWidth() / 2 - 150);
        swapBox.setLayoutY(root.getHeight() / 2 - 75);
    }


    public static void showWinnerScreen(String winner) {
        Stage winnerStage = new Stage();
        StackPane winnerRoot = new StackPane();
        Scene winnerScene = new Scene(winnerRoot, 400, 200);
        winnerStage.setScene(winnerScene);
        winnerStage.setTitle("Kazanan");

        Label winnerLabel = new Label(winner + " Kazandı!");
        winnerLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        winnerRoot.getChildren().add(winnerLabel);
        StackPane.setAlignment(winnerLabel, Pos.CENTER);

        //|----------------------------------------------------------------------------------------|
        //|                     kazanan ekranının sürekli renk değiştirmesini sağlar.              |
        //|       !!! Eğer IŞIĞA DAYALI NÖBETLER geçiriyorsanız lütfen bu animasyonu kapatın !!!   |
        //|----------------------------------------------------------------------------------------|

        Timeline colorChangeTimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), event -> {
            Random rand = new Random();
            Color randomColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            winnerRoot.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(randomColor, javafx.scene.layout.CornerRadii.EMPTY, Insets.EMPTY)));
        }));
        colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
        colorChangeTimeline.play();

        winnerStage.show();
    }
}
