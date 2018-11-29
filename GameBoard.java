package lab7out;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;



import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class GameBoard extends JPanel
{
	private JPanel container;
	private ChatClient chatclient;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private ArrayList<JButton> btt_opponent = new ArrayList<JButton>();
	private ArrayList<JButton> disable = new ArrayList<JButton>();

	private ArrayList<Integer> pick_locations = new ArrayList<Integer>();
	private ArrayList<Integer> btt_hits = new ArrayList<Integer>();

	private ArrayList<Integer> oppent_locations = new ArrayList<Integer>();

	JPanel panel = new JPanel();
	JPanel opponent = new JPanel();

	private final JPanel player = new JPanel();

	public GameBoard(JPanel container, ChatClient chatclient) throws IOException {
		this.container =container;
		this.chatclient = chatclient;
		buttons.clear();



		int x = 0;
		int y = 0;

		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j <10; j++)
			{
				JButton btnNewButton = new JButton("");
				btnNewButton.setBounds(x, y, 30, 30);
				buttons.add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("button hhitttt!!!");
						JButton btt_hit = (JButton) e.getSource();
						if(buttons.contains(btt_hit))	{
							int a = buttons.indexOf(btt_hit);

							if(pick_locations.contains(a)) {
								System.out.println("location chosen!!!" +  a);
								btt_hit.setBackground(Color.RED);
								btt_hit.setForeground(Color.BLACK);
								btt_hit.setText("X");
								panel.repaint();
							}
						}

					}
				});
				player.add(btnNewButton);
				x += 30;
			}
			x = 0;
			y += 30;
		}
		setLayout(new BorderLayout(0, 0));
		x = 0;
		y = 0;
		//getContentPane().add(panel_1);
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j <10; j++)
			{
				JButton btnNewButton = new JButton("");
				btt_opponent.add(btnNewButton);
				btnNewButton.setBounds(x, y, 30, 30);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("button hhitttt!!!");
						JButton btt_hit = (JButton) e.getSource();
						int a =-1;
						if(btt_opponent.contains(btt_hit))	{
							a = btt_opponent.indexOf(btt_hit);
							//						btt_indices.add(a);

							if(oppent_locations.contains(a)) {
								System.out.println("location chosen!!!" +  a);
								btt_hit.setBackground(Color.RED);
								btt_hit.setOpaque(true);
								btt_hit.setBorderPainted(false);
								btt_hit.setForeground(Color.DARK_GRAY);
								btt_hit.setText("X");
								btt_hits.add(a);
								panel.repaint();
								btt_opponent.get(a).setEnabled(false);
							}
						}
						disable.add(btt_hit);
						disableOpponentBoard();
						allHit(); 
						try
						{
							chatclient.sendToServer("position"+a);
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				opponent.add(btnNewButton);
				x += 30;
			}
			x = 0;
			y += 30;
		}


		panel.setBounds(60, 70, 300, 300);

		//getContentPane().add(panel);
		panel.setLayout(null);

		this.add(panel);
		player.setBounds(105, 75, 300, 300);


		panel.add(player);
		player.setLayout(null);
		panel.add(opponent);
		opponent.setBounds(498, 75, 300, 300);

		opponent.setLayout(null);



		JLabel lblYourBoard = new JLabel("Your Board");
		panel.add(lblYourBoard);
		lblYourBoard.setBounds(219, 25, 80, 16);

		JLabel lblOpponentsBoard = new JLabel("Opponent's Board");
		panel.add(lblOpponentsBoard);
		lblOpponentsBoard.setBounds(595, 25, 122, 16);
		this.add(panel);

		Board board = new Board();


	}
	//	  public static void main(String[] args)
	//	  {	  
	//		  GameBoard board =  new GameBoard();  
	//		  this.setTitle("Chat Client");
	//		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		//  panel.setEnabled(false);
	//		  board.shipLocations();
	//		  board.opponentShips();
	//		  board.setSize(800, 500);
	//		  board.setVisible(true);
	//	  }
	public void setshipLocations( ArrayList<Integer > list2) {

		//I’m using ships of lengths: 5, 4, 3, 3, 2

		this.pick_locations = list2;
		for(int i = 0; i<pick_locations.size() ; i++) {
			int index =  list2.get(i);
			buttons.get(index).setBackground(Color.DARK_GRAY);
			buttons.get(index).setOpaque(true);
			buttons.get(index).setBorderPainted(false);
		}
		for(int i = 0; i<buttons.size() ; i++) {
			buttons.get(i).setEnabled(false);
		}
		this.repaint();
	}
	public void setopponentShips(ArrayList<Integer>  list1) {


		this.oppent_locations = list1;

		this.repaint();
	}
	public void allHit() {
		boolean result = true;

		for (int i =0; i<oppent_locations.size();i++) {

			if(!this.btt_hits.contains(oppent_locations.get(i))) {
				result= false;
			}
		}
		if(result) {		
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this, "End Game" +"\n Do you want to play again?", "GameOver", dialogButton);
			if(dialogResult == 0) {

				System.out.println("Yes option");
				CardLayout cardLayout = (CardLayout)container.getLayout();
				cardLayout.show(container, "4");
				this.repaint();
			}
			else {
				//this.dispose();
				System.out.println("No Option");
				CardLayout cardLayout = (CardLayout)container.getLayout();
				cardLayout.show(container, "4");
				this.repaint();
			}
		}

	}
	public void enableOpponentBoard() {

		for(int i = 0; i<btt_opponent.size() ; i++) {
			btt_opponent.get(i).setEnabled(true);
		}
		for (int i =0; i<disable.size();i++) {

			disable.get(i).setEnabled(false);
		}
		this.repaint();
	}


	public void disableOpponentBoard() {
		for(int i = 0; i<btt_opponent.size() ; i++) {
			btt_opponent.get(i).setEnabled(false);
		}
		this.repaint();
	}
	public void disableABox(int a) {
		buttons.get(a).setEnabled(false);
		if (pick_locations.contains(a)) ;
		buttons.get(a).setBackground(Color.BLACK);
		buttons.get(a).setOpaque(true);
		buttons.get(a).setBorderPainted(false);
		this.repaint();
	}

}

