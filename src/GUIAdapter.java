
import exceptions.EmptyPopulationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public class GUIAdapter {
    private subsetGUI gui = null;
    private File file = null;
    public SubsetSumGA ga = null;
    
    public GUIAdapter(subsetGUI gui){
        this.gui = gui;
        this.gui.getIndividualTolerance().setValue(100*Config.INDIVIDUAL_TOLERANCE);
        this.gui.getMutationNumberInStep().setValue(Config.MUTATION_NUMBER);
        this.gui.getMutationPropability().setValue(100*Config.MUTATION_PROPABILITY);
        this.gui.getTargetSum().setValue(Config.TARGET_SUM);
        this.gui.getPopulationSize().setValue(Config.POPULATION_SIZE);
        this.gui.getMaxIterationsNumber().setValue(Config.ITER_SIZE);
        initListeners();
    }
    
    private void initListeners(){
        this.gui.getIndividualTolerance().addChangeListener((ChangeEvent e) -> {
            Config.INDIVIDUAL_TOLERANCE = (Integer)gui.getIndividualTolerance().getValue()/100.0;
        });
        
        this.gui.getMutationNumberInStep().addChangeListener((ChangeEvent e) -> {
            Config.MUTATION_NUMBER = (Integer)gui.getMutationNumberInStep().getValue();
        });
        
        this.gui.getMutationPropability().addChangeListener((ChangeEvent e) -> {
            Config.MUTATION_PROPABILITY = (Double)gui.getMutationPropability().getValue()/100.0;
        });
        
        this.gui.getPopulationSize().addChangeListener((ChangeEvent evt) -> {
            Config.POPULATION_SIZE = (Integer)gui.getPopulationSize().getValue();
        });
        
        this.gui.getTargetSum().addChangeListener((ChangeEvent e) -> {
            Config.TARGET_SUM = (Integer)gui.getTargetSum().getValue();
        });
        
        this.gui.getMaxIterationsNumber().addChangeListener((ChangeEvent e) -> {
            Config.ITER_SIZE = (Integer)gui.getMaxIterationsNumber().getValue();
        });
        
        this.gui.getStartButton().addActionListener((ActionEvent e) -> { 
            if(this.file == null){
                JOptionPane.showMessageDialog(gui, "Zaimportuj plik z danymi", "Brak danych", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            this.ga = new SubsetSumGA(new CSVReader(), this.file);
            new Thread(ga).start();
            
        });
        
        this.gui.getGenerateChartBtn().addActionListener((ActionEvent e) -> {
            ChartSolution chart = new ChartSolution("Wykres", "x", ga.iterationsSum);
            chart.setSize(800, 600);
            chart.setVisible(true);
        });
        
        this.gui.getImportDataCSVBtn().addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.showOpenDialog(gui);
            this.file = fileChooser.getSelectedFile();
        });
    }
}
