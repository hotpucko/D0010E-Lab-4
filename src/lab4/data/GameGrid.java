	package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	
	static final int EMPTY= 0;
	static final int OTHER= 1;
	static final int ME= 2;
	static final int INROW = 5;
	int[][] boardSpaces; 
	

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		
	int[][] boardSpaces = new int[size][size];
	
	}
	
	
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		
		return 0;
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return 0;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if ( boardSpaces[x][y] == EMPTY){
			if (player == 1) {
				boardSpaces[x][y] = ME;
				setChanged();
				notifyObservers();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for (int i=0; i< boardSpaces[0].length; i++) {
			for(int j=0; j <boardSpaces.length; j++) {
				boardSpaces[i][j] = EMPTY;
			}
				
		}
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		int[] hMask =new int[] {1,1,1,1,1};
		int[] vMask = new int [] {1,1,1,1,1};
//		int[][]dDownMask = new int[5][5];
//		
//		for (int i =0; i  < dDownMask.length; i++) {
//			
//			dDownMask [i][i] =1;
//		}
		
		
		//checks horizontal wins
		for (int i=0; i< (boardSpaces[0].length - INROW); i++) { 
			
			for (int j = 0; j < boardSpaces.length;j++) {
				
			int[] Checkwin =new int []{boardSpaces[i][j], boardSpaces[i+1][j], boardSpaces[i+2][j], boardSpaces[i+3][j], boardSpaces[i+4][j]};
			if 	(Checkwin == hMask) {
				return true;
				}
			}
		}
		//Checks vertical wins
			for (int i=0; i< (boardSpaces.length - INROW); i++) {
				
				for (int j = 0; j < boardSpaces[0].length;j++) {
					
					int[] Checkwin =new int []{boardSpaces[i][j], boardSpaces[i+1][j], boardSpaces[i+2][j], boardSpaces[i+3][j], boardSpaces[i+4][j]};
					if 	(Checkwin == vMask) {
						return true;
					}
					
				}
				
			}
		// checks NW to SE wins
			
			int[][] subMatrix = new int[INROW][INROW];
			
			for (int i =0; i< boardSpaces.length - INROW; i++) {
				for (int j =0; j<boardSpaces[0].length -INROW; j++) {

					for (int k = 0; k < INROW; k++) {
						for (int l = 0; l < INROW; l++) {
							
							subMatrix[k][l] = boardSpaces[i][j];
							int diagonalCounter=0;
							
							for (int m =0; m  < INROW; m++) {
								
								if (subMatrix [m][m] ==1) {
									diagonalCounter++;
									if (diagonalCounter == INROW){
										return true;
									}	
								}
							}
						}
					}
				}
			}
			// for SW to NE wins
			int[][] subMatrix2 = new int[INROW][INROW];
			
			for (int i =0; i< boardSpaces.length - INROW; i++) {
				for (int j =0; j<boardSpaces[0].length -INROW; j++) {
					
					for (int k = 0; k < INROW; k++) {
						for (int l = 0; l < INROW; l++) {
							
							subMatrix2[k][l] = boardSpaces[i][j];
							int diagonalCounter2= 0;
						
							for (int m =0; m  < INROW; m++) {
								for (int n=INROW; n > -1; n--) {
									
									if (subMatrix [m][n] ==1)
										diagonalCounter2++;
									if (diagonalCounter2 == INROW){
										return true;
								}
							}
	}
						}
					}
				}
			}
return false;
	}
}