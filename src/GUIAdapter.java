import config.CurrentConfig;
import config.InstanceConfig;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;

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
    private CurrentConfig config = null;
    public AlgorithmInstance ga = null;
    
    public GUIAdapter(subsetGUI gui){
        
        this.gui = gui;
        this.config = CurrentConfig.getCurrentConfig();
        this.gui.getIndividualTolerance().setValue(100*this.config.INDIVIDUAL_TOLERANCE);
        this.gui.getMutationNumberInStep().setValue(this.config.MUTATION_NUMBER);
        this.gui.getMutationPropability().setValue(100*this.config.MUTATION_PROPABILITY);
        this.gui.getTargetSum().setValue(this.config.TARGET_SUM);
        this.gui.getPopulationSize().setValue(this.config.POPULATION_SIZE);
        this.gui.getMaxIterationsNumber().setValue(this.config.ITER_SIZE);
        this.gui.getPropabilityOfSettingGene().setValue(100*this.config.GENE_SET);
        this.gui.getMaxNumberSetValue().setValue(this.config.TARGET_SUM);
        initListeners();
    }
    
    private void initListeners(){
        this.gui.getIndividualTolerance().addChangeListener((ChangeEvent e) -> {
            this.config.INDIVIDUAL_TOLERANCE = (Integer)gui.getIndividualTolerance().getValue()/100.0;
        });
        
        this.gui.getMutationNumberInStep().addChangeListener((ChangeEvent e) -> {
            this.config.MUTATION_NUMBER = (Integer)gui.getMutationNumberInStep().getValue();
        });
        
        this.gui.getMutationPropability().addChangeListener((ChangeEvent e) -> {
            this.config.MUTATION_PROPABILITY = (Double)gui.getMutationPropability().getValue()/100.0;
        });
        
        this.gui.getPopulationSize().addChangeListener((ChangeEvent evt) -> {
            this.config.POPULATION_SIZE = (Integer)gui.getPopulationSize().getValue();
        });
        
        this.gui.getTargetSum().addChangeListener((ChangeEvent e) -> {
            this.config.TARGET_SUM = (Integer)gui.getTargetSum().getValue();
            this.gui.getMaxNumberSetValue().setValue(this.config.TARGET_SUM);
        });
        
        this.gui.getMaxIterationsNumber().addChangeListener((ChangeEvent e) -> {
            this.config.ITER_SIZE = (Integer)gui.getMaxIterationsNumber().getValue();
        });
        
        this.gui.getPropabilityOfSettingGene().addChangeListener((ChangeEvent e) -> {
            this.config.GENE_SET = (Integer)gui.getPropabilityOfSettingGene().getValue()/100.0;
        });
        
        this.gui.getMaxNumberSetValue().addChangeListener((ChangeEvent e) -> {
            this.config.MAX_NUMBER_SET_VALUE = (Integer)this.gui.getMaxNumberSetValue().getValue();
        });
        
        this.gui.getStartButton().addActionListener((ActionEvent e) -> { 
            if(this.file == null){
                JOptionPane.showMessageDialog(gui, "Zaimportuj plik z danymi", "Brak danych", JOptionPane.ERROR_MESSAGE);
                return;
            }
            InstanceConfig instanceConfig = new InstanceConfig(this.config);
            new AlgorithmInstance(
                    new SubsetSumGA(new CSVReader(",", this.config.TARGET_SUM), this.file, instanceConfig), 
                    new ChartSolution("Wykres")
            );
        });
        
        this.gui.getImportDataCSVBtn().addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.showOpenDialog(gui);
            this.file = fileChooser.getSelectedFile();
            if(!this.file.exists()){
                this.file = null;
                JOptionPane.showMessageDialog(gui, "Wybrany plik nie istnieje", "Błędny plik", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        this.gui.getExitBtn().addActionListener((ActionEvent e) -> {
            int choice = JOptionPane.showConfirmDialog(gui, 
                    "Zamykając aplikację stracisz wszystkie niezapisane wyniki algorytmów.\nCzy nadal chcesz zamknąć aplikację?",
                    "Potwierdź zamknięcie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(choice == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        });
    }
}
