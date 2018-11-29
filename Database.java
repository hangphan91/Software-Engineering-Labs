package lab7out;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.util.*;



public class Database
{
	private Connection conn;
	private   HashMap<String,String> query_here = new HashMap<String,String>();

	private  Statement stmt;
	private  ResultSet result;
	private  ResultSetMetaData rmd;
	  //Add any other data fields you like – at least a Connection object is mandatory
	  public Database() throws IOException
	  {
	    //Add your code here
		    Statement stmt;
		    ResultSet rs;
		    ResultSetMetaData rmd;
		    
		    //Read properties file
		    Properties prop = new Properties();
		    FileInputStream fis = new FileInputStream("lab7out/db.properties");
		    prop.load(fis);
		    String url = prop.getProperty("url");
		    String user = prop.getProperty("user");
		    String pass = prop.getProperty("password");    
		    
		    
		    try
		    {
		      //Read the connection properties as Strings
		    
		      
		      //Perform the Connection
		      conn = DriverManager.getConnection(url,user,pass);

		    } 
		    catch (SQLException e)
		    {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    } 
		  
	  }
	  
	  public HashMap<String,String> query(String query) throws SQLException
	  {


	      stmt=conn.createStatement();  
	      
	      //Execute a DML statement
	  
	    
	      //Execute a query
	      result=stmt.executeQuery(query);  
	      
	      //Get metadata about the query
	      rmd = result.getMetaData();
	      //Get the # of columns
	      int no_columns = rmd.getColumnCount();
	    
	      //Get a column name
	      String name = rmd.getColumnName(1);
	      
	      //Fetch each row (use numeric numbering
	      while(result.next()) 
	      {
	    	 query_here.put(result.getString(1), result.getString(2));
	    	  // query_here.add(result.getString(1)+","+result.getString(2));
	    	 // System.out.println(result.getString(1)+"  "+result.getString(2)+"  "+result.getString(3));
	    	  
	      }
	      
	     
		  
		  //Add your code here
		 return query_here;
	  }
	  
	  public void executeDML(String dml) throws SQLException
	  {
	    //Add your code here

	      stmt=conn.createStatement();  
	      
	      //Execute a DML statement
	      boolean rs = false;
	    
	      //Execute a query
	      rs=stmt.execute(dml);  
	   
	    	  System.out.println("SUCCESS!!!!!!!!");
	    	  conn.close();

		  
	  }

}
