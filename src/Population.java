import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Population {
    boolean isSorted = false;
    private Integer size;
    ArrayList<Individual> individuals = new ArrayList<Individual>();

    public Population(Integer size) {
        this.size = size;
    }
    
    public void addNewIndividual(Individual ind){
        this.getIndividuals().add(ind);
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    @Override
    public String toString() {
        String result = "Population [size="+individuals.size()+"]:\n";
        for(int i=0; i < individuals.size(); i++){
            result += individuals.get(i).toString() + "\n";
        }
        return result;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public Double getAverageFitness(){
        Double sum = 0.0;
        for(Individual ind : individuals){
            sum += ind.getFitness();
        }
        return (sum/individuals.size());
    }
    
    public Population sort(){
        this.getIndividuals().sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.getFitness() < o2.getFitness()){
                    return -1;
                }
                if(o1.getFitness()> o2.getFitness()){
                    return 1;
                }
                return 0;
            }
        });
       return this;
    }
    
}
