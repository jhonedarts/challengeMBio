/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jhone
 */
public class JsonParseTest {
    private JsonParse instance;
    private String data;
    
    public JsonParseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = JsonParse.getInstance();
        data = instance.loadDataBase();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadDataBase method, of class JsonParse.
     */
    @Test
    public void testLoadDataBase() {
        System.out.println("loadDataBase");
        String result = instance.loadDataBase();
        assertNotNull(result);        
    }

    /**
     * Test of loadBookings method, of class JsonParse.
     */
    @Test
    public void testLoadBookings() {
        System.out.println("loadBookings");
        String expResult = "Joanna117";
        String result = instance.loadBookings(data).get(0).getFirstname();
        int count = instance.loadBookings(data).size();
        assertEquals(expResult, result+""+count);
    }

    /**
     * Test of loadDealers method, of class JsonParse.
     */
    @Test
    public void testLoadDealers() {
        System.out.println("loadDealers");
        String expResult = "MB Albufeira3";
        String result = instance.loadDealers(data).get(0).getName();
        int count = instance.loadDealers(data).size();
        assertEquals(expResult, result+""+count);
    }
    
}
