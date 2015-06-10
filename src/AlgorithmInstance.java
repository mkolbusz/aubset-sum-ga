
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

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
        try {
            InputStream is;
            InputStream is2;

            this.frame = new JFrame("Wątek algorytmu");
            this.frame.setLayout(new GridLayout(3,0));
            this.frame.setBounds(750,400,0,0);
            
            JLabel lbl = new JLabel("WYKONANE ITERACJE: " + this.ga.config.iterations);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setForeground(new Color(50, 75, 85));
            lbl.setBackground(Color.RED);
            
            JButton chartBtn = new JButton("GENERUJ WYKRES");
            
            chartBtn.setBackground(new Color(222, 237, 236));
            chartBtn.setForeground(new Color(50, 75, 85));
            
            chartBtn.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){
                    chartBtn.setBackground(new Color(19, 38, 49));
                    chartBtn.setForeground(new Color(222, 237, 236));
                }
                public void mouseExited(java.awt.event.MouseEvent evt){
                    chartBtn.setBackground(new Color(222, 237, 236));
                    chartBtn.setForeground(new Color(50, 75, 85));
                }
            });
            
            
            
            JButton saveBtn = new JButton("ZAPISZ WYNIK ALGORYTMU DO PLIKU");
            
            
            saveBtn.setBackground(new Color(222, 237, 236));
            saveBtn.setForeground(new Color(50, 75, 85));
            
            saveBtn.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){
                    saveBtn.setBackground(new Color(19, 38, 49));
                    saveBtn.setForeground(new Color(222, 237, 236));
                }
                public void mouseExited(java.awt.event.MouseEvent evt){
                    saveBtn.setBackground(new Color(222, 237, 236));
                    saveBtn.setForeground(new Color(50, 75, 85));
                }
            });
            
            
            
            
            Font font;
            Font font2;
            is = new FileInputStream(new File("futura.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            lbl.setFont(font);
            lbl.setFont(lbl.getFont().deriveFont(1, (float)15.0));
     
                    
            is2 = new FileInputStream(new File("futura_demi.ttf"));
            font2 = Font.createFont(Font.TRUETYPE_FONT, is2);
            
            chartBtn.setFont(font2);
            chartBtn.setFont(chartBtn.getFont().deriveFont(1, (float)15.0));
            saveBtn.setFont(font2);
            saveBtn.setFont(saveBtn.getFont().deriveFont(1, (float)15.0));
            
            
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlgorithmInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(AlgorithmInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void showChart(){
        if(this.ga == null || this.ga.chart == null){
                JOptionPane.showMessageDialog(this.frame, "Brak danych do wygenerowania wykresu.\nUruchom algorytm ponownie.", "Brak danych", JOptionPane.ERROR_MESSAGE);
                return;
            }
        this.ga.chart.setSize(1024, 768);
        this.ga.chart.setVisible(true);
        this.ga.chart.createChart("WYKRES ZBIEŻNOŚCI FUNKCJI DOPASOWANIA");
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
