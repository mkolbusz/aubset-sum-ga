
import exceptions.EmptyPopulationException;

import java.io.*;
import java.util.*;

public class Main {

    public static SortedSet<Integer> SET = null;

    public static void main(String[] args) throws IOException {

        Main.SET = Main.randomSet();
        if(Main.SET == null){
            return;
        }

        System.out.println(Main.SET);
        // GENETIC ALGORITHM
        SubsetSumGA ga = new SubsetSumGA();
        Population population = ga.initPopulation(Main.SET.size(), Config.POPULATION_SIZE);

        if(ga.selection(population, Config.TARGET_SUM) != true){

            while(Config.ITER_SIZE-- != 0){
                try {
                    ga.reproduction(population, Config.POPULATION_SIZE);
                }catch (EmptyPopulationException e){
                   e.printStackTrace();
                    return;
                }
                population = ga.crossover(population);
                ga.mutation(population);

                if(ga.selection(population, Config.TARGET_SUM) == true){
                    break;
                }
            }

        }


        population.getIndividuals().sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.fitness() < o2.fitness()){
                    return -1;
                }
                if(o1.fitness() > o2.fitness()){
                    return 1;
                }
                return 0;
            }
        });

        ArrayList<Integer> result = transformResult(population.getIndividuals().get(0));
        System.out.println("ROZWIAZANIE: " + result);
    }

    private static SortedSet<Integer> randomSet(){
        TreeSet<Integer> input = new TreeSet<Integer>();
        Random random = new Random();
        Integer k = 0;
        while(input.size() != 20){
            if(input.add(random.nextInt(Config.TARGET_SUM))){
                k = 0;
            }else {
                k++;
            }
            if(k >= 100){
                System.out.println("TOO LARGE SET SIZE OR TO LOW RANDOM BOUND");
                return null;
            }
        }

        return input.headSet(Config.TARGET_SUM);
    }

    private static ArrayList<Integer> transformResult(Individual ind){
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> it = Main.SET.iterator();
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
}
