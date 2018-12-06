import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;

class  Individual{
    int[] gen = new int[Setup_VRP.getDimension()];
    int[] Transport = new int[Setup_VRP.getDimension()];
    private int total_distance;
    private int total_transport;


    public Individual(){
        gen[0] = 0;
        total_transport = 0;
        total_distance = 0;
    }

    public int get_Distance(int i, int j){
        int distance = 0;
        for (int k = i+1; k < j; k++) {
            distance += Setup_VRP.Distance[gen[k]][gen[k + 1]];
        }
        distance += Setup_VRP.Distance[0][gen[i+1]] + Setup_VRP.Distance[gen[j]][0];
        return distance;
    }

    public void update_distance(){
        int[] V = new int[gen.length];
        V[0] = 0;
        for (int i=1;i<gen.length;i++){
            V[i] = MAX_VALUE;
        }
        int j, capacity;
        int distance = 0;
        for (int i=1;i<gen.length;i++){
            j = i;  capacity = 0;
            do {
                capacity += Setup_VRP.Demand[j];
                if(i == j){
                    distance = Setup_VRP.Distance[gen[j]][gen[0]]*2;
                }else{
                    distance  = distance - Setup_VRP.Distance[gen[j-1]][0]
                            + Setup_VRP.Distance[gen[j-1]][gen[j]] + Setup_VRP.Distance[gen[j]][0];
                }
                if(capacity < Setup_VRP.getCapacity()){
                    if(V[i-1] + distance < V[j]) {
                        V[j] = V[i - 1] + distance;
                    }
                }
                j++;
            }while(j < gen.length && capacity < Setup_VRP.getCapacity());
        }
        total_distance = V[gen.length-1];
    }

    public void Greedy(){
        int capacity = Setup_VRP.getCapacity();
        int index_current = 0;
        int transport = 0;
        while (index_current < Setup_VRP.getDimension()-1){

            int next;
            if(capacity != Setup_VRP.getCapacity()) next = Setup_VRP.get_nearest_random(gen[index_current]);
            else next = Setup_VRP.get_nearest_random(gen[0]);
            gen[index_current+1] = next;

            if(capacity - Setup_VRP.Demand[next] >= 0) {
                total_distance += Setup_VRP.Distance[gen[index_current]][next];
                capacity = capacity - Setup_VRP.Demand[next];
                Transport[index_current] = transport;
            }else{
                capacity = Setup_VRP.getCapacity() - Setup_VRP.Demand[next];
                total_distance += Setup_VRP.Distance[gen[index_current]][0] + Setup_VRP.Distance[next][0];
                transport++;
                Transport[index_current] = transport;

            }
            index_current++;
        }
        for(int i=1; i< Setup_VRP.getDimension(); i++){
            Setup_VRP.PointGone[i] = false;
        }
    }

    public void split(){
        total_transport = 0;
        int[] V = new int[gen.length];
        int[] P = new int[gen.length];
        V[0] = 0;
        for (int i=1;i<gen.length;i++){
            V[i] = MAX_VALUE;
        }
        int j, capacity;
        int distance = 0;
        for (int i=1;i<gen.length;i++){
            j = i;  capacity = 0;
            do {
                capacity += Setup_VRP.Demand[j];
                if(i == j){
                    distance = Setup_VRP.Distance[gen[j]][gen[0]]*2;
                }else{
                    distance  = distance - Setup_VRP.Distance[gen[j-1]][0]
                            + Setup_VRP.Distance[gen[j-1]][gen[j]] + Setup_VRP.Distance[gen[j]][0];
                }
                if(capacity < Setup_VRP.getCapacity()){
                    if(V[i-1] + distance < V[j]) {
                        V[j] = V[i - 1] + distance;
                        P[j] = i - 1;
                    }
                }
                j++;
            }while(j < gen.length && capacity < Setup_VRP.getCapacity());
        }
        total_distance = V[gen.length-1];
        int k = gen.length-1;
        while(k > 0){
            for(int i = k; i > P[k]; i--){
                Transport[i] = total_transport;
            }
            total_transport++;
            k = P[k];
        }
        Transport[0] = total_transport-1;
    }

    public void swap(int i, int j){
        int s = total_distance;
            int temp = gen[j];
            gen[j] = gen[i];
            gen[i] = temp;
            update_distance();
        if(s < total_distance){
            temp = gen[j];
            gen[j] = gen[i];
            gen[i] = temp;
            total_distance = s;
        }
    }

    public void break_intersection(int m, int n, int p, int q){

        Point A1 = Setup_VRP.Array_Point.get(gen[m]);
        Point A2 = Setup_VRP.Array_Point.get(gen[n]);
        Point B1 = Setup_VRP.Array_Point.get(gen[p]);
        Point B2 = Setup_VRP.Array_Point.get(gen[q]);
        if(Point.cut(A1, A2, B1, B2)){
            if(gen[m]*gen[p] != 0) swap(m,p);
            else if(gen[m]*gen[q] != 0) swap(m,q);
            else if(gen[n]*gen[p] != 0) swap(n,p);
            else if(gen[n]*gen[q] != 0) swap(n,q);
        }
    }

    public void local_search(){
        for(int i=1; i < gen.length-4; i++){
            for(int j=i+2; j< gen.length-1; j++){
                if(Transport[i] == Transport[i+1]){
                    if(Transport[j] == Transport[j+1]){
                        break_intersection(i,i+1, j,j+1);
                    }else{
                        break_intersection(i,i+1, j, 0);
                        break_intersection(i,i+1, 0, j+1);
                    }
                }else{
                    if(Transport[j] == Transport[j+1]){
                        break_intersection(i,0, j, j+1);
                        break_intersection(0,i+1, j,j+1);
                    }
                }
            }
        }
        split();
    }


    public int getTotal_distance() { return total_distance; }

    @Override
    public String toString() {
        return "Individual{" +
                "gen=" + Arrays.toString(gen) +
                ",\n total distance=" + total_distance +
                ",\n total transport=" + total_transport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return total_distance == that.total_distance &&
                Arrays.equals(gen, that.gen);
    }

}
