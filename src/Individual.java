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
        setFitness(calculateDistance(Config.TARGET_SUM, Main.SET));
    }



    public Chromosome getChromosome() {
        return chromosome;
    }


    public Integer calculateDistance(Integer target, Collection<Integer> set){
        Integer value = 0;
        Iterator<Integer> it = set.iterator();
        for(int i=0; i < set.size(); i++){
            Integer n = it.next();
            if(this.getChromosome().getGenes().get(i).isActive()){
                value += n;
            }

        }
        this.setFitness((Integer) Math.abs(target - value));
        this.setValue(value);
        return this.getFitness();
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
    
    public void calculateFitness(){
        this.setFitness(calculateDistance(Config.TARGET_SUM, Main.SET));
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
