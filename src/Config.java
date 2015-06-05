/**
 * Created by kolbusz on 21.04.15.
 */
public abstract class Config {

    /**
     * Rozmiar zbioru liczb
     */
    public static Integer SET_SIZE = 10;

    /**
     * Rozmiar populacji
     */
    public static Integer POPULATION_SIZE = 3000;
    
    /**
     * Prawdopodobieństwo ustawienia genu dla osobinka przy inicjalizacji populacji
     */
    public static Double GENE_SET = 0.3;
    
    /**
     * Docelowa suma
     */
    public static Integer TARGET_SUM = 50;
    
    /**
     * Tolerancja odległości od docelowej sumy/tolerancja osobnika
     * x*TARGET_SUM
     */
    public static Double INDIVIDUAL_TOLERANCE = 0.5;
    
    /**
     * Ilość iteracji
     */
    public static Integer ITER_SIZE = 10;

    /**
     * Prawdopodobieństwo zajścia mutacji
     */
    public static Double MUTATION_PROPABILITY = 0.01;

    /**
     * Maksymalna ilość mutacji w jednym kroku
     */
    public static Integer MUTATION_NUMBER = 5;

}
