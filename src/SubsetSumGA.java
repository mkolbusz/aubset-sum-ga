import java.io.File;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Klasa algorytmu genetycznego
 */
public class SubsetSumGA implements Runnable, GeneticAlgorithm {
    public ArrayList<Integer> iterationsSum;
    
    public SubsetSumGA(Reader reader, File file) {
        Main.SET = reader.read(file);
    }
    
    
    
    @Override
    public void run() {
        if(Main.SET == null){
            return;
        }
        
        iterationsSum = new ArrayList<>();

        // GENETIC ALGORITHM
        Population population = this.initPopulation(Main.SET.size(), Config.POPULATION_SIZE);
        population.sort();
        Integer first = population.getIndividuals().get(0).getFitness();
        Integer last = population.getIndividuals().get(population.getIndividuals().size()-1).getFitness();
        this.iterationsSum.add(first);
        Integer iterations = Config.ITER_SIZE;
        while(iterations-- != 0 && !isSolved(population)){
            
            this.selection(population);            
            this.reproduction(population);
            population = this.crossover(population);
            this.mutation(population);
            population.sort();
            first = population.getIndividuals().get(0).getFitness();
            last = population.getIndividuals().get(population.getIndividuals().size()-1).getFitness();
            this.iterationsSum.add(first);
            
        }
        
       
        
        System.out.println(iterations +" - ITERATIONS: " + this.iterationsSum);
        population.sort();

        ArrayList<Integer> result = Main.transformResult(population.getIndividuals().get(0));
        System.out.println(Main.SET);
        System.out.println("ROZWIAZANIE: " + result + "\nOsobnik: " + population.getIndividuals().get(0));
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
            childs[0].calculateFitness();
            childs[1].calculateFitness();
            
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
                return ind.getFitness() <= Config.INDIVIDUAL_TOLERANCE*Config.TARGET_SUM;
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
         //throw new EmptyPopulationException();
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
            if(i < Config.MUTATION_NUMBER && random.nextFloat() <= Config.MUTATION_PROPABILITY){
                Integer index = random.nextInt(population.getIndividuals().iterator().next().getChromosome().length() * 10) / 10;
                ind.getChromosome().getGene(index).changeActivity();
                i++;
            }
        }
        return population;
    }

}
