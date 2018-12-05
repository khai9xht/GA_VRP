import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;

public class Setup_VRP {
    private static int dimension = 0;
    private static int total_transport = 0;
    private static int capacity = 0;
    private String path;
    static int [][] Distance;
    static int[] Demand ;
    static boolean[] PointGone;
    static ArrayList<Point> Array_Point = new ArrayList<>();

    //update value to dimension, total_transport, capacity
    private void updateDimension(String str){
        String dimen = str.substring(str.indexOf("X-n")+3,str.indexOf("-k"));
        dimension = Integer.parseInt(dimen);
    }
    private void updateTotal_transport(String str){
        String trans = str.substring(str.indexOf("-k")+2);
        total_transport = Integer.parseInt(trans);
    }
    private void updateCapacity(String str){
        String path = "CAPACITY\t:\t\t\t";
        String capa = str.substring(str.indexOf(path )+ path.length(),str.indexOf('\t',str.indexOf(path )+ path.length()));
        capacity = Integer.parseInt(capa);
    }

    //add point and Demand to array
    private void addPoint(String str){
        int x1 = str.indexOf("\t");
        int x2 = str.indexOf("\t",x1+1);
        String str1 = str.substring(x1+1,x2);
        String str2 = str.substring(x2+1);
        int x = Integer.parseInt(str1);
        int y = Integer.parseInt(str2);
        Array_Point.add(new Point(x,y));
    }
    private void addDemand(String str,int j){
        int x1 = str.indexOf("\t");
       // int x2 = str.indexOf("\t",x1+1);
        String str1;
        if(str.charAt(str.length()-1) == '\t') str1 = str.substring(x1+1,str.length()-1);
        else str1 = str.substring(x1+1);
        int demand = Integer.parseInt(str1);
        Demand[j] = demand;
    }

    /**
     * Read source file
     * @throws FileNotFoundException catch case that source file isn't found
     */
    private void read_file() throws FileNotFoundException {
        FileReader fileReader = new FileReader(path);
        try (BufferedReader file = new BufferedReader(fileReader)) {
            String str = file.readLine().trim();
            updateDimension(str);
            updateTotal_transport(str);
            for(int i=0;i<5;i++){ str = file.readLine(); }
            updateCapacity(str);

            Distance = new int[dimension][dimension];
            Demand = new int[dimension];
            PointGone = new boolean[dimension];
            for(int i=0;i<dimension;i++){
                PointGone[i] = false;
            }
            PointGone[0] = true;

            str = file.readLine();
            int i=0;
            do {
                str = file.readLine();
                addPoint(str);
                i++;
            }while(i != dimension);

            str = file.readLine();
            int j=0;
            do{
                str = file.readLine();
                addDemand(str,j);
                j++;
            }while(j != dimension);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * make a matrix saves distance between two point in Array_Point
     */
    private void distance(){
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                int dist = Array_Point.get(i).distance(Array_Point.get(j));
                Distance[i][j] = dist;
            }
        }
    }

    /**
     * Constructor of Setup_VRP
     */
    public Setup_VRP(String path) {
        this.path = path;
        try {
            read_file();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        distance();
    }

    public static int get_nearest_random(int j){
        PointGone[j] = true;
        int min1 = MAX_VALUE;
        int min2 = MAX_VALUE;
        int next1 = 0;
        int next2 = 0;
        for(int i=1; i < dimension; i++){
            if(!PointGone[i] && Distance[j][i] < min2){
                if(Distance[j][i] < min1){
                    min2 = min1; next2 = next1;
                    min1 = Distance[j][i]; next1 = i;
                }
                else if(Distance[j][i] > min1){
                    min2 = Distance[j][i]; next2 = i;
                }
            }
        }

        if(next1 == 0){
            PointGone[next2] = true;
            return next2;
        }
        if(next2 == 0){
            PointGone[next1] = true;
            return next1;
        }
        Random rd = new Random();
        if(rd.nextInt(2)==0){
            PointGone[next1] = true;
            return next1;
        }
        else{
            PointGone[next2] = true;
            return next2;
        }
    }

    //getter and setter for dimension, total_transport, capacity
    public static int getDimension() { return dimension; }
    public void setDimension(int dimension) { this.dimension = dimension; }
    public static int getTotal_transport() { return total_transport; }
    public void setTotal_transport(int total_transport) { this.total_transport = total_transport; }
    public static int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
