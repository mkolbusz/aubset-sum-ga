import java.util.*;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Individual{
    Chromosome chromosome;
    private Integer fitness = null;
    private Integer value = null;

    public Individual(Integer length) {
        this.chromosome = new Chromosome(length);
        Random random = new Random();
        for(int i=0; i < length; i++){
            this.getChromosome().getGenes().add(new Gene((random.nextInt(1000) < 30 ? true : false)));
        }
    }



    public Chromosome getChromosome() {
        return chromosome;
    }
    @Override
    public String toString() {
        String result = "[";
        for(Gene gene : this.getChromosome().getGenes()){
            result += gene + ", ";
        }
        result += "] = " + this.getFitness() + "(" + this.getValue() + ")\n";
        return result;
    }

    /**
     * @return the distance
     */
    public Integer getFitness() {
        if(this.fitness == null){
            throw new NullPointerException();
        }
        return fitness;
    }

    /**
     * @param fitness the distance to set
     */
    public void setFitness(Integer fitness) {
        this.fitness = fitness;
    }
    

    /**
     * @return the value
     */
    public Integer getValue() {
        if(this.value == null){
            throw new NullPointerException();
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }

}
