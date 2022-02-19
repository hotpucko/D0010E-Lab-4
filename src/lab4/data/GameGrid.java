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
	int[][] grid; 
	

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		
	grid = new int[size][size];
	for (int i=0; i< (grid.length); i++) {
		for (int j = 0; j < grid[0].length;j++) {
			grid[i][j] = EMPTY;
			}
		}
	
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
		if ( grid[x][y] == EMPTY){
			if (player == 1) {
				grid[x][y] = ME;
			}else {
				grid[x][y]= OTHER;
			}	
				setChanged();
				notifyObservers();
				return true;
			}
		
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for (int i=0; i< grid[0].length; i++) {
			for(int j=0; j <grid.length; j++) {
				grid[i][j] = EMPTY;
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
		
		int[] winarray = new int[] {1,1,1,1,1};

        for (int i = INROW; i < grid[0].length; i++)
        {
            for (int j = 0; j < grid.length - INROW; j++)
            {
                int[] drwin = new int[INROW];
                int[] dlwin = new int[INROW];

                for (int k = 0; k < INROW; k++)
                {
                    drwin[k] = grid[i-k][j+k];
                    dlwin[k] = grid[i+k][j+k];
                }

                if (dlwin == winarray || drwin == winarray)
                    return true;

            }
        }
return false;
	}
}