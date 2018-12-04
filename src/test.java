public class test {
    public static void main(String[] args) {
        Setup_VRP setup_vrp = new Setup_VRP("X-n101-k25.vrp");
        Population a = new Population();
       Individual x = new Individual();
       Individual y = new Individual();
       x.Greedy();
       x.split();
        for(int i=0;i<x.gen.length;i++){
            System.out.print(x.gen[i]+" ");
        }
        System.out.println();
        for(int i=0;i<x.gen.length;i++){
            System.out.print(x.Transport[i]+" ");
        }
        System.out.println();
        System.out.println(x.getTotal_distance());
    }
}
