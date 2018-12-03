import java.util.Random;

import static java.lang.Integer.MAX_VALUE;

public class Population {
    protected static final int total_Individual = 20;
    protected Individual[] individuals = new Individual[total_Individual];
    protected double[] Probabilities = new double[total_Individual];
    protected int SmallesTotalDistance = MAX_VALUE;


    public void set_SmallesTotalDistance(){
        for(int i=1;i<total_Individual;i++){
            if(SmallesTotalDistance > individuals[i].getTotal_distance())
                SmallesTotalDistance = individuals[i].getTotal_distance();
        }
    }


    public void set_Probabilities(){
        double total = 0;
        double total_probability = 100;
        for(int i=0; i<total_Individual;i++){
            total += individuals[i].getTotal_distance();
        }
        for(int i=0; i<total_Individual-1;i++){
            Probabilities[i] = individuals[i].getTotal_distance()/total*100;
            total_probability -= Probabilities[i];
        }
        Probabilities[total_Individual-1] = total_probability;
    }

    public Individual Selection(){
        Random rd = new Random();
        int radius = rd.nextInt(100)+1;
        int index = 0;
        while(radius > 0 && index < total_Individual){
            radius -= Probabilities[index];
            index++;
        }
        return individuals[index-1];
    }

    public Individual Crossover(Individual x, Individual y){
        Individual Child = new Individual();
        Random rd = new Random();
        int random;
        do {
            random = rd.nextInt(Setup_VRP.getDimension());
        }while(random == Setup_VRP.getDimension()/2 || random == 0);

        int index_first, index_last;
        if(random > Setup_VRP.getDimension()/2){
            index_last = random;
            index_first = random - Setup_VRP.getDimension()/2;
        }else{
            index_first = random;
            index_last = random + Setup_VRP.getDimension()/2;
        }

        Boolean[] V = new Boolean[Setup_VRP.getDimension()];
        for(int i=0; i<Setup_VRP.getDimension(); i++){
            V[i] = false;
        }

        int index_child;
        for(index_child = index_first; index_child< index_last; index_child++){
            Child.gen[index_child] = x.gen[index_child];
            V[x.gen[index_child]] = true;
        }

       for(int index_y = index_last ; index_y < Setup_VRP.getDimension() + index_last; index_y++ ){
           if(index_child == Setup_VRP.getDimension()) index_child ++;
           if(!V[y.gen[index_y % Setup_VRP.getDimension()]]){
               Child.gen[index_child % Setup_VRP.getDimension()] = y.gen[index_y % Setup_VRP.getDimension()];
               V[y.gen[index_y % Setup_VRP.getDimension()]] = true;
               index_child++;
           }
       }
        Child.split();
        return Child;
    }

    public int getSmallesTotalDistance() { return SmallesTotalDistance; }

    public void setSmallesTotalDistance(int smallesTotalDistance) { SmallesTotalDistance = smallesTotalDistance; }

    public Population(){
        for(int i = 0; i < total_Individual; i++){
            individuals[i] = new Individual();
            individuals[i].Greedy();
        }
        set_Probabilities();
        set_SmallesTotalDistance();
    }
}
