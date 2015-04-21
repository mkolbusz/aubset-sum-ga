import java.util.*;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Individual{
    Chromosome chromosome;

    public Individual(Integer length) {
        this.chromosome = new Chromosome(length);
        Random random = new Random();
        for(int i=0; i < length; i++){
            this.getChromosome().getGenes().add(new Gene((random.nextInt(100) < 30 ? true : false)));
        }
    }



    public Chromosome getChromosome() {
        return chromosome;
    }

    public Double fitness(){
        return calculateDistance(Config.TARGET_SUM, Main.SET);
    }


    public Double calculateDistance(Integer target, Set<Integer> set){
        Double position = 0.0;
        Iterator<Integer> it = set.iterator();
        for(int i=0; i < set.size(); i++){
            Integer n = it.next();
            if(this.getChromosome().getGenes().get(i).isActive()){
                position += n;
            }

        }
        return Math.abs(target - position);
    }
    @Override
    public String toString() {
        String result = "[";
        for(Gene gene : this.getChromosome().getGenes()){
            result += gene + ", ";
        }
        result += "] = " + String.valueOf(this.fitness()) + "\n";
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Individual)) return false;
        Individual o = (Individual)obj;
        for(int i=0; i < this.getChromosome().getGenes().size(); i++){
            if(this.getChromosome().getGene(i) != o.getChromosome().getGene(i)){
                return false;
            }
        }
        return true;
    }
}
