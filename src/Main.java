
import exceptions.EmptyPopulationException;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Main {

    

    public static void main(String[] args){
        
        subsetGUI gui = new subsetGUI();
        
        GUIAdapter guiAdapter = new GUIAdapter(gui);
        
        gui.setVisible(true);
    }

}
