/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import players.Player;
import controller.Controller;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.awt.Color;
/**
 * This class is the set up panel where the user can select the desired players
 * and load files if they chose to play against of the Roulette players.
 */
public class SetUpView extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private Player selected = null;
	private  final Image image;
	private String path_1, path_2;

	/**
	 * Create the panel.
	 */
	public SetUpView(String[] names) {
		path_1 = null ; 
		path_2 = null;
		/*
		 * background image for the set up panel
		 */
		image = loadImage("c4.JPG");
		setBounds(new Rectangle(0, 0, 600, 520));
		setLayout(null);
	

		/*
		 * This label displays the path of the chosen file
		 * for player 1
		 */
		final JLabel lblPath_1 = new JLabel();
		lblPath_1.setBounds(12, 604, 212, 40);
		add(lblPath_1); 
		/*
		 * This label displays the path of the chosen file
		 * for player 2
		 */
		final JLabel lblPath_2 = new JLabel();
		lblPath_2.setBounds(445, 604, 212, 40);
		add(lblPath_2);
		/*
		 * This button is to create or load a file for player 1
		 */
		final JButton loadButton_1 = new JButton("Create/Load File");
		loadButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		loadButton_1.setEnabled(false);
		loadButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechoser = new JFileChooser();
				int ret = filechoser.showSaveDialog(SetUpView.this);
				if(ret == JFileChooser.APPROVE_OPTION){
					lblPath_1.setText(filechoser.getCurrentDirectory().toString()+ File.separator + filechoser.getSelectedFile().getName());
					path_1 = lblPath_1.getText();
				}
			}
		});
		loadButton_1.setBounds(108, 93, 167, 45);
		add(loadButton_1);
		/*
		 * This button is to create or load a file for player 2
		 */
		final JButton loadButton_2 = new JButton("Create/Load File");
		loadButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		loadButton_2.setEnabled(false);
		loadButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechoser = new JFileChooser();
				int ret = filechoser.showSaveDialog(SetUpView.this);
				if(ret == JFileChooser.APPROVE_OPTION){
					lblPath_2.setText(filechoser.getCurrentDirectory().toString()+ File.separator + filechoser.getSelectedFile().getName());
					path_2 = lblPath_2.getText();
				}
			}
		});
		loadButton_2.setBounds(445, 93, 172, 45);
		add(loadButton_2);
		/*
		 * This is where the user selects their desired player
		 * for player 1.
		 */
		final JComboBox comboPlayer1 = new JComboBox(names);	
		comboPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboPlayer1.setBounds(108, 13, 167, 40);
		comboPlayer1.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if(((String)comboPlayer1.getSelectedItem()).equals("RoulettePlayer_1")){
					loadButton_1.setEnabled(true); 
					
				} else if (((String)comboPlayer1.getSelectedItem()).equals("RoulettePlayer_2")){
					loadButton_1.setEnabled(true); 
					
				}  else if (((String)comboPlayer1.getSelectedItem()).equals("MCTS_Adjusting")){
					loadButton_1.setEnabled(true); 
				}
				else{
					loadButton_1.setEnabled(false);
				}
			}
		});
		add(comboPlayer1);
		/*
		 * This is where the user selects their desired player
		 * for player 2.
		 */
		final JComboBox comboPlayer2 = new JComboBox(names);	
		comboPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboPlayer2.setBounds(442, 13, 175, 40);
		comboPlayer2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if(((String)comboPlayer2.getSelectedItem()).equals("RoulettePlayer_1") ){
					loadButton_2.setEnabled(true); 
					
				}else if(((String)comboPlayer2.getSelectedItem()).equals("RoulettePlayer_2") ){
					loadButton_2.setEnabled(true); 
					
				}  else if (((String)comboPlayer2.getSelectedItem()).equals("MCTS_Adjusting")){
					loadButton_2.setEnabled(true); 
				}
				else{
					loadButton_2.setEnabled(false);
				}
			}
		});
		add(comboPlayer2);
		/*
		 * This is the start button to start the game
		 */
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
//				System.out.println("view path_1: "+path_1);
//				System.out.println("view path_2: "+path_2);

				c.userChosenOptions((String)comboPlayer1.getSelectedItem(), (String)comboPlayer2.getSelectedItem(), path_1, path_2);

			}
		});
		startButton.setBounds(272, 560, 136, 45);
		add(startButton);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPlayer.setBounds(12, 11, 86, 42);
		add(lblPlayer);

		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPlayer_1.setBounds(343, 12, 76, 41);
		add(lblPlayer_1);
		
		
		
		
	}

	public Player getSelectedPlayer() {
		return selected;
	}
	/**
	 * This is used to load the background image
	 * @param filename Location the URL for the image
	 * @return the image
	 */
	private Image loadImage(String filename) {
		URL url = this.getClass().getResource(filename);
		if(url == null) { System.out.println(filename);}
		return new ImageIcon(url).getImage();
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}
