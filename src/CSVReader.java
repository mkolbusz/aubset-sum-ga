
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public class CSVReader implements Reader{
    String delimiter = ",";

    public CSVReader() {}

    
    public CSVReader(String delimiter) {
        this.delimiter = delimiter;
    }
    
    
    @Override
    public Collection<Integer> read(File file) {
        TreeSet<Integer> result = new TreeSet<Integer>();
        
        try{
            String line = "";
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            while((line = fileReader.readLine()) != null){
                String[] elements = line.split(this.delimiter);
                for(String element : elements){
                    Integer number = Integer.valueOf(element);
                    if(number <= Config.TARGET_SUM){
                        result.add(Integer.valueOf(element));
                    }
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    
}
