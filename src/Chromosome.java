import java.util.ArrayList;

/**
 * Created by kolbusz on 18.04.15.
 */
public class Chromosome {
    private ArrayList<Gene> genes;

    public Chromosome(Integer length) {
        this.genes = new ArrayList<Gene>(length);
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes) {
        this.genes = genes;
    }

    public void setGene(int index, Integer activity){
        this.getGenes().get(index).setActivity(activity);
    }
}
