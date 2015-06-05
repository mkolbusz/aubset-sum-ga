
import java.io.File;
import java.util.Collection;
import java.util.SortedSet;
import javafx.print.Collation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public interface Reader {
    Collection<Integer> read(File file);
}
