import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Population {
    ArrayList<Individual> individuals;
    Double fittedPercent = 0.0;

    public Population() {
        this.individuals = new ArrayList<Individual>();
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }


    public void printIndividuals(){
        for(Individual ind : this.getIndividuals()){
            System.out.println(ind);
        }
    }

}
