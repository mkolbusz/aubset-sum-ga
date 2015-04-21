import exceptions.EmptyPopulationException;

import java.util.*;

/**
 * Created by kolbusz on 18.04.15.
 */
public class SubsetSumGA {

    public Population initPopulation(Integer length, Integer size){

        Population population = new Population();
        for(int i=0; i < size; i++){
            population.getIndividuals().add(new Individual(length));
        }

        return population;
    }

    public boolean selection(Population population, Integer target){
        Integer i = 0;
        for(Iterator<Individual> it = population.getIndividuals().iterator(); it.hasNext();){
            Individual ind = it.next();
            if(ind.fitness().intValue() == 0){
                return true;
            }
            if(ind.fitness().intValue() > (target*Config.INDIVIDUAL_TOLERANCE)){
                i++;
                it.remove();
            }
        }
        return false;
    }

    public Population crossover(Population population){
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
            childs[0] = new Individual(parents[0].getChromosome().length());
            childs[1] = new Individual(parents[1].getChromosome().length());

            childs[0].getChromosome().setGenes(parents[0].getChromosome().getGenes());
            childs[1].getChromosome().setGenes(parents[1].getChromosome().getGenes());
            for(int k=crossPoint; k < length; k++){
                childs[0].getChromosome().setGene(k, parents[1].getChromosome().getGene(k).isActive());
                childs[1].getChromosome().setGene(k, parents[0].getChromosome().getGene(k).isActive());
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
        return newPopulation;
    }

    public void mutation(Population population){
        Random random = new Random();
        Integer i = 0;
        for (Individual ind : population.getIndividuals()) {
            if(i < Config.MUTATION_NUMBER && random.nextFloat() <= Config.MUTATION_PROPABILITY){
                Integer index = random.nextInt(population.getIndividuals().iterator().next().getChromosome().length() * 10) / 10;
                ind.getChromosome().getGene(index).changeActivity();
                i++;
            }
        }
    }

    public void reproduction(Population population, Integer size) throws EmptyPopulationException {
        if(population.getIndividuals().size() == 0){
            throw new EmptyPopulationException();
        }

        TreeMap<Double, ArrayList<Individual>> groupedInd = new TreeMap<Double, ArrayList<Individual>>();

        for(Individual ind : population.getIndividuals()){
            Double f = ind.fitness();
            if(groupedInd.get(f) == null){
                groupedInd.put(f, new ArrayList<Individual>());
            }
            ArrayList<Individual> tmp = groupedInd.get(f);
            tmp.add(ind);
        }

        Integer emptySpace = size - population.getIndividuals().size();

        Double sum = 0.0;
        for(Double key : groupedInd.keySet()){
            sum += key;
        }

        Random random = new Random();
        for(Double key : groupedInd.keySet()){
            Double percent = (0.5*Config.TARGET_SUM - key)/sum;
            Integer space = (int)Math.round(percent*emptySpace);
            while(space-- != 0){
                ArrayList<Individual> inds = groupedInd.get(key);
                population.getIndividuals().add(inds.get(random.nextInt(inds.size())));
            }
        }


    }
}
