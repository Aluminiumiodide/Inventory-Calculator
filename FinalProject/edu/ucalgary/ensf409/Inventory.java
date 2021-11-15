/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Inventory
{
    public final String DBURL = "jdbc:mysql://localhost/inventory"; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
    private static Connection dbConnect;
    private static ResultSet results;
    protected static String category; //stores user input for category
    protected static String type; // stores user input for type
    protected static int amount; //stores user input for number of items requested
    public static ArrayList<Chair> chairList = new ArrayList<>(); // Stores an array of Chairs info from table from SQL
    public static ArrayList<Desk> deskList = new ArrayList<>(); // Stores an array of Desks info from table from SQL
    public static ArrayList<Lamp> lampList = new ArrayList<>(); // Stores an array of Lamps info from table from SQL
    public static ArrayList<Filing> filingList = new ArrayList<>(); // Stores an array of Filings info from table from SQL
    public static ArrayList<Manufacturer> manuList = new ArrayList<>(); // Stores an array of Manufacturers info from table from SQL
    public static List<List<String>> powerSet;
    
    public Inventory (String category, String type, int number,String username, String password)
    { // Constructor
		if(category.equals("Chair")||category.equals("chair")) {
			if(type.equals("mesh")||type.equals("Mesh")){}
			else if(type.equals("Kneeling")||type.equals("kneeling")) {}
			else if(type.equals("Task")||type.equals("task")) {}
			else if(type.equals("Excecutive")||type.equals("executive")) {}
			else if(type.equals("Ergonomic")||type.equals("ergonomic")) {}
			else {
				throw new IllegalArgumentException("Either the spelling is wrong or the type does not exist for chair");
			}
		}
		if(category.equals("Desk")||category.equals("desk")) {
			if(type.equals("Standing")||type.equals("standing")){}
			else if(type.equals("Adjustable")||type.equals("adjustable")) {}
			else if(type.equals("Traditional")||type.equals("traditional")) {}
			else {
				throw new IllegalArgumentException("Either the spelling is wrong or the type does not exist for desk");
			}
		}
		if(category.equals("Lamp")||category.equals("lamp")) {
			if(type.equals("Desk")||type.equals("desk")){}
			else if(type.equals("Study")||type.equals("study")) {}
			else if(type.equals("SwingArm")||type.equals("Swingarm")||type.equals("swingarm")||type.equals("swingArm")) {
				type = "Swing Arm";
			}
			else {
				throw new IllegalArgumentException("Either the spelling is wrong or the type does not exist for lamp."
						+ " if swing arm then enter as SwingArm");
			}
		}
		if(category.equals("Filing")||category.equals("filing")) {
			if(type.equals("Small")||type.equals("small")){}
			else if(type.equals("Medium")||type.equals("medium")) {}
			else if(type.equals("Large")||type.equals("large")) {}
			else {
				throw new IllegalArgumentException("Either the spelling is wrong or the type does not exist for filing");
			}
		}
    	Inventory.category = category;
        Inventory.type = type;
        Inventory.amount = number;
        this.USERNAME = "ali";
        this.PASSWORD = "ensf409";
    }
    
    //main method to prompt the user for their requirements, and also collects information from the data base
    public static void main(String[] args) throws ClassNotFoundException{
        Scanner myObj = new Scanner(System.in);
        String username;
        String password;
        
        System.out.println("What is your Username?"); // Promts the user for their SQL username
        username = myObj.nextLine();
        System.out.println("What is your Password?"); // Promts the user for their SQL password
        password = myObj.nextLine();
        
        System.out.println("What is the category?"); // Promts the user for the category of the product
        category = myObj.nextLine();
        System.out.println("What is the Type? (enter as one word ie: swing arm -> swingarm)");    // Promts the user for the type of the product
        type = myObj.nextLine();
        System.out.println("How many would you like?"); // Promts the user for the ammount of the product they would like
        int a;
        try{
            try {
                a = Integer.parseInt(myObj.nextLine());
            }catch(Exception x) {
                throw new IllegalArgumentException("Please enter an integer number");
            }
            amount = a;

        }catch(Exception e){
            throw new IllegalArgumentException("Not a number");
        }
        if(amount < 1) {
            throw new IllegalArgumentException("Please request furnitures above 0");
        }

        System.out.println("You want "+ amount + " "+ type+" "+category+"(s)\n");
        Inventory order = new Inventory(category,type,amount,username,password);
        myObj.close();
        Class.forName("com.mysql.jdbc.Driver");
        order.initializeConnection();
        order.getInfo();   
        if (category.equalsIgnoreCase("Chair"))
        	System.out.println(chairPrice(chairList, type, amount));
        else if (category.equalsIgnoreCase("Desk"))
        	System.out.println(deskPrice(deskList, type, amount));
        else if (category.equalsIgnoreCase("Filing"))
        	System.out.println(filingPrice(filingList, type, amount));
        else if (category.equalsIgnoreCase("Lamp"))
        	System.out.println(lampPrice(lampList, type, amount));
    }
    
    public void initializeConnection(){ // Initializes Connection to SQL
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getInfo()
    { // Retrieves relavent information from SQL database and transfers that data to the appropiate ArrayLists 
        try{               // (ex. Chair Table into chairList of type ArrayList<Chair>)
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM Manufacturer");
            while(results.next()){
                Manufacturer temp = new Manufacturer(results.getString("ManuID"),results.getString("Name"),
                		results.getString("Phone"),results.getString("Province"));
                manuList.add(temp);
            }
            myStmt.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        try {                    
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM "+ category);
            if(category.equalsIgnoreCase("chair")){
                while (results.next()){
                    Chair temp = new Chair(results.getString("ID"),results.getString("Type"),results.getInt("Price"),
                    		results.getString("ManuID"),results.getString("Legs").charAt(0),results.getString("Arms").charAt(0),
                    		results.getString("Seat").charAt(0),results.getString("Cushion").charAt(0));
                    chairList.add(temp);
                }
            }
            else if (category.equalsIgnoreCase("desk")){
                while (results.next()){
                    Desk temp = new Desk(results.getString("ID"),results.getString("Type"),results.getInt("Price"),
                    		results.getString("ManuID"),results.getString("Legs").charAt(0),results.getString("Top").charAt(0),
                    		results.getString("Drawer").charAt(0));
                    deskList.add(temp);
                }
            }
            else if (category.equalsIgnoreCase("filing")){
                while (results.next()){
                    Filing temp = new Filing(results.getString("ID"),results.getString("Type"),results.getInt("Price"),
                    		results.getString("ManuID"),results.getString("Rails").charAt(0),results.getString("Drawers").charAt(0),
                    		results.getString("Cabinet").charAt(0));
                    filingList.add(temp);
                }
            }
            else if (category.equalsIgnoreCase("lamp")){
                while (results.next()){
                    Lamp temp = new Lamp(results.getString("ID"),results.getString("Type"),results.getInt("Price"),
                    		results.getString("ManuID"),results.getString("Base").charAt(0),results.getString("Bulb").charAt(0));
                    lampList.add(temp);
                }
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
	@param inputList array list containing all the Chair items
	@param type String to specify what type of item the user wants
	@param amount int to specify number of items the user wants
	*/
    //The price method calculates the cheapest price with the help of other methods
    public static <T> int chairPrice (ArrayList <T> inputList, String type, int amount)
    {
    	int size = 0;
    	int price = 999999999;	//max number an int can hold as default price, so anything cheaper than that is a possible solution
    	ArrayList <T> containsMyType = new ArrayList<>();	//new arraylist containing just items of users specified type
    	
    	for (int i = 0; i < inputList.size(); i++)
    	{
    		T tempChair = inputList.get(i);
    		if (type.equalsIgnoreCase(((Furniture)tempChair).getType()))
    		{
    			size++;
    			containsMyType.add(tempChair);
    		}
    	}
    	
    	String [] array = new String [size];
    	for (int i = 0; i < containsMyType.size(); i++)
    	{
    		T tempChair = (T)containsMyType.get(i);
    		array[i] = ((Furniture)tempChair).getID();
    	}   	
    	combinations(array);	//generates power set of all items in our containsMyType array list

    	List<T> comboToBuy = new LinkedList<T>();
        for (int i = 0; i < powerSet.size(); i++)
        {	//calculates the price of each combination
        	List<T> tempCombo = new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	int tempPrice = chairCalculate(containsMyType, tempCombo, amount);
        	if (tempPrice < price)	//if new combo produces lower price, save information
        	{
        		price = tempPrice;
        		comboToBuy = (List<T>) new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	}
        }
        
        Object[] ourOrder = comboToBuy.toArray();
        formatOutput (ourOrder, type, price, amount);	//calling method to create output file
        for(int i=0; i < ourOrder.length; i++)	//iterative loop going through and deleting from the data base all items that were used
            deleteFromDatabase((String)ourOrder[i]);
        return price;
    }
    
    /**
	@param containsMyType array list containing only the Chair items of users specified type
	@param tempCombo array list containing the ID's of the temporary combination from the power set
	@param amount int to specify number of items we want to create
	*/
    //does the math part of calculation logic
    public static <T> int chairCalculate (ArrayList <T> containsMyType, List<T> tempCombo, int amount)
    {
    	int price = 999999999;
    	int tempPrice = 0;
		int legs = 0;	//all item componentes initialized to 0
		int arms = 0;
		int seat = 0;
		int cushion = 0;
    	for (int i = 0; i < tempCombo.size(); i++)
    	{
    		String tempID = (String) tempCombo.get(i);
    		for (int j = 0; j < containsMyType.size(); j++)
    		{
    			T tempItem = containsMyType.get(j);
    			if (tempID == ((Furniture)tempItem).getID())
    			{
    				if (((Chair)tempItem).getLegs() == 'Y')	//increment components if item contains it
    	    			legs++;
    	    		if (((Chair)tempItem).getArms() == 'Y')
    	    			arms++;
    	    		if (((Chair)tempItem).getSeat() == 'Y')
    	    			seat++;
    	    		if (((Chair)tempItem).getCushion() == 'Y')
    	    			cushion++;
    	    		tempPrice += ((Furniture)tempItem).getPrice();
    	    		break;
    			}
    		}
    	}
    	//if combination fulfils order, set temp price as new price (saves price)
    	if (legs >= amount && arms >= amount && seat >= amount && cushion >= amount){
			price = tempPrice;
		}
    	return price;
    }
    
    //same as chairPrice
    public static <T> int deskPrice (ArrayList <T> inputList, String type, int amount)
    {
    	int size = 0;
    	int price = 999999999;
    	ArrayList <T> containsMyType = new ArrayList<>();
    	
    	for (int i = 0; i < inputList.size(); i++)
    	{
    		T tempDesk = inputList.get(i);
    		if (type.equalsIgnoreCase(((Furniture)tempDesk).getType()))
    		{
    			size++;
    			containsMyType.add(tempDesk);
    		}
    	}
    	
    	String [] array = new String [size];
    	for (int i = 0; i < containsMyType.size(); i++)
    	{
    		T tempDesk = (T)containsMyType.get(i);
    		array[i] = ((Furniture)tempDesk).getID();
    	}   	
    	combinations(array);

    	List<T> comboToBuy = new LinkedList<T>();
        for (int i = 0; i < powerSet.size(); i++)
        {
        	List<T> tempCombo = new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	int tempPrice = deskCalculate(containsMyType, tempCombo, amount);
        	if (tempPrice < price)
        	{
        		price = tempPrice;
        		comboToBuy = (List<T>) new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	}
        }
        
        Object[] ourOrder = comboToBuy.toArray();
        formatOutput (ourOrder, type, price, amount);
        for(int i=0; i < ourOrder.length; i++)
            deleteFromDatabase((String)ourOrder[i]);
        return price;
    }
    
    //same as chairCalculate
    public static <T> int deskCalculate (ArrayList <T> containsMyType, List<T> tempCombo, int amount)
    {
    	int price = 999999999;
    	int tempPrice = 0;
		int legs = 0;
		int top = 0;
		int drawers = 0;
    	for (int i = 0; i < tempCombo.size(); i++)
    	{
    		String tempID = (String) tempCombo.get(i);
    		for (int j = 0; j < containsMyType.size(); j++)
    		{
    			T tempItem = containsMyType.get(j);
    			if (tempID == ((Furniture)tempItem).getID())
    			{
    				if (((Desk)tempItem).getLegs() == 'Y')
    	    			legs++;
    	    		if (((Desk)tempItem).getTop() == 'Y')
    	    			top++;
    	    		if (((Desk)tempItem).getDrawers() == 'Y')
    	    			drawers++;
    	    		tempPrice += ((Furniture)tempItem).getPrice();
    	    		break;
    			}
    		}
    	}
    	if (legs >= amount && top >= amount && drawers >= amount){
			price = tempPrice;
		}
    	return price;
    }
    
  //same as chairPrice
    public static <T> int filingPrice (ArrayList <T> inputList, String type, int amount)
    {
    	int size = 0;
    	int price = 999999999;
    	ArrayList <T> containsMyType = new ArrayList<>();
    	
    	for (int i = 0; i < inputList.size(); i++)
    	{
    		T tempFiling = inputList.get(i);
    		if (type.equalsIgnoreCase(((Furniture)tempFiling).getType()))
    		{
    			size++;
    			containsMyType.add(tempFiling);
    		}
    	}
    	
    	String [] array = new String [size];
    	for (int i = 0; i < containsMyType.size(); i++)
    	{
    		T tempFiling = (T)containsMyType.get(i);
    		array[i] = ((Furniture)tempFiling).getID();
    	}   	
    	combinations(array);

    	List<T> comboToBuy = new LinkedList<T>();
        for (int i = 0; i < powerSet.size(); i++)
        {
        	List<T> tempCombo = new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	int tempPrice = filingCalculate(containsMyType, tempCombo, amount);
        	if (tempPrice < price)
        	{
        		price = tempPrice;
        		comboToBuy = (List<T>) new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	}
        }
        
        Object[] ourOrder = comboToBuy.toArray();
        formatOutput (ourOrder, type, price, amount);
        for(int i=0; i < ourOrder.length; i++)
            deleteFromDatabase((String)ourOrder[i]);
        return price;
    }
    
  //same as chairCalculate
    public static <T> int filingCalculate (ArrayList <T> containsMyType, List<T> tempCombo, int amount)
    {
    	int price = 999999999;
    	int tempPrice = 0;
		int rails = 0;
		int drawers = 0;
		int cabinet = 0;
    	for (int i = 0; i < tempCombo.size(); i++)
    	{
    		String tempID = (String) tempCombo.get(i);
    		for (int j = 0; j < containsMyType.size(); j++)
    		{
    			T tempItem = containsMyType.get(j);
    			if (tempID == ((Furniture)tempItem).getID())
    			{
    				if (((Filing)tempItem).getRails() == 'Y')
    					rails++;
    	    		if (((Filing)tempItem).getDrawers() == 'Y')
    	    			drawers++;
    	    		if (((Filing)tempItem).getCabinet() == 'Y')
    	    			cabinet++;
    	    		tempPrice += ((Furniture)tempItem).getPrice();
    	    		break;
    			}
    		}
    	}
    	if (rails >= amount && drawers >= amount && cabinet >= amount){
			price = tempPrice;
		}
    	return price;
    }

  //same as chairPrice
    public static <T> int lampPrice (ArrayList <T> inputList, String type, int amount)
    {
    	int size = 0;
    	int price = 999999999;
    	ArrayList <T> containsMyType = new ArrayList<>();
    	
    	for (int i = 0; i < inputList.size(); i++)
    	{
    		T tempLamp = inputList.get(i);
    		if (type.equalsIgnoreCase(((Furniture)tempLamp).getType()))
    		{
    			size++;
    			containsMyType.add(tempLamp);
    		}
    	}
    	
    	String [] array = new String [size];
    	for (int i = 0; i < containsMyType.size(); i++)
    	{
    		T tempLamp = (T)containsMyType.get(i);
    		array[i] = ((Furniture)tempLamp).getID();
    	}   	
    	combinations(array);

    	List<T> comboToBuy = new LinkedList<T>();
        for (int i = 0; i < powerSet.size(); i++)
        {
        	List<T> tempCombo = new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	int tempPrice = lampCalculate(containsMyType, tempCombo, amount);
        	if (tempPrice < price)
        	{
        		price = tempPrice;
        		comboToBuy = (List<T>) new LinkedList<T>((Collection<? extends T>) powerSet.get(i));
        	}
        }
        
        Object[] ourOrder = comboToBuy.toArray();
        formatOutput (ourOrder, type, price, amount);
        for(int i=0; i < ourOrder.length; i++)
            deleteFromDatabase((String)ourOrder[i]);
        return price;
    }
    
  //same as chairCalculate
    public static <T> int lampCalculate (ArrayList <T> containsMyType, List<T> tempCombo, int amount)
    {
    	int price = 999999999;
    	int tempPrice = 0;
		int base = 0;
		int bulb = 0;
    	for (int i = 0; i < tempCombo.size(); i++)
    	{
    		String tempID = (String) tempCombo.get(i);
    		for (int j = 0; j < containsMyType.size(); j++)
    		{
    			T tempItem = containsMyType.get(j);
    			if (tempID == ((Furniture)tempItem).getID())
    			{
    				if (((Lamp)tempItem).getBase() == 'Y')
    					base++;
    	    		if (((Lamp)tempItem).getBulb() == 'Y')
    	    			bulb++;
    	    		tempPrice += ((Furniture)tempItem).getPrice();
    	    		break;
    			}
    		}
    	}
    	if (base >= amount && bulb >= amount){
			price = tempPrice;
		}
    	return price;
    }
    
    /**
	@param args array of Strings thats used to create all possible combinations with all its indices
	*/
    //the generic method responsible for outputting the power set with the use of one other helper function
    public static void combinations (String[] args)
    {
        powerSet = new LinkedList<List<String>>();
        for (int i = 1; i <= args.length; i++)
            powerSet.addAll(generating(Arrays.asList(args), i));		//calls helper function
    }
    
    /**
	@param values linked list containing all the stand alone combinations
	@param amount size to indicate the size of the list
	*/
    //generic helper method called recursively to generate power set
    public static <T> List<List<T>> generating(List<T> values, int size)
    {
        if (0 == size)
            return Collections.singletonList(Collections.<T> emptyList());

        if (values.isEmpty())
            return Collections.emptyList();

        List<List<T>> combination = new LinkedList<List<T>>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = generating(subSet, size - 1);

        for (List<T> set : subSetCombination)
        {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(generating(subSet, size));

        return combination;
    }
    
    /**
	@param itemsToBuy object array containing all the items in our order
	@param type String to specify what type of item the user wants
	@param amount int to specify number of items the user wants
	*/
    //method to write into file (successful orders or incomplete orders)
    public static void formatOutput (Object[] itemsToBuy, String type, int price, int amount)
    {	
    	try
    	{
    		if (price == 999999999)	//indicates it was a failed order because price is still the max defualt value
			{
				BufferedWriter bw = new BufferedWriter (new FileWriter("orderform.txt"));
				bw.write("User Request: " + amount + "x " + type + " " + category + " " + "\n");
				bw.write("Output: Order cannot be fulfilled based on current inventory. Suggested manufacturers are ");
				 if(category.equalsIgnoreCase("Chair"))
				 {
			            ArrayList<Chair> temp = chairList;
			            String[] chairManu = new String[20];
			            for(int i=0; i<chairList.size();i++) {
			                chairManu[i] = temp.get(i).getManuID();
			            }
			            chairManu = new HashSet<String>(Arrays.asList(chairManu)).toArray(new String[0]);

			            ArrayList<Manufacturer> manuTemp = manuList;
			            String[] manuName = new String[20];

			            for(int i=0;i<chairManu.length;i++) {
			                if(chairManu[i] != null) {
			                    int j = Integer.parseInt(chairManu[i]);
			                    if(j>0) {
			                        manuName[i] = manuTemp.get(j-1).getName();
			                        bw.write("\n" + manuName[i]);
			                    }
			                }
			            }
			     }
				 else if(category.equalsIgnoreCase("Desk")) {
			    	 ArrayList<Desk> temp = deskList;
			            String[] chairManu = new String[20];
			            for(int i=0; i<deskList.size();i++) {
			                chairManu[i] = temp.get(i).getManuID();
			            }
			            chairManu = new HashSet<String>(Arrays.asList(chairManu)).toArray(new String[0]);
			        
			            ArrayList<Manufacturer> manuTemp = manuList;
			            String[] manuName = new String[20];
			        
			            for(int i=0;i<chairManu.length;i++) {
			                if(chairManu[i] != null) {
			                    int j = Integer.parseInt(chairManu[i]);
			                    if(j>0) {
			                        manuName[i] = manuTemp.get(j-1).getName();
			                        bw.write("\n" + manuName[i]);
			                 }
			             }
			         }
			     }
				 else if(category.equalsIgnoreCase("Lamp")) {
			            ArrayList<Lamp> temp = lampList;
			            String[] chairManu = new String[20];
			            for(int i=0; i<lampList.size();i++) {
			                chairManu[i] = temp.get(i).getManuID();
			            }
			            chairManu = new HashSet<String>(Arrays.asList(chairManu)).toArray(new String[0]);
			        
			            ArrayList<Manufacturer> manuTemp = manuList;
			            String[] manuName = new String[20];
			        
			            for(int i=0;i<chairManu.length;i++) {
			                if(chairManu[i] != null) {
			                    int j = Integer.parseInt(chairManu[i]);
			                    if(j>0) {
			                        manuName[i] = manuTemp.get(j-1).getName();
			                        bw.write("\n" + manuName[i]);
			                    }
			                }
			            }
			        }
				 else if(category.equalsIgnoreCase("Filing")) {
			            ArrayList<Filing> temp = filingList;
			            String[] chairManu = new String[20];
			            for(int i=0; i<filingList.size();i++) {
			                chairManu[i] = temp.get(i).getManuID();
			            }
			            chairManu = new HashSet<String>(Arrays.asList(chairManu)).toArray(new String[0]);
			        
			            ArrayList<Manufacturer> manuTemp = manuList;
			            String[] manuName = new String[20];
			        
			            for(int i=0;i<chairManu.length;i++) {
			                if(chairManu[i] != null) {
			                    int j = Integer.parseInt(chairManu[i]);
			                    if(j>0) {
			                        manuName[i] = manuTemp.get(j-1).getName();
			                        bw.write("\n" + manuName[i]);
			                    }
			                }
			            }
			        }
			        bw.close();
			    }
    		else
    		{	//file output for successful orders
    			BufferedWriter bw = new BufferedWriter (new FileWriter("orderform.txt"));
        		bw.write("Furniture Order Form\n\n");
        		bw.write("Faculty Name: " + "\n");
        		bw.write("Contact: " + "\n");
        		bw.write("Date: " + "\n\n");
        		bw.write("Original Request: " + amount + "x " + type + " " + category + "\n\n");
        		bw.write("Items Ordered\n");
        		for (int i = 0; i < itemsToBuy.length; i++)
        			bw.write("ID: " + itemsToBuy[i] + "\n");
        		bw.write("\nTotal Price: $" + price);
        		bw.close();
    		}
    	}
    	catch (Exception e)	{e.printStackTrace(); return;}
    }
    
    /**
	@param id the unique id of the item to be removed from the database
	*/
    //deleted bought items from database and updates database
    public static void deleteFromDatabase(String id) {
        try {
            String query = "DELETE FROM " +category+ " WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, id);
                        
            int rowCount = myStmt.executeUpdate();
            System.out.println("Deleted "+id);
            
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}