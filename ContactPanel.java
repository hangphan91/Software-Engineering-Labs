package lab7out;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactPanel extends JPanel
{
	private JPanel container;
	private ChatClient chatclient;
	public ContactPanel(JPanel container, ChatClient chatclient) {
		this.container = container;
		this.chatclient = chatclient;
		
		//create panel for label
		JPanel myPanel = new JPanel();
	 	JPanel labelPanel = new JPanel(new FlowLayout());
	 	
	    
	    JLabel instructionLabel = new JLabel("Contacts");
	    labelPanel.add(instructionLabel);
	    JPanel textPanel = new JPanel(new FlowLayout());

	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton deleteButton = new JButton("Play BattleShip");
	    deleteButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		try
				{
					chatclient.sendToServer("Play");
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	       	 CardLayout cardLayout = (CardLayout)container.getLayout();
	    	 cardLayout.show(container, "5");
	    	 
	    	}
	    });
	    JButton logoutButton = new JButton("Log Out");
	   
	    buttonPanel.add(deleteButton);
	    buttonPanel.add(logoutButton);
	    
	    
	    JPanel grid = new JPanel(new BorderLayout());
	    grid.add(labelPanel,BorderLayout.NORTH );
	    grid.add(textPanel, BorderLayout.CENTER);
	    grid.add(buttonPanel,BorderLayout.SOUTH);
	    myPanel.add(grid);
	    this.add(myPanel);
}
}
