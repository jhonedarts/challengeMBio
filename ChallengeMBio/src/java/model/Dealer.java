/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jhone
 */
public class Dealer {
    private String id, name;
    private double latitude, longitude;
    private ArrayList<String> closed;
    private ArrayList<Vehicle> vehicles;
    private HashMap<String,Vehicle> vehiclesHashMap;

    public Dealer(String id, String name, double latitude, double longitude, ArrayList<String> closed, ArrayList<Vehicle> vehicles) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.closed = closed;
        this.vehicles = vehicles;
        this.vehiclesHashMap = new HashMap<>();
        loadHashMapVehicles(vehicles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getClosed() {
        return closed;
    }

    public void setClosed(ArrayList<String> closed) {
        this.closed = closed;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
        loadHashMapVehicles(vehicles);
    }

    public HashMap<String, Vehicle> getVehiclesHashMap() {
        return vehiclesHashMap;
    }
    
    private void loadHashMapVehicles(ArrayList<Vehicle> vehicles){
        for(int i=0; i<vehicles.size();i++){
            Vehicle vehicle = vehicles.get(i);
            vehiclesHashMap.put(vehicle.getId(), vehicle);
        }
    }
    
}
