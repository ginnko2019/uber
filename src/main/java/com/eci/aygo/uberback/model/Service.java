package com.eci.aygo.uberback.model;
import java.util.Random;
/**
 *
 * @author rodrigogualtero Info about driver info
 */
public class Service {

    private String driverName;
    private String plate;
    
    
    
    public Service(){
        String[] names = {"Juan Rojas", "Maria Casas", "Mateo Perez", "Santiago Arias", "Camila Sanchez"};
        String[] plates = {"HUK149", "SMS166", "ELU779", "KEQ696", "TVT05E"};
        Random randomNumbers = new Random();
        driverName = names[(randomNumbers.nextInt(4)+0)];
        plate = plates[(randomNumbers.nextInt(4)+0)];
    }
    
    public String getDriverName(){
        return driverName;
    }
    
     public String getPlate(){
        return plate;
    }

}
