package lab7out;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import ocsf.client.*;
import javax.swing.JPanel;
import java.io.Serializable;
import java.io.NotSerializableException;

public class CreateAccountControl implements ActionListener
{
	// Private data fields for the container and chat client.
	private JPanel container;
	private ChatClient chatclient;


	// Constructor for the login controller.
	public CreateAccountControl(JPanel container, ChatClient chatclient)
	{
		this.container = container;
		this.chatclient = chatclient;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();
//				try
//				{
//					chatclient.openConnection();
//				} catch (IOException e1)
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
		chatclient.isConnected();
		System.out.println("IS chat client still connect to server?   " +chatclient.isConnected());


		// The Login button takes the user to the login panel.
		if (command.equals("Submit"))
		{
			// Get the username and password the user entered.


			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			String username = createAccountPanel.getUsername();
			String pass = createAccountPanel.getPassword();
			String re_pass = createAccountPanel.getRe_Password();
			CreateAccountData accountdata = new CreateAccountData();
			accountdata.setNewAccount(username);
			accountdata.setNewPass(pass);
			accountdata.setRe_newPass(re_pass);

			// Check the validity of the information locally first.
			if ( username.equals("")|| pass.equals("")|| re_pass.equals(""))
			{
				displayError("Enter new account, password and verify your password.");
				return;
			}
			//		        boolean result_user = chatserver.getDatabase().validateUsername(username);
			//		        if(result_user) {
			//		        	displayError("Account exists, Choose another account.");
			//		        }
			if(pass.length()<6) {
				displayError("Password has to be at least 6 characters");
				return;
			}
			if(!pass.equals(re_pass)) {
				displayError("Verify Password is not correct.");
				return;
			}

			try
			{
				
				chatclient.sendToServer(accountdata);
				//chatclient.sendToServer(accountdata);
				System.out.println("send account data to server");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean rs = chatclient.msg_from_server();
			System.out.println("msg from server" + rs);
			if(!rs) {
				displayError("Username has already been selected");
			}
			else {
				System.out.println("create new user successed");
				this.createSuccess();
				User newuser = new User();
				newuser.setUsername(username);
				newuser.setPassword(pass);

				//send new account to database in chatserver
				try
				{
					chatclient.sendToServer(newuser);

					System.out.println("send newuser  to server");
				} catch (IOException ea)
				{
					// TODO Auto-generated catch block
					ea.printStackTrace();
				}


			}


		
			}
			


		// The Create button takes the user to the create account panel.
		else if (command.equals("Cancel"))
		{
			//Handle CreatAccount Here
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
	}
	public void createSuccess()
	{
		System.out.println("create succeses");


		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "2");

	}

	// Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	public void displayError(String error)
	{
		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		createAccountPanel.setError(error);

	}
}
