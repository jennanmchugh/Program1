package Program1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    ChoiceBox fillAlgorithms;
    @FXML
    TextField x1,x2,x3,x4,x5,y1,y2,y3,y4,y5,seedX, seedY;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Button run;
    @FXML
    Canvas canvas1;

    private WritableImage image;
    private PixelWriter pixelWriter;
    private List<Edge> edges = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pixelWriter = canvas1.getGraphicsContext2D().getPixelWriter();

    }

    public void setData() {
        List<String> algorithms = new ArrayList<>();
        algorithms.add("Flood Fill 4");
        algorithms.add("Flood Fill 8");
        algorithms.add("Polygon Fill");
        ObservableList observableList = FXCollections.observableList(algorithms);
        fillAlgorithms.setItems(observableList);
    }

    @FXML
    private void drawPolygon() {
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        List<Point.Double> points = getPolygonPoints();
        int nPoints = points.size();
        double[] xPoints = new double[points.size()];
        double[] yPoints = new double[points.size()];
        for (int i=0; i<xPoints.length; i++) {
            xPoints[i] = points.get(i).getX();
        }
        for (int i=0; i<yPoints.length; i++) {
            yPoints[i] = points.get(i).getY();
        }
        //build edge list for use in polygon fill
        for (int i=0;i<=points.size()-1; i++) {
            if (i == points.size()-1) {
                edges.add(new Edge(i, (int)points.get(i).x, (int)points.get(i).y, (int)points.get(0).x, (int)points.get(0).y));
            }
            else {
                edges.add(new Edge(i, (int)points.get(i).x, (int)points.get(i).y, (int)points.get(i+1).x, (int)points.get(i+1).y));
            }
        }
        gc.strokePolygon(xPoints, yPoints, nPoints);
        image = canvas1.snapshot(null, null);
    }

    @FXML
    private void runFill() {
        Color fillcolor = this.colorPicker.getValue();
        String alg = this.fillAlgorithms.getSelectionModel().getSelectedItem().toString();

        if (alg.equals("Flood Fill 4")) {
            int seedX = Integer.parseInt(this.seedX.getText());
            int seedY = Integer.parseInt(this.seedY.getText());
            Color currentColor = image.getPixelReader().getColor(seedX, seedY);
            boolean[][] visited = new boolean[(int)image.getWidth()][(int)image.getHeight()];
            floodFill4(seedX, seedY, visited, fillcolor, currentColor);
        }
        if (alg.equals("Flood Fill 8")) {
            int seedX = Integer.parseInt(this.seedX.getText());
            int seedY = Integer.parseInt(this.seedY.getText());
            Color currentColor = image.getPixelReader().getColor(seedX, seedY);
            boolean[][] visited = new boolean[(int)image.getWidth()][(int)image.getHeight()];
            floodFill8(seedX,seedY, visited, fillcolor,currentColor);
        }
        if (alg.equals("Polygon Fill")) {
            polygonFill();
        }
    }

    private List<Point.Double> getPolygonPoints() {
        int x4Point, y4Point;
        List<Point.Double> points = new ArrayList<>();
        int x1Point = Integer.parseInt(this.x1.getText());
        int y1Point = Integer.parseInt(this.y1.getText());
        points.add(new Point.Double(x1Point, y1Point));
        int x2Point = Integer.parseInt(this.x2.getText());
        int y2Point = Integer.parseInt(this.y2.getText());
        points.add(new Point.Double(x2Point, y2Point));
        int x3Point = Integer.parseInt(this.x3.getText());
        int y3Point = Integer.parseInt(this.y3.getText());
        points.add(new Point.Double(x3Point, y3Point));
        if (!x4.getText().isEmpty() && !y4.getText().isEmpty()) {
            x4Point = Integer.parseInt(this.x4.getText());
            y4Point = Integer.parseInt(this.y4.getText());
            points.add(new Point.Double(x4Point,y4Point));
        }
        if (!x5.getText().isEmpty() && !y5.getText().isEmpty()) {
            int x5 = Integer.parseInt(this.x5.getText());
            int y5 = Integer.parseInt(this.y5.getText());
            points.add(new Point.Double(x5, y5));
        }

        return points;
    }


    private void floodFill4(int x, int y, boolean[][] visited, Color fillColor, Color interiorColor) {
        if (interiorColor.equals(fillColor)) { return; }
        if (!interiorColor.equals(image.getPixelReader().getColor(x,y))) { return; }
        if (visited[x][y]) { return; }
        pixelWriter.setColor(x,y,fillColor);
        visited[x][y] = true;
        floodFill4(x,y-1, visited, fillColor,interiorColor);
        floodFill4(x,y+1, visited, fillColor,interiorColor);
        floodFill4(x-1,y, visited, fillColor,interiorColor);
        floodFill4(x+1,y, visited, fillColor,interiorColor);
    }

    private void floodFill8(int x, int y, boolean[][] visited, Color fillColor, Color interiorColor) {
        if (interiorColor.equals(fillColor)) { return; }
        if (!interiorColor.equals(image.getPixelReader().getColor(x,y))) { return; }
        if (visited[x][y]) { return; }
        pixelWriter.setColor(x,y,fillColor);
        visited[x][y] = true;
        floodFill8(x-1,y, visited, fillColor,interiorColor);
        floodFill8(x+1,y,visited, fillColor, interiorColor);
        floodFill8(x,y-1, visited, fillColor,interiorColor);
        floodFill8(x,y+1, visited, fillColor, interiorColor);
        floodFill8(x+1,y+1, visited, fillColor,interiorColor);
        floodFill8(x-1,y+1, visited, fillColor,interiorColor);
        floodFill8(x-1,y-1, visited, fillColor,interiorColor);
        floodFill8(x+1,y-1, visited, fillColor,interiorColor);

    }

    private void polygonFill() {
        List<Point> vertices = new ArrayList<>();
        List yPoints = new ArrayList();
        for (Edge e : edges) {
            yPoints.add(e.y1);
            yPoints.add(e.y2);
            if (!vertices.contains(new Point(e.x1, e.y1))) {
                vertices.add(new Point(e.x1, e.y1));
            }
            if (!vertices.contains(new Point(e.x2, e.y2))) {
                vertices.add(new Point(e.x2, e.y2));
            }
            int yMin = (int) yPoints.get(0);
            int yMax = (int) yPoints.get(0);
            for (int i = 0; i <yPoints.size()-1; i++) {
                int current = (int)yPoints.get(i);
                if (current > yMax) {
                    yMax = current;
                }
                else if (current < yMin) {
                    yMin = current;
                }
            }
        }

    }

}
