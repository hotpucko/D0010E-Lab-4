	package lab4.data;

import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	
	public static final int EMPTY= 0;
	public static final int OTHER= 1;
	public static final int ME= 2;
	static final int INROW =5;
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
		return grid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return grid.length;
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
			if (player == GameGrid.ME) {
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
	 * Check if a player has the required amount of pieces in row to win
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		int[] winarray = new int[] {player,player,player,player,player};
		

        for (int i = 0; i < grid[0].length; i++)
        {
            for (int j = 0; j < grid.length - INROW; j++)
            {
                int[] drwin = new int[INROW];
                int[] dlwin = new int[INROW];

                for (int k = 0; k < INROW; k++)
                {
                	//System.out.println(String.format("i: %s, j: %s, k: %s", i, j, k));
                	if(i < grid[0].length - INROW)
                		drwin[k] = grid[i+k][j+k];
                	if(i >= INROW)
                		dlwin[k] = grid[i-k][j+k];
                }
                if(drwin[0] != 0)
                	System.out.println(String.format("%s, %s, %s, %s, %s", drwin[0], drwin[1], drwin[2], drwin[3], drwin[4]));
                if (Arrays.equals(dlwin, winarray) || Arrays.equals(drwin, winarray))
                    return true;
                

            }
        }
    	for (int i =0; i< grid[0].length; i++) {
    		int verticalwin=0;
    	for (int j = 0; j < grid.length-INROW; j++) {
    		if(grid[i][j] == player) {
    		if (grid[i][j] == grid[i][j+1]) {
    			verticalwin++;
    			if (verticalwin == INROW-1) {
    			}
    			return true;
    			}
    		}else { verticalwin = 0;
    			
    		}
    		}
    	}
    	for (int j = 0; j < grid.length; j++) {
    		int horizontalwin=0;
        		for (int i =0; i< grid[0].length-INROW; i++) {
        			if(grid[i][j] == player) {
        		if (grid[i][j] == grid[i+1][j]) {
        			horizontalwin++;
        			if (horizontalwin == INROW-1) {
        				return true;
        			}
        			}else { horizontalwin = 0;
        	
        		}
        	}
        }
	} 
    	return false;
}
}
