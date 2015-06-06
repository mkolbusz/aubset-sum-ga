/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author kolbusz
 */
public abstract class Config {
    /**
     * Rozmiar populacji
     */
    public Integer POPULATION_SIZE = 100;
    
    /**
     * Prawdopodobieństwo ustawienia genu dla osobinka przy inicjalizacji populacji
     */
    public Double GENE_SET = 0.15;
    
    /**
     * Docelowa suma
     */
    public Integer TARGET_SUM = 164;
    
    /**
     * Tolerancja odległości od docelowej sumy/tolerancja osobnika
     * x*TARGET_SUM
     */
    public Double INDIVIDUAL_TOLERANCE = 0.5;
    
    /**
     * Ilość iteracji
     */
    public Integer ITER_SIZE = 10;

    /**
     * Prawdopodobieństwo zajścia mutacji
     */
    public Double MUTATION_PROPABILITY = 0.002;

    /**
     * Maksymalna ilość mutacji w jednym kroku
     */
    public Integer MUTATION_NUMBER = 5;
    
    /**
     * Maksymalna wartość elementów w zbiorze wejściowym
     */
    public Integer MAX_NUMBER_SET_VALUE = new Integer(TARGET_SUM);
    
    @Override
    public String toString(){
        return "Rozmiar populacji: " + String.valueOf(POPULATION_SIZE) +
                "\nSuma docelowa: " + String.valueOf(TARGET_SUM) + 
                "\nMaksymalna ilość iteracji: " + String.valueOf(ITER_SIZE) + 
                "\nPrawdopodobieństwo zajścia mutacji: " + String.valueOf(MUTATION_PROPABILITY) +
                "\nMaksymalna ilość mutacji w jednej iteracji: " + String.valueOf(MUTATION_NUMBER) + 
                "\nTolerancja dla osobnika: " + String.valueOf(INDIVIDUAL_TOLERANCE) + 
                "\nPrawdopodobieństwo ustawienia genu w populacji początkowej: " + String.valueOf(GENE_SET);
    }
}
