
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public class AlgorithmInstance implements Runnable{
    SubsetSumGA ga = null;
    ChartSolution chart = null;
    JFrame frame = null;

    public AlgorithmInstance(SubsetSumGA ga, ChartSolution chart) {
        this.ga = ga;
        this.ga.chart = chart;
        new Thread(this).start();
        
    }

    @Override
    public void run() {
        this.ga.run();
        this.createFrame();
        
    }
    
    private void createFrame(){
        this.frame = new JFrame("Wątek algorytmu");
        this.frame.setLayout(new GridLayout(3,0));
        JLabel lbl = new JLabel("Ilość wykonanych iteracji: " + this.ga.config.iterations);
        JButton chartBtn = new JButton("Generuj wykres");
        JButton saveBtn = new JButton("Zapisz wynik algorytmu do pliku");
        chartBtn.addActionListener((ActionEvent e) -> {
            this.showChart();
        });
        saveBtn.addActionListener((ActionEvent e) -> {
            this.saveResults();
        });
        frame.setSize(400,170);
        frame.add(lbl);
        frame.add(chartBtn);
        frame.add(saveBtn);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
    
    
    private void showChart(){
        if(this.ga == null || this.ga.chart == null){
                JOptionPane.showMessageDialog(this.frame, "Brak danych do wygenerowania wykresu.\nUruchom algorytm ponownie.", "Brak danych", JOptionPane.ERROR_MESSAGE);
                return;
            }
        this.ga.chart.setSize(1024, 768);
        this.ga.chart.setVisible(true);
        this.ga.chart.createChart("Wykres zbieżności funkcji dopasowania");
    }
    
    private void saveResults(){
        int choice = JOptionPane.showConfirmDialog(frame, "Czy zapisać takżę konfigurację algorytmu", "Zapis do pliku", JOptionPane.YES_NO_CANCEL_OPTION);
        
        if(choice == JOptionPane.CANCEL_OPTION){
            return;
        }
        String contentToSave = "";
        if(choice == JOptionPane.YES_OPTION){
            contentToSave += this.ga.config + "\n";
        }
        contentToSave += this.ga.result;
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik do zapisu");
        
        int userSelection = fileChooser.showSaveDialog(this.frame);
 
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave));
                bw.write(contentToSave);
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(AlgorithmInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            JOptionPane.showMessageDialog(this.frame, "Plik zapisany w " + fileToSave.getAbsolutePath(), "Plik został zapisany", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    
}
