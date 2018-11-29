package lab7out;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientGUI extends JFrame
{
	private ChatClient chatclient = new ChatClient ();
	JPanel container;
	public ClientGUI() throws IOException {
	 
	    
	 // Set the title and default close operation.
	    this.setTitle("Chat Client");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    // Create the card layout container.
	    CardLayout cardLayout = new CardLayout();
	     container = new JPanel(cardLayout);
	    
	    //Create the Controllers next
	    //Next, create the Controllers
	    InitialControl ic = new InitialControl(container); 
	    LoginControl lc = new LoginControl(container, chatclient); //Probably will want to pass in ChatClient here
	    CreateAccountControl cap = new CreateAccountControl(container,chatclient);
	    
	   
	    
	    
	    // Create the four views. (need the controller to register with the Panels
	    JPanel view1 = new InitialPanel(ic);
	    JPanel view2 = new LoginPanel(lc);
	    JPanel view3 = new CreateAccountPanel(cap);
	    JPanel view4 = new ContactPanel(container, chatclient);
	    GameBoard view5 = new GameBoard( container, chatclient);
	    
	    chatclient.setGameboard(view5);
	    
	    // Add the views to the card layout container.
	    container.add(view1, "1");
	    container.add(view2, "2");
	    container.add(view3, "3");
	    container.add(view4, "4");
	    container.add(view5, "5");
	   
	    
	    // Show the initial view in the card layout.
	    cardLayout.show(container, "4");
	    
	    // Add the card layout container to the JFrame.
	    this.add(container, BorderLayout.CENTER);

	    // Show the JFrame.
	    this.setSize(850, 550);
	    this.setVisible(true);
	    // oepn connection for the socket
	 
	    
	  }

	  // Main function that creates the client GUI when the program is started.
	  public static void main(String[] args) throws IOException
	  {
	    new ClientGUI();
	  }
	
}
