import java.util.ArrayList;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Population {
    ArrayList<Individual> individuals = new ArrayList<Individual>();

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    public void printIndividuals(){
        for(Individual ind : this.getIndividuals()){
            System.out.println(ind);
        }
    }

}
