package lab7out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountPanel extends JPanel
{ 
	  private JTextField usernameField;
	  private JPasswordField passwordField;
	  private JPasswordField verify_passwordField;
	  private JLabel errorLabel;
	  
	  public String getUsername()
	  {
	    return usernameField.getText();
	  }
	  
	  // Getter for the text in the password field.
	  public String getPassword()
	  {
	    return new String(passwordField.getPassword());
	  } 
	  public String getRe_Password()
	  {
	    return new String(verify_passwordField.getPassword());
	  } 
	   //Setter for the error text.
	  public void setError(String error)
	  {
	    errorLabel.setText(error);
	  }
	  
	public CreateAccountPanel(CreateAccountControl cap) {
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 2, 0));
	 
	    JLabel instructionLabel1 = new JLabel("Enter your username and password to create an account", JLabel.CENTER);
	    JLabel instructionLabel2 = new JLabel("Your password must be at least 6 characters", JLabel.CENTER);

	    labelPanel.add(instructionLabel1);
	    labelPanel.add(instructionLabel2);

	    // Create a panel for the login information form.
	    JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
	    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
	    usernameField = new JTextField(10);
	    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
	    passwordField = new JPasswordField(10);
	    JLabel verify_passwordLabel= new JLabel("Verify Password:", JLabel.RIGHT);
	    verify_passwordField = new JPasswordField(10);
	    loginPanel.add(usernameLabel);
	    loginPanel.add(usernameField);
	    loginPanel.add(passwordLabel);
	    loginPanel.add(passwordField);
	    loginPanel.add(verify_passwordLabel);
	    loginPanel.add(verify_passwordField);
	    
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(cap);
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(cap);    
	    buttonPanel.add(submitButton);
	    buttonPanel.add(cancelButton);
	    // Create a panel for errors
	    JPanel labelPanel2 = new JPanel(new GridLayout(2, 1, 5, 5));
	    errorLabel = new JLabel("", JLabel.CENTER);
	    errorLabel.setForeground(Color.RED);
	    labelPanel2.add(errorLabel);
	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new BorderLayout());
	    grid.add(labelPanel, BorderLayout.NORTH);
	    grid.add(loginPanel, BorderLayout.CENTER);
	    grid.add(buttonPanel, BorderLayout.SOUTH);
	    JPanel myPanel = new JPanel(new BorderLayout());
	    myPanel.add(grid, BorderLayout.NORTH);
	    myPanel.add(labelPanel2,BorderLayout.SOUTH);
	    this.add(myPanel);
	}
}
