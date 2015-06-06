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
public class InstanceConfig extends Config {
    public Integer iterations = 0;
    
    public InstanceConfig(CurrentConfig config) {
        this.POPULATION_SIZE = new Integer(config.POPULATION_SIZE);
        this.INDIVIDUAL_TOLERANCE = new Double(config.INDIVIDUAL_TOLERANCE);
        this.MUTATION_PROPABILITY = new Double(config.MUTATION_PROPABILITY);
        this.MUTATION_NUMBER = new Integer(config.MUTATION_NUMBER);
        this.TARGET_SUM = new Integer(config.TARGET_SUM);
        this.ITER_SIZE = new Integer(config.ITER_SIZE);
        this.GENE_SET = new Double(config.GENE_SET);
    }
    
    @Override
    public String toString() {
        return super.toString()
                + "\nIlość wykonanych iteracji: " + this.iterations;
        
    }
    
}
