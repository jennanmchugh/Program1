package Program1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    ChoiceBox fillAlgorithms;
    @FXML
    TextField x1,x2,x3,y1,y2,y3, seedX, seedY;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Button run;
    @FXML
    Canvas canvas1;

    WritableImage image;
    PixelWriter pixelWriter;
    List<Edge> edges = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void setData() {
        List<String> algorithms = new ArrayList<>();
        algorithms.add("Flood Fill 4");
        algorithms.add("Flood Fill 8");
        algorithms.add("Polygon Fill");
        ObservableList observableList = FXCollections.observableList(algorithms);
        fillAlgorithms.setItems(observableList);
    }

    @FXML
    private void buttonSubmit() {
        List<Point.Double> points = getPolygonPoints();
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        drawPolygon(gc, points);
        Color fillcolor = this.colorPicker.getValue();
        String alg = this.fillAlgorithms.getSelectionModel().getSelectedItem().toString();

        if (alg.equals("Flood Fill 4")) {
            int seedX = Integer.parseInt(this.seedX.getText());
            int seedY = Integer.parseInt(this.seedY.getText());
            Color currentColor = image.getPixelReader().getColor(seedX, seedY);
            floodFill4(seedX, seedY, fillcolor, currentColor);
        }
        if (alg.equals("Flood Fill 8")) {
            int seedX = Integer.parseInt(this.seedX.getText());
            int seedY = Integer.parseInt(this.seedY.getText());
            Color currentColor = image.getPixelReader().getColor(seedX, seedY);
            floodFill8(seedX,seedY,fillcolor,currentColor);
        }
        if (alg.equals("Polygon Fill")) {

        }
    }


    private void drawPolygon(GraphicsContext graphicsContext, List<Point.Double> points) {
        int nPoints = points.size();
        double[] xPoints = new double[points.size()];
        double[] yPoints = new double[points.size()];
        for (int i=0; i<xPoints.length; i++) {
            xPoints[i] = points.get(i).getX();
        }
        for (int i=0; i<yPoints.length; i++) {
            yPoints[i] = points.get(i).getY();
        }

        graphicsContext.strokePolygon(xPoints, yPoints, nPoints);
        image = canvas1.snapshot(null, null);
    }

    private List<Point.Double> getPolygonPoints() {
        List<Point.Double> points = new ArrayList<>();
        int x1 = Integer.parseInt(this.x1.getText());
        int y1 = Integer.parseInt(this.y1.getText());
        points.add(new Point.Double(x1, y1));
        int x2 = Integer.parseInt(this.x2.getText());
        int y2 = Integer.parseInt(this.y2.getText());
        points.add(new Point.Double(x2, y2));
        int x3 = Integer.parseInt(this.x3.getText());
        int y3 = Integer.parseInt(this.y3.getText());
        points.add(new Point.Double(x3, y3));
        edges.add(new Edge(0, x1, y1, x2, y2));
        edges.add(new Edge(1, x2, y2, x3, y3));
        return points;
    }
    private void floodFill4(int x, int y, Color fillColor, Color interiorColor) throws StackOverflowError {
        pixelWriter = canvas1.getGraphicsContext2D().getPixelWriter();
        Color color;
        color = image.getPixelReader().getColor(x, y);
        if (color.equals(interiorColor)) {
            pixelWriter.setColor(x, y, fillColor);
            floodFill4(x+1,y,fillColor,interiorColor);
            floodFill4(x-1,y,fillColor,interiorColor);
            floodFill4(x,y+1,fillColor,interiorColor);
            floodFill4(x,y-1,fillColor,interiorColor);
        }
    }

    private void floodFill8(int x, int y, Color fillColor, Color interiorColor) throws StackOverflowError {
        Color color;
        color = image.getPixelReader().getColor(x,y);
        if (color.equals(interiorColor)) {
            pixelWriter = canvas1.getGraphicsContext2D().getPixelWriter();
            pixelWriter.setColor(x,y,fillColor);
            floodFill8(x-1,y,fillColor,interiorColor);
            floodFill8(x+1,y,fillColor,interiorColor);
            floodFill8(x,y-1,fillColor,interiorColor);
            floodFill8(x,y+1,fillColor, interiorColor);
            floodFill8(x+1,y+1,fillColor,interiorColor);
            floodFill8(x-1,y+1,fillColor,interiorColor);
            floodFill8(x-1,y-1,fillColor,interiorColor);
            floodFill8(x+1,y-1,fillColor,interiorColor);
        }
    }

}
