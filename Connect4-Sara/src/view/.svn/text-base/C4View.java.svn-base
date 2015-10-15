/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import players.GuiPlayer;

import controller.Controller;
import controller.GameMouseListener;

import model.ConnectFour;
import model.Game;
import java.awt.Font;
/**
 * This is the main view which displays the game, menus
 * and status bar.
 *
 */
public class C4View extends JPanel implements View {

	private static final long serialVersionUID = 4960946335587142550L;
	private final Cell[][] grid;
	private final Image image;
	private final Random rng;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu menu_1;
	private JMenu menu_2;
	private JMenuItem menuItem;
	private JMenuItem menuItem_15;
	private JMenuItem menuItem_14;
	private JMenuItem menuItem_13;
	private JMenuItem menuItem_12;
	private JMenuItem menuItem_11;
	private JMenuItem menuItem_10;
	private JMenuItem menuItem_9;
	private JMenuItem menuItem_8;
	private JMenuItem menuItem_7;
	private JMenuItem menuItem_6;
	private JMenuItem menuItem_5;
	private JMenuItem menuItem_4;
	private JMenuItem menuItem_3;
	private JMenuItem menuItem_2;
	private JMenuItem menuItem_1;
	private JPanel panel;
	private JLabel lbl;
//	private int[][] p1, p2; 	// just for debugging to be removed
	/**
	 * Constructor in which the menu bar and its options
	 * are created and fields initialised
	 */
	public C4View() {
//		p1= new int[7][6]; // just for debugging to be removed
//		p2= new int[7][6];// just for debugging to be removed
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		menuItem = new JMenuItem("New Game",KeyEvent.VK_N);	
		menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.menuNewGame();
			}
		});
		menu.add(menuItem);	
		
		menuItem_1 = new JMenuItem("Restart",KeyEvent.VK_R);
		menuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.menuRestart();
				
			}
		});
		menu.add(menuItem_1);
		
		menuItem_2 = new JMenuItem("Pause",KeyEvent.VK_P);
		menuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.pause();
				
			}
		});
		menu.add(menuItem_2);
		
		menuItem_3 = new JMenuItem("Continue",KeyEvent.VK_C);
		menuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.continueGame();
				
			}
		});
		menu.add(menuItem_3);
		
		menu.addSeparator();
		menuItem_4 = new JMenuItem("Exit",KeyEvent.VK_E);		
		menuItem_4.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Game Ends!", "Exit",  0);
//				System.exit(0);
				
				Controller c = Controller.getInstance();
				c.menuExit();
				
			}
			
		});
		menu.add(menuItem_4);		
		menu_1 = new JMenu("Help");
		menu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menu_1.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu_1);
		
		
		menuItem_5 = new JMenuItem("Hint", KeyEvent.VK_I);
		menuItem_5.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Controller c = Controller.getInstance();
				if ( c.getPlayer1()  instanceof GuiPlayer || c.getPlayer2()  instanceof GuiPlayer ){
					int hintColumn = c.getHint() + 1;
					if (hintColumn == 0) {
						JOptionPane.showMessageDialog(null,
								"You have used all your hints!", "Hint", JOptionPane.INFORMATION_MESSAGE);

					} else if(hintColumn == -1){
						JOptionPane.showMessageDialog(null,
								"The game has ended!", "Hint", JOptionPane.INFORMATION_MESSAGE);
						
					}else {
						JOptionPane.showMessageDialog(null, " Play in Column: " + hintColumn, "Hint",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}

		});
		menu_1.add(menuItem_5);
		menuItem_6 = new JMenuItem("Rules of Connect-Four",KeyEvent.VK_U);
		menuItem_6.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				HelpGameRulesView gameRules = new HelpGameRulesView();
				JFrame frame = new JFrame("Game Rules");
				frame.getContentPane().add(gameRules);
				frame.setPreferredSize(new Dimension(800,610));
				frame.setVisible(true);
				frame.setLocation(550, 350);
				frame.pack();
			
			}
			
		});
		menu_1.add(menuItem_6);
			
		menuItem_7 = new JMenuItem("About",KeyEvent.VK_A);
		menuItem_7.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuItem_7.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpAboutView about = new HelpAboutView();
				JFrame frame = new JFrame("About");
				frame.getContentPane().add(about);
				frame.setPreferredSize(new Dimension(680,670));
				frame.setVisible(true);
				frame.setLocation(650, 350);
				frame.pack();
			}
			
		});
		menu_1.add(menuItem_7);
		
		menu_1 = new JMenu("Colour");
		menu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menu_1.setMnemonic(KeyEvent.VK_O);
		menuBar.add(menu_1);
		
		menu_2 = new JMenu("Player 1");
		menu_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menu_1.add(menu_2);
		menuItem_8 = new JMenuItem ("Red");
		menuItem_8.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer1Colour(Color.RED);
				repaint();
			}
		});
		menu_2.add(menuItem_8);
		
		menuItem_9 = new JMenuItem ("Yellow");
		menuItem_9.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_9.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer1Colour(Color.YELLOW);
				repaint();
			}
		});
		menu_2.add(menuItem_9);
		
		menuItem_10 = new JMenuItem ("Blue");
		menuItem_10.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_10.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer1Colour(Color.CYAN);
				repaint();
			}
		});
		menu_2.add(menuItem_10);
		
		menuItem_11 = new JMenuItem ("Green");
		menuItem_11.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_11.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer1Colour(Color.GREEN);
				repaint();
			}
		});
		menu_2.add(menuItem_11);
		
		menu_2 = new JMenu("Player 2");
		menu_2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menu_1.add(menu_2);
		menuItem_12 = new JMenuItem ("Red");
		menuItem_12.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_12.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer2Colour(Color.RED);
				repaint();
			}
		});
		menu_2.add(menuItem_12);
		
		menuItem_13 = new JMenuItem ("Yellow");
		menuItem_13.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_13.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer2Colour(Color.YELLOW);
				repaint();
			}
		});
		menu_2.add(menuItem_13);
		
		menuItem_14 = new JMenuItem ("Blue");
		menuItem_14.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_14.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer2Colour(Color.cyan);
				repaint();
			}
		});
		menu_2.add(menuItem_14);
		
		menuItem_15 = new JMenuItem ("Green");
		menuItem_15.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuItem_15.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Controller c = Controller.getInstance();
				c.setPlayer2Colour(Color.GREEN);
				repaint();
			}
		});
		menu_2.add(menuItem_15);
		
		rng = new Random();
		panel = new JPanel();
		GridLayout gl = new GridLayout(6, 7);
		panel.setLayout(gl);
//		this.add(menuBar, 0);
		grid = new Cell[7][6];
		int i = 0;
		for(int y = 5 ; y >= 0 ; y--) {
			for(int x = 0 ; x < 7 ; x++) {
				grid[x][y] = new Cell(x, y);
				panel.add(grid[x][y], i++);
			}
		}
		panel.setBackground(Color.GRAY);
		image = loadImage("grid.png");
		panel.setPreferredSize(new Dimension(600,520));
		
		setLayout(new BorderLayout());
		add(menuBar, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		 lbl = new JLabel();
		 lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lbl, BorderLayout.SOUTH);

		
	}
	
	public void setStatus(String s){
		lbl.setText(s);
	}
	
//	public void update_values(Game game) 	// just for debugging to be removed
//	{
//		p1 = Toolkit.getPlayerValues(game, 1);
//		p2 = Toolkit.getPlayerValues(game, 2);
//	}
	
	public void paint (Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, menuBar.getHeight(), panel.getWidth(), panel.getHeight(), null);
	}

	@Override
	public void update(Game game) {
		if (game instanceof ConnectFour) {
//			System.out.println(game);
//			p1 = Toolkit.getPlayerValues(game, 1);	// just for debugging to be removed
//			p2 = Toolkit.getPlayerValues(game, 2);	// just for debugging to be removed
			int[][] viewGrid = ((ConnectFour) game).getViewGrid();
			for(int y = 0 ; y < 6 ; y++) {
				for(int x = 0 ; x < 7 ; x++) {
					grid[x][y].setOwner(viewGrid[x][y]);
				}
			}
			repaint();
		}
	}
/**
 * This method is used to load the image.
 * @param filename Location the URL for the image
 * @return the image
 */
	private Image loadImage(String filename) {
		URL url = this.getClass().getResource(filename);
		if(url == null) { System.out.println(filename);}
			return new ImageIcon(url).getImage();
	}
	
	private Image getRotatedImage (Image image, int degrees) {
		BufferedImage rotatedImage = null;
		
		if(degrees == 0) {
			return image;
		} else {
			rotatedImage = new BufferedImage(image.getHeight(null), image.getWidth(null), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) rotatedImage.getGraphics();
			g2d.rotate(Math.toRadians(degrees), image.getWidth(null)/2, image.getHeight(null)/2);
			g2d.drawImage(image, 0, 0, null);
		}
	    
	    return rotatedImage;
	}
	/**
	 * This is to notify the player of who won the game.
	 * @param winner the winner of the game, 1 for player 1 and 2 for player 2 and zero for draw.
	 */
	public void notifyOfWin( int winner){
		if (winner == 0){
			JOptionPane.showMessageDialog(this, "Draw!");
		}else{
			JOptionPane.showMessageDialog(this, "Player "+ winner + " won the game!");
		}
	}
	private class Cell extends JPanel {

		private static final long serialVersionUID = 2877994673457913156L;
		private int owner;
		private Image overlay;
//		int x, y;	// just for debugging to be removed
		
		private Cell (int x, int y) {
//			this.x = x;	// just for debugging to be removed
//			this.y = y;	// just for debugging to be removed
			overlay = getRotatedImage(loadImage("chip.png"), rng.nextInt(360));
//			overlay = loadImage("chip.png");			
		}
		
		private void setOwner(int owner) {
			this.owner = owner;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);		
			switch(owner){
			case 1: 
				g.setColor(Controller.getInstance().getPlayer1Colour());		
				g.fillOval(0, 0, getWidth(), getHeight());
				g.drawImage(overlay, 0, 0, getWidth(), getHeight(), null);
				break;
			case 2:
				g.setColor(Controller.getInstance().getPlayer2Colour());
				g.fillOval(0, 0, getWidth(), getHeight());
				g.drawImage(overlay, 0, 0, getWidth(), getHeight(), null);
				break;
			case 3:
				Color p1WinColour = Controller.getInstance().getPlayer1Colour();
				if (p1WinColour == Color.RED){
					g.setColor(new Color(153,0,0));
				}else if(p1WinColour == Color.YELLOW){
					g.setColor(new Color(153,153,0));
				} else if(p1WinColour == Color.CYAN){
					g.setColor(new Color(0,102,102));
				}else{
					g.setColor(new Color(0,153,0));

				}
				g.fillOval(0, 0, getWidth(), getHeight());
				g.drawImage(overlay, 0, 0, getWidth(), getHeight(), null);
				break;
			case 4:
				Color p2WinColour = Controller.getInstance().getPlayer2Colour();
				if (p2WinColour == Color.RED){
					g.setColor(new Color(153,0,0));
				}else if(p2WinColour == Color.YELLOW){
					g.setColor(new Color(153,153,0));
				} else if(p2WinColour == Color.CYAN){
					g.setColor(new Color(0,102,102));
				}else{
					g.setColor(new Color(0,153,0));
				}
				g.fillOval(0, 0, getWidth(), getHeight());
				g.drawImage(overlay, 0, 0, getWidth(), getHeight(), null);
				break;
			}
		
			
//			g.setColor(Color.BLACK); 	// just for debugging to be removed
//			g.drawString(p1[x][y] + "", getWidth()/2, getHeight()/2 - 5);	// just for debugging to be removed
//			g.drawString(p2[x][y] + "", getWidth()/2, getHeight()/2 + 10);	// just for debugging to be removed
		}
	}

	@Override
	public void addGameMouseListener(GameMouseListener ml) {
		addMouseListener(ml);
	}
	
	
	@Override
	public void removeGameMouseListener(GameMouseListener ml) {
		removeMouseListener(ml);
	}

}
