package lab7out;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.*;
import javax.swing.*;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatServer  extends AbstractServer 
{
	private JTextArea log;
	private JLabel status;
	private DatabaseFile database = new DatabaseFile(); 
	//private User user = new User();
	//private LoginData loginData = new LoginData();
	private CreateAccountData accountData = new CreateAccountData();
	private CreateAccountData createAccountData;
	private ArrayList<ConnectionToClient> clients = new ArrayList<ConnectionToClient>();
	private LoginData loginData;
	private String username;
	private  String password;
	private boolean result;
	private User user;
	private long id;
	private ArrayList<Long>  ids = new ArrayList<Long>();
	int play =0;
	private ArrayList<Integer> client1;
	private ArrayList<Integer> client2;

	public ChatServer()
	{
		super(12345);
		clients.clear();
	}

	public ChatServer(int port)
	{
		super(port);
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	}



	public CreateAccountData getAccountData()
	{
		return accountData;
	}

	public void setAccountData(CreateAccountData accountData)
	{
		this.accountData = accountData;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public DatabaseFile getDatabase() throws IOException, SQLException
	{
		database.getMap();
		return database;
	}

	public void setDatabase(DatabaseFile database)
	{
		this.database = database;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		System.out.println("message sent to server: ");
		// TODO Auto-generated method stub
		System.out.println("Message from Client" + arg0.toString() + arg1.toString());
		if(arg0.toString().startsWith("position")) {
			ConnectionToClient client2 =	getAnotherClient(arg1);
			try
			{
				client2.sendToClient(arg0);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(arg0.toString().equals("Play")) {
			play +=1;
			if(play==2) {
				if(clients.size() ==2) {
					//Board from_client = new Board();
					// this is to send from server to other client, which is not client arg1. clients are from different computers
					Board to_client = new Board();
					Board2 to_client2 = new Board2();

					//client1
					int i =0;
					try
					{
						clients.get(i).sendToClient(to_client);
						client1 = to_client.getList();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					System.out.println("send board to client"+ to_client.getList());
					//client2
					i=1;
					try
					{
						clients.get(i).sendToClient(to_client2);
						client2 = to_client2.getList();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					System.out.println("send board to client"+ to_client2.getList());

				}
			}
		}
		//log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");
		if (arg0 instanceof LoginData)
		{

			loginData = (LoginData)arg0;
			String username = loginData.getUsername();
			String password = loginData.getPassword();
			System.out.println("Username/Password: " + loginData.getUsername() + " " + loginData.getPassword());
			boolean result = false;
			try
			{
				result = database.validateAccount(username, password);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try
			{
				arg1.sendToClient(result);


			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		if (arg0 instanceof CreateAccountData)
		{
			accountData = (CreateAccountData)arg0;
			String username = accountData.getNewAccount();
			String password = accountData.getNewPass();
			System.out.println("Username/Password: " + username + " " + password);
			boolean result = database.validateUsername(username);
			System.out.println("validate rsulr  create account: " + result);
			try
			{

				arg1.sendToClient(result);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		if (arg0 instanceof User)
		{
			user = (User)arg0;
			String username = user.getUsername();
			String password = user.getPassword();
			System.out.println("Username/Password: " + username + " " + password);
			try
			{
				database.addtoDatabase(username, password);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}




//		if(clients.size() ==2) {
//			//Board from_client = (Board)arg0;
//			// this is to send from server to other client, which is not client arg1. clients are from different computers
//
//
//			for (int i =0; i<this.clients.size();i++) {
//				if(clients.get(i)!=arg1 ) {
//					Board to_client = new Board();
//
//					try
//					{
//						clients.get(i).sendToClient(to_client);
//						System.out.println("send board to client"+ to_client.getList() + to_client.getList1());
//
//					} catch (IOException e1)
//					{
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//
//				//		    			
//			}
//		}


	}

	protected void listeningException(Throwable exception) 
	{
		//Display info about the exception
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		/*if (this.isListening())
    {
      log.append("Server not Listening\n");
      status.setText("Not Connected");
      status.setForeground(Color.RED);
    }*/

	}

	protected void serverStarted() 
	{
		System.out.println("Server Started");
		//log.append("Server Started\n");
		try
		{
			database.getMap();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void serverStopped() 
	{
		System.out.println("Server Stopped");
		//log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}

	protected void serverClosed() 
	{
		System.out.println("Server and all current clients are closed - Press Listen to Restart");
		//log.append("Server and all current clients are closed - Press Listen to Restart\n");
	}


	protected void clientConnected(ConnectionToClient client) 
	{

		System.out.println("Client Connected");

		//log.append("Client Connected\n");

		//System.out.println("Client Connected");
		// log.append("Client Connected\n");
		if(client!=null) {	
			clients.add(client);
			System.out.println("Client" +client);
		}
		System.out.println("Clients" +clients);
		long id = client.getId();
		ids.add(id);
		try
		{
			Board to_client = new Board();

			client.sendToClient(to_client);
			client.sendToClient(id);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


public ConnectionToClient getAnotherClient(ConnectionToClient client) {
	if(clients.get(0) == client) {
		return clients.get(1);
	}
	
	return clients.get(0) ;
}




}
