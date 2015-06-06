package config;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kolbusz
 */
public class CurrentConfig extends Config{
    private static CurrentConfig config = null;
    
    private CurrentConfig(){};
    
    public static CurrentConfig getCurrentConfig(){
        if(config == null){
            config = new CurrentConfig();
        }
        return config;
    }

    
    
}
