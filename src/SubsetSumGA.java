import java.util.*;

/**
 * Created by kolbusz on 18.04.15.
 */
public class SubsetSumGA {

    public Population initPopulation(Integer length, Integer size){
        System.out.println("INIT POPULATION");
        Population population = new Population();
        for(int i=0; i < size; i++){
            population.getIndividuals().add(new Individual(length));
        }
        System.out.println("INIT POPULATION - END");
        return population;
    }

    public Population selection(Population population, Integer target){
        System.out.println("SELECTION");
        for(Iterator<Individual> it = population.getIndividuals().iterator(); it.hasNext();){
            Individual ind = it.next();
            if(ind.fitness() > target*0.5){
                it.remove();
            }
        }
        System.out.println("SELECTION - END");
        return population;
    }

    public Population crossover(Population population){
        System.out.println("CROSSOVER");
        Integer size = population.getIndividuals().size() / 2;
        Iterator<Individual> it = population.getIndividuals().iterator();
        Population newPopulation = new Population();
        for(int i=0; i < size; i++){
            Individual[] parents = new Individual[2];
            Individual[] childs = new Individual[2];
            parents[0] = it.next();
            parents[1] = it.next();
            Integer length = parents[0].getChromosome().getGenes().size();
            Random random = new Random();
            Integer crossPoint = random.nextInt(length);
            childs[0] = new Individual(parents[0].getChromosome().getGenes().size());
            childs[1] = new Individual(parents[1].getChromosome().getGenes().size());

            childs[0].getChromosome().setGenes(parents[0].getChromosome().getGenes());
            childs[1].getChromosome().setGenes(parents[1].getChromosome().getGenes());
            for(int k=crossPoint; k < length; k++){
                childs[0].getChromosome().getGenes().get(k).setActivity(parents[1].getChromosome().getGenes().get(k).getActivity());
                childs[1].getChromosome().getGenes().get(k).setActivity(parents[0].getChromosome().getGenes().get(k).getActivity());
            }

            if(childs[0].fitness() <= parents[0].fitness()){
                newPopulation.getIndividuals().add(childs[0]);
            }else {
                newPopulation.getIndividuals().add(parents[0]);
            }
            if(childs[1].fitness() <= parents[1].fitness()){
                newPopulation.getIndividuals().add(childs[1]);
            }else {
                newPopulation.getIndividuals().add(parents[1]);
            }
        }
        System.out.println("CROSSOVER - END");
        return newPopulation;
    }

    public void mutation(Population population){
        System.out.println("MUTATION");
        Random random = new Random();

        for (Individual ind : population.getIndividuals()) {
            if(random.nextFloat() <= Main.MUTATION_PROPABILITY){
                System.out.println("MUTATION HAPPEND");
                Integer index = random.nextInt(population.getIndividuals().iterator().next().getChromosome().getGenes().size() * 10) / 10;
                ind.getChromosome().getGenes().get(index).changeActivity();
            }
        }
        System.out.println("MUTATION - END");
    }

    public void reproduction(Population population, Integer size){
        System.out.println("BEFORE REPRODUCTION: " + population.getIndividuals().size());
        Double sum = 0.0;
        Double max = population.getIndividuals().iterator().next().fitness();
        for(Individual ind : population.getIndividuals()){
            sum += ind.fitness();
            if(max < ind.fitness()){
                max = ind.fitness();
            }
        }

        HashMap<Double, Integer> counter = new HashMap<Double, Integer>();
        HashMap<Double, ArrayList<Individual>> container = new HashMap<Double, ArrayList<Individual>>();
        for(Individual ind : population.getIndividuals()){
            Double p = (max-ind.fitness())/sum;
            if(counter.containsKey(p)){
                counter.replace(p, counter.get(p)+1);
            }else {
                counter.put(p, 1);
                container.put(p, new ArrayList<Individual>());

            }
            container.get(p).add(ind);
        }

        Integer emptySpace = size - population.getIndividuals().size();

        Random random = new Random();
        for(Double p : counter.keySet()){
            Double percent = p*counter.get(p);
            Double space = percent*emptySpace;
            ArrayList<Individual> candidates = container.get(p);
            for(int i=0; i < space; i++){
                Integer index = random.nextInt(candidates.size());
                population.getIndividuals().add(candidates.get(index));
            }
        }
        System.out.println("AFTER REPRODUCTION: " + population.getIndividuals().size());

    }
}
