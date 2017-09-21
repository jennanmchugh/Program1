package Program1;

public class Edge {
    int edgeNumber;
    int x1;
    int y1;
    int x2;
    int y2;
    int maxY;
    int minY;

    public Edge(int edgeNumber, int x1, int y1, int x2, int y2) {
        this.edgeNumber = edgeNumber;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }
}
