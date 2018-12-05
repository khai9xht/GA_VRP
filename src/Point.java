public class Point {
    //
    private int x;
    private int y;
    public Point() {}

    //constructor of Point
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getter and setter for x, y
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

    /**
     * Calculate the distance between two points
     * @param A Point
     * @return Distance between this and A
     */
    public int distance(Point A){
        return (int) Math.sqrt((this.x - A.x)*(this.x - A.x) + (this.y - A.y)*(this.y - A.y));
    }

    public static boolean cut(Point A1, Point A2, Point B1, Point B2){
        int m = ((A1.y - A2.y)*(B1.x - A1.x) - (A1.x - A2.x)*(B1.y - A1.y))*((A1.y - A2.y)*(B2.x - A1.x) - (A1.x - A2.x)*(B2.y - A1.y));
        int n = ((B1.y - B2.y)*(A1.x - B1.x) - (B1.x - B2.x)*(A1.y - B1.y))*((B1.y - B2.y)*(A2.x - B1.x) - (B1.x - B2.x)*(A2.y - B1.y));
        return m < 0 && n < 0;
    }
}
