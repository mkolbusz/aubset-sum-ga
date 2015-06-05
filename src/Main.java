
import exceptions.EmptyPopulationException;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.*;

public class Main {

    public static Collection<Integer> SET = null;

    public static void main(String[] args){
        
        subsetGUI gui = new subsetGUI();
        
        GUIAdapter guiAdapter = new GUIAdapter(gui);
        
        gui.setVisible(true);
    }

    public static ArrayList<Integer> transformResult(Individual ind){
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> it = Main.SET.iterator();
        Integer i = 0;
        while(it.hasNext()){
            Integer number = it.next();
            if(ind.getChromosome().getGene(i).isActive()){
                result.add(number);
            }
            i++;
        }
        return result;
    }
}
