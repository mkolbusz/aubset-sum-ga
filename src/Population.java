import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Population {
    ArrayList<Individual> individuals;

    public Population() {
        this.individuals = new ArrayList<Individual>();
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }


}
