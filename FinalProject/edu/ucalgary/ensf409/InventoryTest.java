/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class InventoryTest {

	//enter users mySQL login info in the following two data members:
	String user = "ali";
	String pass = "ensf409";
	@Test
    // This a test to see if method getInfo() works for retrieving manufacturer data give category and type info
    public void testGetInfo_manufacturer() {
        String expResult = "003";

        Inventory test = new Inventory("chair","mesh",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        var temp = Inventory.manuList;
        var result = temp.get(2).getManuID();
        assertEquals("Chair element didn't match: ", expResult, result);
    }

    @Test
    // This a test to see if method getInfo() works for retrieving Chair ID give category and type info
    public void testGetInfo_chair() {
        String expResult = "C3405";

        Inventory test = new Inventory("chair","mesh",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        var temp = Inventory.chairList;
        var result = temp.get(5).getID();
        assertEquals("Chair element didn't match: ", expResult, result);
    }

    @Test
    // This a test to see if method getInfo() works for retrieving Desk ID give category and type info
    public void testGetInfo_desk() {
        String expResult = "D7373";

        Inventory test = new Inventory("desk","standing",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        var temp = Inventory.deskList;
        var result = temp.get(5).getID();
        assertEquals("Desk element didn't match: ", expResult, result);
    }

    @Test
    // This a test to see if method getInfo() works for retrieving Filing ID give category and type info
    public void testGetInfo_filing() {
        String expResult = "F006";

        Inventory test = new Inventory("filing","small",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        var temp = Inventory.filingList;
        var result = temp.get(5).getID();
        assertEquals("Filing element didn't match: ", expResult, result);
    }

    @Test
    // This a test to see if method getInfo() works for retrieving Lamp ID give category and type info
    public void testGetInfo_lamp() {
        String expResult = "L982";

        Inventory test = new Inventory("lamp","desk",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        var temp = Inventory.lampList;
        var result = temp.get(5).getID();
        assertEquals("Lamp element didn't match: ", expResult, result);
    }

    @Test
    // This a test to see if the wrong type was input for category would be caught and throw an exception in terminal
    public void testInventory_WrongType() {

        Inventory test = new Inventory("chair","Small",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        // There is no way of checking whether an exception was thrown or not so the best we could do is fail the test
        // and manually check terminal for the exception statement followed by the failed test
        fail("Was expecting Illegal Argument Exception");
    }

    @Test
    // This a test to see if the incorrect spelling would for type would be caught and throw an exception in terminal
    public void testInventory_IncorrectCategorySpelling() {

        Inventory test = new Inventory("chiar","mesh",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        // There is no way of checking whether an exception was thrown or not so the best we could do is fail the test
        // and manually check terminal for the exception statement followed by the failed test
        fail("Was expecting Illegal Argument Exception");
    }
    @Test
    // This a test to see if the spelling of Swing Arm makes a makes a difference if not it should the test should pass
    //Note: entering Swing Arm in the terminal will result in an error and hence has to be entered a one word
    public void testInventory_Lamp_SwingArm(){ 
        Inventory test = new Inventory("Lamp","Swingarm",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        
        assertEquals(30, Inventory.lampPrice(Inventory.lampList, Inventory.type, Inventory.amount));
    }

    @Test
    // This a test to see if the spelling error in type should throw an error
    public void testInventory_IncorrectTypeSpelling() {

        Inventory test = new Inventory("chair","mseh",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        // There is no way of checking whether an exception was thrown or not so the best we could do is fail the test
        // and manually check terminal for the exception statement followed by the failed test
        fail("Was expecting Illegal Argument Exception");
    }

    @Test
    // This is a test to see if numbers below 1 are accepted
    public void testInventory_IncorrectNumber() {

        Inventory test = new Inventory("chair","mesh",0,user,pass);
        test.initializeConnection();
        test.getInfo();
        // There is no way of checking whether an exception was thrown or not so the best we could do is fail the test
        // and manually check terminal for the exception statement followed by the failed test
        fail("Was expecting Illegal Argument Exception");
    }

    @Test
    // This test is to see if a error will be thrown if a negative Integer number is entered
    public void testInventory_Negative() {

        Inventory test = new Inventory("chair","mesh",-1,user,pass);
        test.initializeConnection();
        test.getInfo();
        // There is no way of checking whether an exception was thrown or not so the best we could do is fail the test
        // and manually check terminal for the exception statement followed by the failed test
        fail("Was expecting Illegal Argument Exception");
    }

    @Test
    // This test is to see if an obviously large amount of chair will pass through calculate price and fail
    public void testInventory_TooManyChairs() {
        int expResult = 999999999;
        Inventory test = new Inventory("chair","mesh",15,user,pass);
        test.initializeConnection();
        test.getInfo();
        int result = Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount);
        assertEquals("Works",expResult,result);
    }

    @Test
    //This test is to see if deleteFromDatabase() deletes from database
    public void testDeleteFromDatabase() {

        Inventory test = new Inventory("chair","mesh",1,user,pass);
        test.initializeConnection();
        test.getInfo();
        test.deleteFromDatabase("C1320");
        // have to manually check in sql for deletion
        
    }
    
    //test to assure the cheapest price for assembling a desk lamp is $20
    @Test
	public void priceDeskLampTest()
	{
    	Inventory test = new Inventory("lamp","desk",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        int expResult = 20;
        int result = Inventory.lampPrice(Inventory.lampList, Inventory.type, Inventory.amount);
        System.out.println(result);
        assertEquals(expResult, result);
	}
    
  //test to assure the cheapest price for assembling a study lamp is $10
    @Test
    public void priceStudyLampTest()
	{
    	Inventory test = new Inventory("lamp","study",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(10, Inventory.lampPrice(Inventory.lampList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a swing arm lamp is $30
    @Test
    public void priceSwingArmLampTest()
	{
    	Inventory test = new Inventory("lamp","swing arm",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(30, Inventory.lampPrice(Inventory.lampList, Inventory.type, Inventory.amount));
	}
    
  //test to assure 3 study lamps can not be created with the given inventory database
    @Test
    public void priceMultipleLampsTest()
	{
    	Inventory test = new Inventory("lamp","study",3,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(999999999, Inventory.lampPrice(Inventory.lampList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling small filing is $100
    @Test
    public void priceSmallFilingTest()
	{
    	Inventory test = new Inventory("filing","small",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(100, Inventory.filingPrice(Inventory.filingList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a medium filing is $200
    @Test
    public void priceMediumFilingTest()
	{
    	Inventory test = new Inventory("filing","medium",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(200, Inventory.filingPrice(Inventory.filingList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a large filing is $300
    @Test
    public void priceLargeFilingTest()
	{
    	Inventory test = new Inventory("filing","large",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(300, Inventory.filingPrice(Inventory.filingList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling 2 large filings is $600
    @Test
    public void priceMultipleFilingsTest()
	{
    	Inventory test = new Inventory("filing","large",2,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(600, Inventory.filingPrice(Inventory.filingList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a standing desk is $300
    @Test
    public void priceStandingDeskTest()
	{
    	Inventory test = new Inventory("desk","standing",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(300, Inventory.deskPrice(Inventory.deskList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling an adjustable desk is $400
    @Test
    public void priceAdjustableDeskTest()
	{
    	Inventory test = new Inventory("desk","adjustable",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(400, Inventory.deskPrice(Inventory.deskList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a traditional desk is $100
    @Test
    public void priceTraditionalDeskTest()
	{
    	Inventory test = new Inventory("desk","traditional",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(100, Inventory.deskPrice(Inventory.deskList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling 2 traditional desks is $200
    @Test
    public void priceMultipleDesksTest()
	{
    	Inventory test = new Inventory("desk","traditional",2,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(200, Inventory.deskPrice(Inventory.deskList, Inventory.type, Inventory.amount));
	}
    
  //test to assure a kneeling chair can not be created with the given inventory database
    @Test
    public void priceKneelingChairTest()
	{
    	Inventory test = new Inventory("chair","kneeling",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(999999999, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a task chair is $150
    @Test
    public void priceTaskChairTest()
	{
    	Inventory test = new Inventory("chair","task",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(150, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling a mesh chair is $200
    @Test
    public void priceMeshChairTest()
	{
    	Inventory test = new Inventory("chair","mesh",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(200, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling an executive chair is $400
    @Test
    public void priceExecutiveChairTest()
	{
    	Inventory test = new Inventory("chair","executive",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(400, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
    
  //test to assure the cheapest price for assembling an ergonomic chair is $250
    @Test
    public void priceErgonomicChairTest()
	{
    	Inventory test = new Inventory("chair","ergonomic",1,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(250, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
    
  //test to assure 2 mesh chairs can not be created with the given inventory database
    @Test
    public void priceMultipleChairsTest()
	{
    	Inventory test = new Inventory("chair","mesh",2,user,pass);
    	test.initializeConnection();
        test.getInfo();
        assertEquals(999999999, Inventory.chairPrice(Inventory.chairList, Inventory.type, Inventory.amount));
	}
}