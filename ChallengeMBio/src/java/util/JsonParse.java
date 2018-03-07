/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Booking;
import model.Dealer;
import model.Vehicle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jhone
 */
public class JsonParse {
    private final String arq = "C:\\dataset.json";
    private static JsonParse instance = null;

    public static JsonParse getInstance(){
        if(instance==null){
            instance = new JsonParse();
        }
        return instance;
    }
    private JsonParse() {
    }
    
    
    public String loadDataBase(){
        String data ="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(arq)));
            String line;
            while ((line = reader.readLine()) != null){
                data = data.concat(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonParse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public ArrayList<Booking> loadBookings(String data){
        JSONObject jo;
        ArrayList<Booking> bookings = new ArrayList<>();
        
        try {
            jo = new JSONObject(data);
            JSONArray jaAux = jo.getJSONArray("bookings");
            String id, firstName, lastName, vehicleId, pickupDate, createdAt;
            for(int j=0; j<jaAux.length(); j++){
                id = jaAux.getJSONObject(j).getString("id");
                firstName = jaAux.getJSONObject(j).getString("firstName");
                lastName = jaAux.getJSONObject(j).getString("lastName");
                vehicleId = jaAux.getJSONObject(j).getString("vehicleId");
                pickupDate = jaAux.getJSONObject(j).getString("pickupDate");
                createdAt = jaAux.getJSONObject(j).getString("createdAt");
                Booking booking = new Booking(id, firstName, lastName, vehicleId, pickupDate, createdAt);
                bookings.add(booking);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    public ArrayList<Dealer> loadDealers(String data){
        JSONObject jo;
        ArrayList<Dealer> dealers = new ArrayList<>();
        
        try {
            jo = new JSONObject(data);
            JSONArray jaAux = jo.getJSONArray("dealers");
            String id, name;
            double latitude, longitude;
            ArrayList<String> closed = new ArrayList<>();
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            for(int j=0; j<jaAux.length(); j++){
                id = jaAux.getJSONObject(j).getString("id");
                name = jaAux.getJSONObject(j).getString("name");
                latitude = jaAux.getJSONObject(j).getDouble("latitude");
                longitude = jaAux.getJSONObject(j).getDouble("longitude");
                JSONArray aux = jaAux.getJSONObject(j).getJSONArray("closed");
                for(int i=0; i<aux.length(); i++){
                    String s = aux.getString(i);
                    closed.add(s);
                }
                aux = jaAux.getJSONObject(j).getJSONArray("vehicles");
                for(int i=0; i<aux.length(); i++){
                    String idcar = aux.getJSONObject(i).getString("id");
                    String model = aux.getJSONObject(i).getString("model"); 
                    String fuel = aux.getJSONObject(i).getString("fuel");
                    String transmission = aux.getJSONObject(i).getString("transmission");
                    HashMap<String,List<Integer>> availability = new HashMap<>();
                    JSONArray aux2;
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("monday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("monday", schedules);
                        System.out.println("with monday "+model);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("tuesday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("tuesday", schedules);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("wednesday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("wednesday", schedules);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("thursday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("thursday", schedules);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("friday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("friday", schedules);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("saturday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("saturday", schedules);
                    } catch (JSONException e) {}
                    try {
                        aux2 = jaAux.getJSONObject(i).getJSONArray("sunday");
                        ArrayList<Integer> schedules = new ArrayList<>();
                        for(int k=0; k<aux2.length(); k++){
                            int a = aux2.getInt(k);
                            schedules.add(a);
                        }
                        availability.put("sunday", schedules);
                    } catch (JSONException e) {}
                    Vehicle vehicle = new Vehicle(idcar, model, fuel, transmission, availability);
                    vehicles.add(vehicle);
                }
                Dealer dealer = new Dealer(id, name, latitude, longitude, closed, vehicles);
                dealers.add(dealer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dealers;
    }    
}
