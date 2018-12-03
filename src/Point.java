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

    public boolean cut(Point A1, Point A2, Point B1, Point B2){

        return true;
    }
}
