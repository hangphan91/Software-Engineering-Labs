package lab7out;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class DatabaseFile
{
	private ArrayList<Integer> Id = new ArrayList<Integer>();
	private HashMap<String,String> account;
	private HashMap<String,Integer> account_ID = new HashMap<String, Integer>();
	  
	


	public DatabaseFile()  {
				
		 account = new HashMap<String,String>(); 

	}

	public void addtoDatabase(String username, String password) throws IOException, SQLException {
		 String command ="insert into user values('" + username + "',aes_encrypt('" + password + "','key'));";
	// "insert into user values('jsmith@uca.edu',aes_encrypt('hello123','key'))" D
		 Database database = new Database();
		if (!account.containsKey(username)) {
			 account.put(username, password);
			 
			
			    
			    String type = "D";
			    
			    System.out.println("command" + command);
			    System.out.println("type" + type);
			    // Create the database object.
			   
			    
			    // Execute a query if Q was specified.
			    if (type.equals("D")) {
			    	   // Run the DML.
				      try
				      {
				         database.executeDML(command);
				   
				       }
				       catch(SQLException sql)
				       {
				           System.out.println("Error executing DML.");
				        }
			    
			    }
			 
		 }
		 else {
			 System.out.println("account exists");
		 }
		 
	}
	 public boolean validateAccount(String username, String pass) throws IOException, SQLException {
		 getMap();
		 System.out.println("user" + username + "pass" + pass);
		 System.out.println("accountssss " +account);
			  Iterator it = account.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pair = (Map.Entry)it.next();
			        System.out.println("user name from map: " + pair.getKey() + " pass: " + pair.getValue());
			        if (pair.getKey().equals(username) && pair.getValue().equals(pass)) {
			        	 System.out.println("trueeeeeeee");
			        	
			        	return true; // matched user name and password
			        	//return true mean, there exists the account and password
			        }
			    }

		 return false;
	 }
	  
	
	 
	 public boolean validateUsername(String username) {
		System.out.println("list of all account and pass words"+ account);
		 Iterator it = account.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if (pair.getKey().equals(username)) {
		        	 System.out.println("falseeeee");
		        	return false;//return true mean, there exists the account
		        }
		    }
		    System.out.println("trueeeeeeee");
		 return true;
	 }
	 
	 
	public HashMap<String,String> getMap() throws IOException, SQLException {
		Database database = new Database();
		account = database.query("select * from user");
		System.out.println(" list of all account and passwords "+account);
		return account;
	}
	
	public int generateID(String username) {
		Random rand = new Random();
		int id  = rand.nextInt(20000);
		if (account_ID.containsKey(username) ) {
			while  (Id.contains(id)) {
				id  = rand.nextInt(20000);
			}
			Id.add(id);
			account_ID.put(username, id);
		 }
		 else {
			 System.out.println("account created");
		 }
		
		return id;
	}
	
}
