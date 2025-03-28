package hexstrategy.group51;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class HexBoard {
    private int size;
    private Color[][] board;
    private GameController gameController;

    public HexBoard(int size, GameController gameController) {
        this.size = size;
        this.board = new Color[size][size];
        this.gameController = gameController;
    }

    public void createHexMap(Pane root) {
        double cellSize = 30; // * 1 keyfi bir değişiklik. daha sonra altıgenlerin boyutu değiştirilmek istenirse bunu o yüzdede değiştirin.
        double offsetX = cellSize * 1.9; //altıgenlerin yataydaki arasındaki mesafe. bitişik istenirse 1.7 (projede de mesafe var diye boşluk bıraktım. -arda)
        double offsetY = cellSize * 1.67; //aynı şekilde bu da dikeydeki mesafe. bitişik istenirse 1.5
        //1.9 ve 1.67 de deneyerek bulunmuş değerler ekstra bir hesabı yok

        //altıgenleri temizle
        root.getChildren().removeIf(node -> node instanceof Polygon);
        //oluşacak eşkenar dörthenin genişlik ve yüksekliği
        double rectWidth = size * offsetX;
        double rectHeight = size * offsetY;
        double startX = (root.getWidth() - rectWidth) / 2 - 85;//buradaki 85 deneyerek bulundu yatayda ortalamayı çözemediğim için sağ sola kaydırarak buldum. 85 değiştirilerek sola ve sağa kaydırılabilir -arda
        double startY = (root.getHeight() - rectHeight) / 2 + offsetY / 2;//yüksekliği ortalama

        //hex map oluşturma döngüsü
        for (int row = 0; row < size; row++) {
            double rowOffsetX = row * offsetX / 2; //her satırda bir öncekinden sağa kayacak (altıgenin genişliğinin yarısı)
            for (int col = 0; col < size; col++) {
                HexCell hexagon = new HexCell(cellSize);
                hexagon.setFill(Color.WHITE); //ana iç renk
                hexagon.setStroke(Color.BLACK); //dış çizgi rengi
                //pozisyon ayarları
                double x = startX + col * offsetX + rowOffsetX;
                double y = startY + row * offsetY - row;
                hexagon.setTranslateX(x);
                hexagon.setTranslateY(y);

                //final yapmayınca olmuyordu IDE önerisi. :D
                int finalRow = row;
                int finalCol = col;
                hexagon.setOnMouseClicked(event -> gameController.handleHexClick(finalRow, finalCol, hexagon));
                root.getChildren().addAll(hexagon);
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == null;
    }

    public void setColor(int row, int col, Color color) {
        board[row][col] = color;
    }

    //kazanan kontrolü
    public boolean checkWin(Color playerColor) {
        int reverse=(playerColor==Color.RED)?1:2;
        boolean result=true;
        int way=3;
        int[][] hexgame=new int[size][size];
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if(board[i][j]==Color.RED){
                    hexgame[i][j]=2;
                }
                else if(board[i][j]==Color.BLUE){
                    hexgame[i][j]=1;
                }
                else{
                    hexgame[i][j]=reverse;
                }
            }
        }//diziyi 2 boyutlu int diziye atar

        if(reverse==1){
            for (int i=0;i<size;i++){
                if(hexgame[i][0]==reverse){hexgame[i][0]=way;}
            }//ilk sütundaki belirli renge way değerini atar
        }
        else{
            for (int i=0;i<size;i++){
                if(hexgame[0][i]==reverse){hexgame[0][i]=way;}
            }//ilk satırdaki belirli renge way değerini atar
        }

        if(reverse==1){
            for (int j=1;j<size;j++,way++){
                for (int i=0;i<size;i++){
                    if(i==size-1){if(hexgame[i][j]==reverse && hexgame[i][j-1]==way){hexgame[i][j]=way+1;}}
                    else{if(hexgame[i][j]==reverse&&(hexgame[i][j-1]==way||hexgame[i+1][j-1]==way)){hexgame[i][j]=way+1;}}
                }
            }//değer eğer doğru renkse ve arkasında bir önceki yol değeri varsa bir fazlası bu yola atanır
        }
        else{
            for (int i=1;i<size;i++,way++){
                for (int j=0;j<size;j++){
                    if(j==size-1){if(hexgame[i][j]==reverse && hexgame[i-1][j]==way){hexgame[i][j]=way+1;}}
                    else{if(hexgame[i][j]==reverse&&(hexgame[i-1][j]==way||hexgame[i-1][j+1]==way)){hexgame[i][j]=way+1;}}
                }
            }//değer eğer doğru renkse ve arkasında bir önceki yol değeri varsa bir fazlası bu yola atanır
        }

        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if( hexgame[i][j]==size+3-1){ result=false;}
            }
        }//eğer ilerleyiş karşı duvara ulaştıysa false ulaşamadıysa true değerini döndürür
        return result;
    }
}
