public class test {
    public static void main(String[] args) {
        Setup_VRP setup_vrp = new Setup_VRP("X-n101-k25.vrp");
        Population a = new Population();
       Individual x = new Individual();
       Individual y = new Individual();
       x.Greedy();
       y.Greedy();
       System.out.println(x);
       System.out.println(y);
       Individual z = a.Crossover(x,y);
       System.out.println(z);
        System.out.println(z.getTotal_distance());
    }
}
