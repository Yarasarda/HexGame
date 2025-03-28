package hexstrategy.group51;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController {
    private int gridSize; //varsayılan boyut
    private boolean redTurn = true;//sıra
    private int moveCount = 0;//hamle sayacı
    private boolean swapUsed = false;//swap rule kontrol
    private HexBoard hexBoard;
    private Label turnText;//sıra belirten text
    private Label moveCountText;//hamle sayacı text'i
    private Pane gameRoot;//oyun sahnesi
    private HexCell firstHexagon; //ilk hamle yapılan altıgen
    private int firstMoveRow;//ilk hamle satır sayısı
    private int firstMoveCol;//ilk hamle sütun sayısı

    public void startGame(int size, Stage primaryStage) {
        this.gridSize = size;
        Stage gameStage = new Stage();
        gameRoot = new Pane();

        turnText = new Label("Sıra: Kırmızı");
        turnText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        moveCountText = new Label("Hamle Sayısı: 0");
        moveCountText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        //text'leri ortala
        VBox infoBox = new VBox(10, turnText, moveCountText);
        infoBox.setAlignment(Pos.CENTER);
        //text'leri ekle
        gameRoot.getChildren().add(infoBox);
        gameRoot.setStyle("-fx-background-color: transparent;");

        Scene gameScene = new Scene(gameRoot, 800, 600);
        gameStage.setScene(gameScene);
        gameStage.setTitle("Hex Game");
        gameStage.setFullScreen(true); // Tam ekranı ayarla
        gameStage.setFullScreenExitHint("");//burada normalde [Çıkış için "ESC"] yazıyordu fakat uzun kalıyor ve her testte çok bekletiyor diye boş bırakıldı.
        gameStage.show();

        hexBoard = new HexBoard(size, this);
        hexBoard.createHexMap(gameRoot); //seçilen boyutta altıgeni oluşturur

        redTurn = true;
        moveCount = 0;
        swapUsed = false;
        updateLabels();
    }


    //tıklamada boyama vs.
    public void handleHexClick(int row, int col, HexCell hexagon) {
        if (hexBoard.isCellEmpty(row, col)) {
            hexBoard.setColor(row, col, redTurn ? Color.RED : Color.BLUE);


            Color startColor = (Color) hexagon.getFill();
            Color endColor = redTurn ? Color.RED : Color.BLUE;
            //tıklama animasoyunun süresi. Bana kalsa 0'a çekerim çünkü süre uzarsa sıra buglanabiliyor
            FadeTransition colorTransition = new FadeTransition(Duration.millis(450), hexagon);
            colorTransition.setFromValue(0);
            colorTransition.setToValue(1);
            colorTransition.setOnFinished(e -> {
                hexagon.setFill(endColor);
                hexagon.setOpacity(1);
            });
            colorTransition.play();

            if (moveCount == 0) {
                firstMoveRow = row;
                firstMoveCol = col;
                firstHexagon = hexagon;
            }

            moveCount++;
            if (hexBoard.checkWin(redTurn ? Color.RED : Color.BLUE)) {
                UIController.showWinnerScreen(redTurn ? "Kırmızı" : "Mavi");
            } else {
                if (moveCount == 1 && !swapUsed) {
                    UIController.showSwapOption(this, firstMoveRow, firstMoveCol, gameRoot);
                } else {
                    // Sıranın değişmesi gerekiyor
                    redTurn = !redTurn;
                    updateLabels();
                }
            }
        }
    }





    public void usedSwapRule() {
        hexBoard.setColor(firstMoveRow, firstMoveCol, Color.BLUE);
        firstHexagon.setFill(Color.BLUE);
        redTurn = true;//sırayı değiştirir
        updateLabels();
    }

    //normalde böyle bir methodun gerekmemesinı ve fazlalık gibi gözükmesi farkındayım fakat tek methodda buglanıyor ve bazen sıra sürekli aynı renkte kalıyor -arda
    public void notSwapRule(){
        redTurn = !redTurn;//sıra değişmez
    }

    //zaten arkaplan değişiyor ama aynı zamanda text'leri de değiştiren method
    public void updateLabels() {
        turnText.setText("Sıra: " + (redTurn ? "Kırmızı" : "Mavi"));
        moveCountText.setText("Hamle Sayısı: " + moveCount);

        //yarı saydam arkaplan
        gameRoot.setStyle(redTurn ? "-fx-background-color: rgba(255, 0, 0, 0.3);" : "-fx-background-color: rgba(0, 0, 255, 0.3);"); //tam yarı saydam bile değil aslında. Test yaparken göz yormasın diye 0.3'e sabitlendi
    }

    public void setSwapUsed(boolean swapUsed) {
        this.swapUsed = swapUsed;
    }

    public boolean isRedTurn() {
        return redTurn;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
