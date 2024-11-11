package com.eci.aygo.uberback;
import com.eci.aygo.uberback.model.*;
import java.util.HashMap;
import java.util.Map; 
import java.util.List;
import java.util.Set;
import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;

/**
 *
 * @author rodrigogualtero
 */
public class Uberback {
    
    

    public static Map<String, Object> getService() {
        Service service1 = new Service();
        Map<String, Object> map = new HashMap();
        map.put("driverName", service1.getDriverName());
        map.put("Plate", service1.getPlate());
        return map;
    }
    
    private static Map<String, String> getLocation(String location){
        Map<String, String> locations = new HashMap();
        String urlInitial = "https://maps.googleapis.com/maps/api/geocode/json?address="+location.trim().strip().replaceAll("\\s+","")+"&key=AIzaSyDr-Wpdrcm_dRc5GQlIbvosIvbEw4TbF08";
        try {
            // Crea el objeto que representa una URL2
            URL siteURL = new URL(urlInitial);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V>
            // para poder navegarlo
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine = null;
            int cont = 0;
            while ((inputLine = reader.readLine()) != null && cont == 0) {
                if(inputLine.contains("lat") ){
                    locations.put("lat",(inputLine.split(":")[1]).split(",")[0]);
                }
                if(inputLine.contains("lng") ){
                    locations.put("lng",(inputLine.split(":")[1]).split(",")[0]);
                cont ++;    
                }    
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return locations;
    }
     
    public static Map<String, String[]> getRoute(String route){
        String origin = route.split(",")[0];
        String destination = route.split(",")[1];
        
        Map<String,String> originR = Uberback.getLocation(origin);
        Map<String,String> destinationR = Uberback.getLocation(destination);
        // Generar el recorrido
        return generateRoute(originR, destinationR);
    }
    
    private static Map<String, String[]> generateRoute(Map<String, String> start, Map<String, String> end) {
        LinkedHashMap<String, String[]> route = new LinkedHashMap<>();
        try{
        // Convertir las coordenadas iniciales de String a Double
        double startLat = Double.parseDouble(start.get("lat"));
        double startLng = Double.parseDouble(start.get("lng"));

        // Convertir las coordenadas finales de String a Double
        double endLat = Double.parseDouble(end.get("lat"));
        double endLng = Double.parseDouble(end.get("lng"));

        // Calcular los incrementos para cada paso
        double latIncrement = (endLat - startLat) / 49;
        double lngIncrement = (endLng - startLng) / 49;

        // Generar las coordenadas intermedias y añadirlas al HashMap
        for (int i = 0; i < 50; i++) {
            double newLat = startLat + (latIncrement * i);
            double newLng = startLng + (lngIncrement * i);

            String key = "ubicacion" + (i + 1);
            String[] value = {String.valueOf(newLat), String.valueOf(newLng)};
            route.put(key, value);
        }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return route;
    }
}
