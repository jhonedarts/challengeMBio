/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge;

import com.google.gson.Gson;
import java.util.ArrayList;
import model.Booking;
import model.Dealer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.JsonParse;

/**
 *
 * @author jhone
 */
public class ChallengeTest {
    Challenge instance;
    
    public ChallengeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Challenge();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listVehicles method, of class Challenge.
     */
    @Test
    public void testListVehicles() {
        System.out.println("listVehicles");
        int tipo = 1;//model
        String key = "E";
        String expResult = "768a73af-4336-41c8-b1bd-76bd700378ce";
        String result = instance.listVehicles(tipo, key);
        assertTrue(result.contains(expResult));
        
        tipo = 2;//fuel
        key = "ELECTRIC";
        expResult = "d5d0aabc-c0de-4f38-badc-759f96f5fca3";
        result = instance.listVehicles(tipo, key);
        assertTrue(result.contains(expResult));
        
        tipo = 3;//transmission
        key = "MANUAL";
        expResult = "1cd6eae7-5f6f-42a7-a4ca-de7e498d9ce4";
        result = instance.listVehicles(tipo, key);
        assertTrue(result.contains(expResult));
        
        tipo = 4;//dealer
        key = "bbcdbbad-5d0b-45ef-90ac-3581b997e063";//MB Lisboa
        expResult = "778a04fd-0a6a-4dc7-92bb-a7517608efc2";
        result = instance.listVehicles(tipo, key);
        assertTrue(result.contains(expResult));
    }

    /**
     * Test of findDealer method, of class Challenge.
     */
    @Test
    public void testFindDealer() {
        instance.reload();
        System.out.println("findDealer");
        String model = "A";
        String fuel = "ELECTRIC";
        String transmission = "AUTO";
        double myLatitude = 38.746720;
        double myLongitude = -9.229836;
        String expResult = "bbcdbbad-5d0b-45ef-90ac-3581b997e063";//MB Lisboa
        String result = instance.findDealer(model, fuel, transmission, myLatitude, myLongitude);
        assertTrue(result.contains(expResult));
    }

    /**
     * Test of createBooking method, of class Challenge.
     */
    @Test
    public void testCreateBooking() {
        System.out.println("createBooking");
        String content = "{\n" +
"			\"id\": \"c509c312-f56d-441c-83cf-0accd59c9d70\",\n" +
"			\"firstName\": \"Marcus\",\n" +
"			\"lastName\": \"Randolph\",\n" +
"			\"vehicleId\": \"44a36bfa-ec8f-4448-b4c2-809203bdcb9e\",\n" +
"			\"pickupDate\": \"2018-03-06T10:30:00\",\n" +
"			\"createdAt\": \"2018-02-26T08:42:46.294\"\n" +
"		}";
        //this booking already exists. In this test I can save it because there 
        //is no persistence in database.
        //But the idea is that the user can not select an already booked schedule, 
        //since the availability of the vehicle will be taken, making it 
        //impossible for the user to choose it.
        assertTrue(instance.createBooking(content));
    }

    /**
     * Test of deleteBooking method, of class Challenge.
     */
    @Test
    public void testDeleteBooking() {
        System.out.println("deleteBooking");
        String id = "d972958b-9f4a-400a-b68b-011c3780e06e";
        String reason = "I will travel in this days";
        assertTrue(instance.deleteBooking(id, reason));
    }
    
}
