import config.InstanceConfig;
import java.io.File;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Klasa algorytmu genetycznego
 */
public class SubsetSumGA implements GeneticAlgorithm {
    public Collection<Integer> set = null;
    public ChartSolution chart = null;
    public ArrayList<Integer> result;
    public InstanceConfig config = null;
    
    
    public SubsetSumGA(Reader reader, File file, InstanceConfig config) {
        this.set = reader.read(file);
        this.config = config;
    }
    
    public Collection<Integer> getNumberSet(){
        return this.set;
    }
    
    
    public void run() {
        if(this.set == null){
            return;
        }
        
        chart.bestFitness = new ArrayList<>();
        chart.worstFitness = new ArrayList<>();
        chart.avFitness = new ArrayList<>();

        // GENETIC ALGORITHM
        Population population = this.initPopulation(this.getNumberSet().size(), this.config.POPULATION_SIZE);
        this.fitness(population);
        population.sort();
        chart.bestFitness.add(population.getIndividuals().get(0).getFitness());
        chart.worstFitness.add(population.getIndividuals().get(population.getIndividuals().size()-1).getFitness());
        this.config.iterations = new Integer(this.config.ITER_SIZE);
        while(this.config.iterations-- != 0 && !isSolved(population)){
            this.selection(population);         
            this.reproduction(population);
            population = this.crossover(population);
            this.mutation(population);
            this.fitness(population);
            population.sort();
            chart.bestFitness.add(population.getIndividuals().get(0).getFitness());
            chart.worstFitness.add(population.getIndividuals().get(population.getIndividuals().size()-1).getFitness());
            chart.avFitness.add(population.getAverageFitness());
        }
        this.config.iterations = this.config.ITER_SIZE - this.config.iterations - 1;
        population.sort();
        this.result = transformResult(population.getIndividuals().get(0));
    }
    
    private boolean isSolved(Population population){
        population.sort();
        if(population.getIndividuals().get(0).getFitness().equals(0)){
            return true;
        }
        return false;
    }
    
    public Population initPopulation(Integer chromosomeLength, Integer size){
        Population population = new Population(size);
        for(int i=0; i < population.getSize(); i++){
            population.addNewIndividual(new Individual(chromosomeLength));
        }
        
        return population;
    }
    
    /**
     * Krzyżowanie w losowym punkcie dwóch chromosomów
     * @param population
     * @return 
     */
    public Population crossover(Population population){
        Integer size = population.getIndividuals().size() / 2;
        Iterator<Individual> it = population.getIndividuals().iterator();
        Population newPopulation = new Population(population.getSize());
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
            this.individualFitness(childs[0]);
            this.individualFitness(childs[1]);
            
            /**
             * Sprawdzenie czy nowo powstałe osobniki po krzyżowaniu są lepsze od rodziców
             */
            if(childs[0].getFitness() <= parents[0].getFitness()){
                newPopulation.getIndividuals().add(childs[0]);
            }else {
                newPopulation.getIndividuals().add(parents[0]);
            }
            if(childs[1].getFitness()<= parents[1].getFitness()){
                newPopulation.getIndividuals().add(childs[1]);
            }else {
                newPopulation.getIndividuals().add(parents[1]);
            }
        }
        return newPopulation;
    }

    @Override
    public Population selection(Population population) {
        List<Individual> inds = population.getIndividuals().stream().filter(new Predicate<Individual>(){
            @Override
            public boolean test(Individual ind) {
                return ind.getFitness() <= config.INDIVIDUAL_TOLERANCE*config.TARGET_SUM;
            }
            
        }).collect(Collectors.toList());
        population.individuals.clear();
        population.individuals.addAll(inds);
        return population;
    }

    @Override
    public Population reproduction(Population population) {
        if(population.getIndividuals().isEmpty()){
            System.err.println("Empty population after selection");

            return population;
        }

        Map<Integer, List<Individual>> indGroupMap = population.getIndividuals().stream().collect(Collectors.groupingBy(w -> w.getFitness()));
       
        
        Integer emptySpace = population.getSize() - population.getIndividuals().size();

        Double sum = 0.0;

        for(Integer key : indGroupMap.keySet()){
            sum += key;
        }
        ArrayList<Integer> reverse = new ArrayList<Integer>(indGroupMap.keySet());
        Collections.reverse(reverse);
        Random random = new Random();
        int i = 0;
        
        for(Integer key : indGroupMap.keySet()){
            Double percent = reverse.get(i++)/sum;            
            Integer space = (int)Math.round(percent*emptySpace);
            while(space-- != 0){
                List<Individual> inds = indGroupMap.get(key);
                population.getIndividuals().add(inds.get(random.nextInt(inds.size())));
            }
        }

        return population;
    }
    
    
    
    @Override
    public Population mutation(Population population) {
        Random random = new Random();
        Integer i = 0;
        for (Individual ind : population.getIndividuals()) {
            if(i < this.config.MUTATION_NUMBER && random.nextFloat() <= this.config.MUTATION_PROPABILITY){
                Integer index = random.nextInt(population.getIndividuals().iterator().next().getChromosome().length() * 10) / 10;
                ind.getChromosome().getGene(index).changeActivity();
                i++;
            }
        }
        return population;
    }
    
    
    public ArrayList<Integer> transformResult(Individual ind){
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> it = this.getNumberSet().iterator();
        Integer i = 0;
        while(it.hasNext()){
            Integer number = it.next();
            if(ind.getChromosome().getGene(i).isActive()){
                result.add(number);
            }
            i++;
        }
        return result;
    }

    @Override
    public void fitness(Population population) {
        for(Individual ind : population.getIndividuals()){
            this.individualFitness(ind);
        }
    }
    
    public Integer individualFitness(Individual ind){
        Integer value = 0;
        Iterator<Integer> it = this.set.iterator();
        for(int i=0; i < this.set.size(); i++){
            Integer n = it.next();
            if(ind.getChromosome().getGenes().get(i).isActive()){
                value += n;
            }
        }
        ind.setFitness((Integer) Math.abs(this.config.TARGET_SUM - value));
        ind.setValue(value);
        return ind.getFitness();
    }
}
