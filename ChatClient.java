package lab7out;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient
{
	private GameBoard gameboard;
  public void setGameboard(GameBoard gameboard)
	{
		this.gameboard = gameboard;
	}
private boolean msg_from_server;
  public ChatClient()
  {
    super("localhost",8300);
    System.out.println("port" + 8300);
    try
   	{
   		this.openConnection();
   	} catch (IOException e1)
   	{
   		// TODO Auto-generated catch block
   		e1.printStackTrace();
   	}
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
    System.out.println("Server Message sent to Client "  +(boolean)arg0);
   
    msg_from_server = (boolean)arg0;
    if(arg0.toString().startsWith("position")) {
    	int position = Integer.parseInt(arg0.toString().substring(7));
    	gameboard.disableABox(position);
    }
    
    if (arg0 instanceof Board) {
    	Board2 b = new Board2();
    	Board from_server = (Board)arg0;
    	ArrayList<Integer> array = from_server.getList();
		gameboard.setshipLocations(array);
//		array = from_server.getList();
		gameboard.setopponentShips(b.getList());
		System.out.println(" board in client"+ from_server.getList());
    }
    if (arg0 instanceof Board2) {
    	Board b = new Board();
    	Board2 from_server = (Board2)arg0;
    	ArrayList<Integer> array = from_server.getList();
		gameboard.setshipLocations(array);
//		array = from_server.getList();
		gameboard.setopponentShips(b.getList());
		System.out.println(" board in client"+ from_server.getList());
    }

  }
  public boolean msg_from_server() {
	  System.out.println("Server Message sent to Client in msg_from_server() "  +msg_from_server);
	  return msg_from_server;
	  
  }
  
  public void connectionException (Throwable exception) 
  {
    //Add your code here
	  System.out.println(" throwed exception");

  }
  public void connectionEstablished()
  {
    //Add your code here
	  System.out.println("Connection established");
  }
  

}