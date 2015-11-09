/***********************************************************************
This is the graphical representation of the game that uses the game 
class to determine valid information about who won and where to put the 
denoted players pieces

@author Evan Stedman
@version Winter 2015
***********************************************************************/
package project2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConnectFourPanel extends JPanel{

	/** the visual of the game board */
	private JLabel[][] board;
	
	/** an array of buttons/columns to drop the "chip" */
	private JButton[] selection;   
	
	/** the working/computational aspect */
	private ConnectFourGame game;
	
	/** holds part of the score keeper for player 1 */
	private JLabel player1Won;
	
	/** holds part of the score keeper for player 2 */
	private JLabel player2Won;
	
	/** holds the score for player 1 */
	private int player1Count;
	
	/** holds the score for player 2 */
	private int player2Count;
	
	ButtonListener listener = new ButtonListener();
	
	public ConnectFourPanel(){
		
		//startPlayer is default 1
		int startPlayer = 1;
		
		//board size starts off at 10 by 10
		int boardSz = 10;
		
		game = new ConnectFourGame();
		
		//Sets the players win's to 0
		setPlayer1Count(0);
		setPlayer2Count(0);
		
		//Separates certain elements as panel to help look nicer
		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel();
		
		//creates a board of user chosen size
		try{
			//Determines who the starting player is
			String strBoard = JOptionPane.showInputDialog (null, 
					"Enter The Board Size: 4-19"); 
			boardSz = Integer.parseInt(strBoard);
			if(boardSz >= 4 && boardSz <= 20)
				game = new ConnectFourGame(boardSz);
			
			//throws a message if the user enters an invalid name
			else
				throw new NumberFormatException();
				
		}
		catch(NumberFormatException n){
			game = new ConnectFourGame();
			JOptionPane.showMessageDialog(null, "You Entered An "
					+"Invalid Board Size So It Was Set To 10x10.");
		}
		
		//puts the array of columns at the top
		top.setLayout(new GridLayout(1,boardSz,2,2));
		selection = new JButton[boardSz];
		
		//Adds all the necessary listeners
		for (int col = 0; col < boardSz; col++) {
			selection[col] = new JButton ("Select " + (col+1));
			selection[col].addActionListener(listener);
			top.add(selection[col]);
		}
		
		//sets up the visual board as a 2-d array of labels
		center.setLayout(new GridLayout(boardSz,boardSz,8,1));
		board = new JLabel[boardSz][boardSz];
		
		for (int row = 0; row < boardSz; row++) 
			for (int col = 0; col < boardSz; col++) {
				board[row][col] = new JLabel ("|____________|");
				center.add(board[row][col]);
				}

		//sets up the bottom of the frame for score keeping
		bottom.setLayout(new GridLayout(2,2));
		JLabel play1Wins = new JLabel ("Player 1 Wins: ");
		JLabel play2Wins = new JLabel ("Player 2 Wins: ");
		player1Won = new JLabel(""+getPlayer1Count());
		player2Won = new JLabel(""+getPlayer2Count());
		bottom.add(play1Wins);
		bottom.add(player1Won);
		bottom.add(play2Wins);
		bottom.add(player2Won);
		
		//Separates the different panels
		add (top, BorderLayout.NORTH);
		add (center, BorderLayout.CENTER);
		add (bottom, BorderLayout.SOUTH);
		
		try{
			//Determines who the starting player is
			String strdPlayer = JOptionPane.showInputDialog (null, 
					"Enter The Starting Player: 1 or 2"); 
			startPlayer = Integer.parseInt(strdPlayer);
			if(startPlayer == 1)
				game.setPlayer(1);
			else if(startPlayer == 2)
				game.setPlayer(2);
			
			//throws a message if the user enters an invalid name
			else
				throw new NumberFormatException();
		}
		catch(NumberFormatException n){
			game.setPlayer(1);
			JOptionPane.showMessageDialog(null, "You Entered An "
					+"Invalid Player So The Default Of 1 Was Set.");
		}
		
	}
	
	/*******************************************************************
	Clears the board so a reset appears on the GUI
	*******************************************************************/
	public void wipeGame(){
		for (int row = 0; row < game.getBrdSz(); row++) 
			for (int col = 0; col < game.getBrdSz(); col++) {
				board[row][col].setText("|____________|");
				}
	}
	
	/*******************************************************************
	@return the score of player 2
	*******************************************************************/
	public int getPlayer2Count() {
		return player2Count;
	}
	
	/*******************************************************************
	@param using an integer, sets the score for player 2
	*******************************************************************/
	public void setPlayer2Count(int player2Count) {
		this.player2Count = player2Count;
	}
	
	/*******************************************************************
	@return the score of player 1
	*******************************************************************/
	public int getPlayer1Count() {
		return player1Count;
	}

	/*******************************************************************
	@param using an integer, sets the score for player 1
	*******************************************************************/
	public void setPlayer1Count(int player1Count) {
		this.player1Count = player1Count;
	}
	
	/*******************************************************************
	used to perform an action when a button is pressed
	*******************************************************************/
	private class ButtonListener implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			for (int col = 0; col < game.getBrdSz(); col++) 
				if (selection[col] == e.getSource()){
                	int row = game.selectCol(col);
                	if (row == -1) {
        				JOptionPane.showMessageDialog(null,
        						"This Column is full");
                	}
                	board[row][col].setText("|_____" + game.getPlayer() 
                							+ "______|");
                	if (game.getGameStatus() == GameStatus.player1Won) {
        				JOptionPane.showMessageDialog(null, "Player 1 "
        						+ "won \nThe game will reset");
        				game.reset();
        				wipeGame();
        				setPlayer1Count(getPlayer1Count()+1);
        				player1Won .setText(""+getPlayer1Count());
        			}
                	if(game.getGameStatus() == GameStatus.player2Won) {
        				JOptionPane.showMessageDialog(null, "Player 2 "
        						+ "won \nThe game will reset");
        				game.reset();
        				wipeGame();
        				setPlayer2Count(getPlayer2Count()+1);
        				player2Won .setText(""+getPlayer2Count());
        				
        			}
                	if (game.getGameStatus() == GameStatus.Cats) {
        				JOptionPane.showMessageDialog(null, "No One Won"
        						+ "\nThe game will reset");
        				game.reset();
        				wipeGame();
        			}
				}
			//changes the players turn after a move
			game.switchPlayer(game.getPlayer());
		}
	}
}
