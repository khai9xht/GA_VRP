
public class Genetic_Algorithm {
    private Population population = new Population();
    private Individual Father_1, Mother_1, Child_11, Child_12;
    private Individual Father_2, Mother_2, Child_21, Child_22;


    public void Selection_All(){
        do {
            Father_1 = population.Selection();
            Father_2 = population.Selection();
            Mother_1 = population.Selection();
            Mother_2 = population.Selection();
        }while(Father_1.equals(Father_2) || Mother_1.equals(Mother_2)
                || Father_2.equals(Mother_2) || Father_1.equals(Mother_1)
                || Mother_1.equals(Father_2) || Father_1.equals(Mother_2));
    }

    public void Crossover_All(){
        Child_11 = population.Crossover(Father_1,Mother_1);
        Child_12 = population.Crossover(Mother_1,Father_1);
        Child_21 = population.Crossover(Father_2,Mother_2);
        Child_22 = population.Crossover(Mother_2,Father_2);
    }
    public void LocalSearchAll(){
        Child_11.local_search();
        Child_12.local_search();
        Child_21.local_search();
        Child_22.local_search();
    }
    public void re_createAll(){
        population.re_create(Child_11);
        population.re_create(Child_12);
        population.re_create(Child_22);
        population.re_create(Child_21);
        population.set_SmallesTotalDistance();
        population.set_Probabilities();

    }
    public Genetic_Algorithm(){
        int i=0;
        while(i < 50){
            Selection_All();
            Crossover_All();
            LocalSearchAll();
            re_createAll();
            i++;
        }
        System.out.print( population.fitness);

    }


}