
import java.util.*;

public class Main {
    public static Integer POPULATION_SIZE = 10000;
    public static Integer SUM = 40;
    public static Integer ITER_SIZE = 1;
    public static Double  MUTATION_PROPABILITY = 0.001;
    public static SortedSet<Integer> SET;

    public static void main(String[] args) {

        SortedSet<Integer> set = Main.SET = Main.randomSet();
        if(set == null){
            return;
        }

        System.out.println(set);
        // GENETIC ALGORITHM
        SubsetSumGA ga = new SubsetSumGA();
        Population population = ga.initPopulation(set.size(), POPULATION_SIZE);
        if(ga.selection(population, SUM) != true){
            while(ITER_SIZE-- != 0){
                try {
                    ga.reproduction(population, POPULATION_SIZE);
                }catch (Exception e){
                   e.printStackTrace();
                    return;
                }
                population = ga.crossover(population);
                ga.mutation(population);

                if(ga.selection(population, SUM) == true){
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

        System.out.println(population.getIndividuals().get(0));
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
