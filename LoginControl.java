package lab7out;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import ocsf.client.*;

public class LoginControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private ChatClient chatclient;
  
  
  // Constructor for the login controller.
  public LoginControl(JPanel container, ChatClient chatclient)
  {
    this.container = container;
   this.chatclient = chatclient;
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
	  
 		
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

  try
 	{
	  chatclient.openConnection();
 	} catch (IOException e1)
 	{
 		// TODO Auto-generated catch block
 		e1.printStackTrace();
 	}

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }
    
    
    
    
    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      LoginData data = new LoginData();
      data.setPassword(loginPanel.getPassword());
      data.setUsername(loginPanel.getUsername());
		/////System.out.println("account + pass "); 

      // Check the validity of the information locally first.
      if (data.getUsername().equals("") || data.getPassword().equals(""))
      {
        displayError("You must enter a username and password.");
        return;
      }

      
      // Submit the login information to the server.
     
     try
	{
    	 //send data to server
		chatclient.sendToServer(data);
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     System.out.println("login control message from server: " +chatclient.msg_from_server());
     try
	{
		Thread.sleep(1000);
	} catch (InterruptedException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     if (chatclient.msg_from_server())
      {     	
    	 loginSuccess();
    	 CardLayout cardLayout = (CardLayout)container.getLayout();
    	 cardLayout.show(container, "4");
     }
     else {
    	 displayError("You must enter a valid username and password.");
     }
     
     
    }
  }

  // After the login is successful, set the User object and display the contacts screen. - this method would be invoked by 
  //the ChatClient
  public void loginSuccess()
  {
    System.out.println("login succeses");
   
    
  }

  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayError(String error)
  {
    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    loginPanel.setError(error);
    
  }
}
