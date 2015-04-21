import com.sun.org.apache.bcel.internal.generic.POP;

import java.util.*;

public class Main {
    public static Integer POPULATION_SIZE = 10000;
    public static Integer SUM = 45;
    public static Integer ITER_SIZE = 100;
    public static Double  MUTATION_PROPABILITY = 0.01;
    public static SortedSet<Integer> SET;

    public static void main(String[] args) {

        SortedSet<Integer> set = Main.SET = Main.randomSet();
        if(set == null){
            return;
        }

        // GENETIC ALGORITHM
        SubsetSumGA ga = new SubsetSumGA();
        Population population = ga.initPopulation(set.size(), POPULATION_SIZE);



        while(population.getIndividuals().size() <= POPULATION_SIZE*0.1 || ITER_SIZE-- != 0){
            ga.selection(population, SUM);
            ga.reproduction(population, POPULATION_SIZE);
            population = ga.crossover(population);
            ga.mutation(population);
        }
        for(Individual ind : population.getIndividuals()){
            System.out.println(ind);
        }
        System.out.println(set);
    }

    private static SortedSet<Integer> randomSet(){
        TreeSet<Integer> input = new TreeSet<Integer>();
        Random random = new Random();
        Integer k = 0;
        while(input.size() != 100){
            if(input.add(random.nextInt(200))){
                k = 0;
            }else {
                k++;
            }
            if(k >= 100){
                System.out.println("TOO LARGE SET SIZE OR TO LOW RANDOM BOUND");
                return null;
            }
        }

        return input.headSet(SUM);
    }
}
