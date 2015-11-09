/***********************************************************************
This is the part that actually does the computations and decides things
like who really won and what counts as a win.

@author Evan Stedman
@version Winter 2015
***********************************************************************/
package project2;

public class ConnectFourGame {
	
	/** a 2-dimensional array that represents the connect four board */
	private int[][] board; 
	
	/** the current game status such as in progress or cats game */
	private GameStatus status;
	
	/** the current player */
	private int player;
	
	/** the board size */
	private int brdSz;

	/*******************************************************************
	Constructor initiates the board to be a default 10X10 2-dimensional
	array and sets each element of the array to 0 to represent empty and
	sets the starting player to player 1
	*******************************************************************/
	public ConnectFourGame(){
		
		//start the game off as in progress
		status = GameStatus.InProgress;
		board = new int[10][10];
		setBrdSz(10);
		player = 1;
		reset();
	}

	/*******************************************************************
	Constructor initiates the board to be a given size 2-dimensional
	array and sets each element of the array to 0 to represent empty and
	sets the starting player to player 1
	@param the new size of the board x by x
	*******************************************************************/
	public ConnectFourGame(int boardSz){
		
		//start the game off as in progress
		status = GameStatus.InProgress;
		board = new int[boardSz][boardSz];
		setBrdSz(boardSz);
		player = 1;
		reset();
	}
	
	/*******************************************************************
	@return the player's denoted number is given
	*******************************************************************/
	public int getPlayer(){
		return player;
	}
	
	
	/*******************************************************************
	@param sets the current player to the given integer
	*******************************************************************/
	public void setPlayer(int player){
		this.player = player;
	}
	
	/*******************************************************************
	This gets called by the GUI to switch the user
	@param the player thats turn is ending
	*******************************************************************/
	public void switchPlayer(int playerToSwitch){
		if(player == 1)
			player = 2;
		else 
			player = 1;
	}
	
	/*******************************************************************
	Determines whether or not a column can have a "chip" added to it
	@param the column that is being attempted to put a "chip" in 
	@return determines if the column selected can actually be added on
	to and what row the "chip" ends up on. If the column is full then 
	returns negative 1
	*******************************************************************/
	public int selectCol(int col){
		
		//since connect four starts at the bottom row we have to do a 
		//for loop that counts down
		for(int row = getBrdSz(); row > -1; row = row-1){
			if(board[row][col] == 0){
				board[row][col] = player;
				System.out.println("1");
				return row;
			}
		}
		System.out.println("2");
		return -1;
		
	}
	
	/*******************************************************************
	resets the board to be represented by all 0's to denote empty cells
	*******************************************************************/
	public void reset(){
		for (int row = 0; row < getBrdSz(); row++) {
			System.out.println("");
			for (int col = 0; col < getBrdSz(); col++){
				board[row][col] = 0;
				System.out.println(""+ board[row][col]);
			}
		}
	}
	
	/*******************************************************************
	Checks for any kind of winning patter such as a match of four 
	horizontal, diagonal, or vertical
	@param only checks for the player that just had a turn
	@return either the number representing the player that won, or a 0 
	if neither player won
	*******************************************************************/
	public int checkWinner(int player){
		
		//Checks for a horizontal win with wrap around
		for (int row = 0; row < getBrdSz(); row++){
			if(board[row][0] == player && board[row][getBrdSz()-3] == 
					player && board[row][getBrdSz()-2] == player && 
					board[row][getBrdSz()-1] == player)
				return player;
		}
		
		//Checks for a horizontal win with wrap around
		for (int row = 0; row < getBrdSz(); row++){
			if(board[row][1] == player && board[row][getBrdSz()-2] == 
					player && board[row][getBrdSz()-1] == player && 
					board[row][0] == player)
				return player;
		}
		
		//Checks for a horizontal win with wrap around
		for (int row = 0; row < getBrdSz(); row++){
			if(board[row][1] == player && board[row][2] == player && 
					board[row][getBrdSz()-1] == player && board[row][0] 
							== player)
				return player;
		}
		
		//Checks for a plain horizontal win without wrap around
		for (int row = 0; row < getBrdSz(); row++){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row][col] == player) && (board[row][col+1] == 
						player) && (board[row][col+2] == player) && 
						board[row][col+3] == player)
					return player;
		}
		
		//Checks for vertical win
		for (int row = 0; row < getBrdSz()-3; row++){
			for (int col = 0; col < getBrdSz(); col++) 
				if ((board[row][col] == player) && (board[row+1][col] == 
						player) && (board[row+2][col] == player) && 
						board[row+3][col] == player)
					return player;
		}
		
		//Checks for non wrapping diagonal down right
		for (int row = 0; row < getBrdSz()-3; row++){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row][col] == player) && (board[row+1][col+1] 
						== player) && (board[row+2][col+2] == player) && 
						board[row+3][col+3] == player)
					return player;
		}
		
		//Checks for non wrapping diagonal up right
		for (int row = getBrdSz()-1; row > 3; row--){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row][col] == player) && (board[row-1][col+1] 
						== player) && (board[row-2][col+2] == player) && 
						board[row-3][col+3] == player)
					return player;
		}
		
		//Checks for wrapping diagonal down right
		for (int row = 0; row < getBrdSz()-3; row++){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row+3][0] == player) && (board[row+2][9] 
						== player) && (board[row+2][8] == player) && 
						board[row][7] == player)
					return player;
		}
		
		//Checks for wrapping diagonal down right
		for (int row = 0; row < getBrdSz()-3; row++){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row+3][2] == player) && (board[row+2][1] 
						== player) && (board[row+2][0] == player) && 
						board[row][9] == player)
					return player;
		}
		
		//Checks for wrapping diagonal down right
		for (int row = 0; row < getBrdSz()-3; row++){
			for (int col = 0; col < getBrdSz()-3; col++) 
				if ((board[row+3][1] == player) && (board[row+2][0] 
						== player) && (board[row+2][9] == player) && 
						board[row][8] == player)
					return player;
		}
		
		//Returns 0 if there are no winners
		return 0;
		
		
	}
	
	/*******************************************************************
	@return if the board is filled up without a winner then returns 1
	and if there is even a possibility of a win returns a 0
	*******************************************************************/
	public int checkCats(){
		for (int row = 0; row < getBrdSz(); row++){ 
			for (int col = 0; col < getBrdSz(); col++) 
				if(board[row][col] == 0)
					return 0;
		}
		return 1;
	}
	
	/*******************************************************************
	@return the possibilities being 1 of the 4 states of GameStatus
	*******************************************************************/
	public GameStatus getGameStatus(){
		/*if(checkCats() == 1)
			status = GameStatus.Cats;
		if(checkWinner(player) == 1)
			status = GameStatus.player1Won;
		if(checkWinner(player) == 2)
			status = GameStatus.player2Won;
		if(checkWinner(player) == 0)
			status = GameStatus.InProgress;*/
		return status;
	}

	/*******************************************************************
	 @return the board size
	 ******************************************************************/
	public int getBrdSz() {
		return brdSz;
	}

	/*******************************************************************
	 @param the new size of the board as an integer
	 ******************************************************************/
	public void setBrdSz(int brdSz) {
		this.brdSz = brdSz;
	}
}
