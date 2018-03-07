/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jhone
 */
public class Vehicle {
    private String id, model, fuel, transmission;
    private HashMap<String,List<Integer>> availability; 

    public Vehicle(String id, String model, String fuel, String transmission, HashMap<String, List<Integer>> availability) {
        this.id = id;
        this.model = model;
        this.fuel = fuel;
        this.transmission = transmission;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public HashMap<String, List<Integer>> getAvailability() {
        return availability;
    }

    public void setAvailability(HashMap<String, List<Integer>> availability) {
        this.availability = availability;
    }
    
    
}
