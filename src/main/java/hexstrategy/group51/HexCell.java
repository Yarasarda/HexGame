package hexstrategy.group51;

import javafx.scene.shape.Polygon;

public class HexCell extends Polygon {
    public HexCell(double size) {
        super(createHexPoints(size));
    }

    //altıgenin noktalarını oluşturur
    private static double[] createHexPoints(double size) {
        double[] points = new double[12];
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 6 + Math.PI / 3 * i; //altıgenin pozisyonunu ayarlamak için. (şuan sivri uç yukarda)
            points[i * 2] = size * Math.cos(angle);
            points[i * 2 + 1] = size * Math.sin(angle);
        }
        return points;
    }
}
