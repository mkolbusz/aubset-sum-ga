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

    public void setGene(int index, Boolean activity){
        this.getGenes().get(index).setActivity(activity);
    }
    public Gene getGene(int index){
        if(index >= 0 && index < this.getGenes().size()){
            return this.getGenes().get(index);
        }
        throw new IndexOutOfBoundsException("Out of genes array");
    }

    public Integer length(){
        return this.genes.size();
    }
}
