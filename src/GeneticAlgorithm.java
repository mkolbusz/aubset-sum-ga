
import java.util.Collection;
import java.util.function.Predicate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public interface GeneticAlgorithm {
    Population initPopulation(Integer size, Integer chromosomeLength);
    Population selection(Population population);
    Population reproduction(Population population);
    Population crossover(Population population);
    Population mutation(Population population);
}
