/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge;

import com.google.gson.Gson;
import static java.lang.Math.abs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Booking;
import model.Vehicle;
import model.Dealer;
import util.JsonParse;

/**
 * REST Web Service
 *
 * @author jhone
 */
@Path("index")
public class Challenge {

    @Context
    private UriInfo context;
    
    private Gson gson;
    private JsonParse parse;
    private String data;
    private ArrayList<Dealer> dealers;
    private ArrayList<Booking> bookings;

    /**
     * Creates a new instance of Challenge
     */
    public Challenge() {
        this.gson = new Gson();
        this.parse = JsonParse.getInstance();
        this.data = parse.loadDataBase();
        this.dealers = parse.loadDealers(data);
        this.bookings = parse.loadBookings(data);
    }
    
    public void reload(){
        this.data = parse.loadDataBase();
        this.dealers = parse.loadDealers(data);
        this.bookings = parse.loadBookings(data);
    }
    
    
    /**
     * Retrieves representation of an instance of challenge.Challenge
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return data;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dealership/list-vehicles/{tipo}/{key}")
    public String listVehicles(@PathParam("tipo") int tipo, @PathParam("key") String key) {
        
        ArrayList<Vehicle> vehiclesList = new ArrayList<>();
        switch (tipo) {
            case 1://model
                for (Dealer dealer : dealers){
                    for (Vehicle vehicle : dealer.getVehicles()){
                        if(vehicle.getModel().equals(key)){
                            vehiclesList.add(vehicle);
                        }
                    }
                }
                break;
            case 2://Fuel
                for (Dealer dealer : dealers){
                    for (Vehicle vehicle : dealer.getVehicles()){
                        if(vehicle.getFuel().equals(key)){
                            vehiclesList.add(vehicle);
                        }
                    }
                }
                break;
            case 3://Transmission
                for (Dealer dealer : dealers){
                    for (Vehicle vehicle : dealer.getVehicles()){
                        if(vehicle.getTransmission().equals(key)){
                            vehiclesList.add(vehicle);
                        }
                    }
                }
                break;
            case 4://Dealer
                for (Dealer dealer : dealers){
                    if(dealer.getId().equals(key)){
                        vehiclesList = dealer.getVehicles();
                    }
                }
                break;
            default:
                break;
        }        
        return gson.toJson(vehiclesList);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dealership/find-dealer/{model}/{fuel}/{transmission}/{latitude}/{longitude}")
    public String findDealer(@PathParam("model") String model, @PathParam("fuel") String fuel,
     @PathParam("transmission") String transmission, @PathParam("latitude") double myLatitude, 
     @PathParam("fuel") double myLongitude) {
        Dealer dealerFinded = null;
        double distance = -1;
        //System.out.println(model+"\n"+fuel+"\n"+transmission+"\n"+myLatitude+"\n"+myLongitude+"\n\n");
        for (Dealer dealer : dealers){
            for (Vehicle vehicle : dealer.getVehicles()){
                //System.out.println("\n"+vehicle.getModel()+"\n"+vehicle.getFuel()+"\n"+vehicle.getTransmission()+"\n");
                if(vehicle.getModel().equals(model) && vehicle.getFuel().equals(fuel)
                        && vehicle.getTransmission().equals(transmission)){
                    double dealerDistance = abs(myLatitude - dealer.getLatitude()) + abs(myLongitude - dealer.getLongitude());
                    if(distance == -1){
                        distance = dealerDistance;
                        dealerFinded = dealer;
                    }else if(dealerDistance < distance){
                        distance = dealerDistance;
                        dealerFinded = dealer;
                    }
                }
            }
        }
        if (dealerFinded == null)
            return gson.toJson("0");
        return gson.toJson(dealerFinded);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("dealership/booking/create")
    public boolean createBooking(String content){//content in json format
        Booking booking = gson.fromJson(content, Booking.class);        
        
        //disable availibility of the vehicle, so they can not choose an already booked schedule
        String carId = booking.getVehicleId();
        String dateText = booking.getPickupDate().split("T")[0].replace('-', '/');
        String hourText = booking.getPickupDate().split("T")[1];
        String[] hourSplit = hourText.split(":");
        int hour = Integer.parseInt(hourSplit[0]+""+hourSplit[1]);
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String weekday = null;
        try {
            Date date = formato.parse(dateText);
            switch (date.getDay()){
                case 0:
                    weekday = "sunday";
                    break;
                case 1:
                    weekday = "monday";
                    break;
                case 2:
                    weekday = "tuesday";
                    break;
                case 3:
                    weekday = "wednesday";
                    break;
                case 4:
                    weekday = "thursday";
                    break;
                case 5:
                    weekday = "friday";
                    break;
                case 6:
                    weekday = "saturday";
                    break;
                default:
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Dealer dealer : dealers){
            for (Vehicle vehicle : dealer.getVehicles()){
                if(vehicle.getId().equals(carId)){
                    HashMap<String,List<Integer>> avalibility = vehicle.getAvailability();
                    List<Integer> weekdayAux = avalibility.get(weekday);
                    if(weekdayAux!=null){
                        for(int a=0; a<weekdayAux.size();a++){
                            if(hour==weekdayAux.get(a)){
                                weekdayAux.remove(a);
                            }
                        } 
                        avalibility.put(weekday, weekdayAux);
                        vehicle.setAvailability(avalibility);
                    }
                }
            }
        }
        //save dealers in DataBase for persistence
        bookings.add(booking);
        //a comparison method with the database returns true or false for the 
        //insert, in this case I will always return true
        return true;
    }
    
    @DELETE
    @Path("dealership/booking/delete/{id}/{reason}")
    public boolean deleteBooking(@PathParam("id") String id, @PathParam("reason") String reason){
        Booking bookingDelete = null;
        System.out.println("Reason: "+reason);//nothing to do
        for (Booking booking : bookings){
            if(booking.getId().equals(id))
                bookingDelete = booking;
        }
        if(bookingDelete == null){
            return false;
        }
        bookings.remove(bookingDelete);
        //No persistence without DataBase
        
        //Now the availability will be returned to the vehicle
        String carId = bookingDelete.getVehicleId();
        String dateText = bookingDelete.getPickupDate().split("T")[0].replace('-', '/');
        String hourText = bookingDelete.getPickupDate().split("T")[1];        
        String[] hourSplit = hourText.split(":");
        int hour = Integer.parseInt(hourSplit[0]+""+hourSplit[1]);
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String weekday = null;
        try {
            Date date = formato.parse(dateText);
            switch (date.getDay()){
                case 0:
                    weekday = "sunday";
                    break;
                case 1:
                    weekday = "monday";
                    break;
                case 2:
                    weekday = "tuesday";
                    break;
                case 3:
                    weekday = "wednesday";
                    break;
                case 4:
                    weekday = "thursday";
                    break;
                case 5:
                    weekday = "friday";
                    break;
                case 6:
                    weekday = "saturday";
                    break;
                default:
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Dealer dealer : dealers){
            for (Vehicle vehicle : dealer.getVehicles()){
                if(vehicle.getId().equals(carId)){
                    HashMap<String,List<Integer>> avalibility = vehicle.getAvailability();
                    if(avalibility.get(weekday)==null){
                        avalibility.put(weekday, new ArrayList<>());
                    }
                    List<Integer> weekdayAux = avalibility.get(weekday);
                    weekdayAux.add(hour);
                    avalibility.put(weekday, weekdayAux);
                    vehicle.setAvailability(avalibility);
                }
            }
        }
        return true;
    }
    
}
