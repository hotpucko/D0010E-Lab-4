package lab4.data;



import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Observable;

/*
 * 
 * 
 * @author Arvid From
 */
public class GameGrid extends Observable {
	
/* the space on the board is empty */
	public static final int EMPTY = 0;
	
/*the space on the board is occupied by the opponent*/
	public static final int OTHER = 1;
	
/*the space on the board is occupied by the player*/
	public static final int ME = 2;
	
	static final int INROW = 5;
	int[][] grid;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            The width/height of the game grid
	 */
	public GameGrid(int size) {

		grid = new int[size][size];
		for (int i = 0; i < (grid.length); i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = EMPTY;
			}
		}

	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
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
	public int getSize() {
		return grid.length;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		if (grid[x][y] == EMPTY) {
			if (player == GameGrid.ME) {
				grid[x][y] = ME;
			} else {
				grid[x][y] = OTHER;
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
	public void clearGrid() {
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = EMPTY;
			}

		}
		setChanged();
		notifyObservers();

	}

	/**
	 * Check if a player has the required amount of pieces in row to win
	 * along vertical, horizontal and diagonal axes.
	 * @param player
	 *            the player to check whether they have won
	 * @return true if player has correct amount of pieces in row, false otherwise
	 */
	public boolean isWinner(int player) {

		int[] winArray = new int[INROW];
		Arrays.fill(winArray, player);

		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				int[] drwin = new int[INROW];
				int[] dlwin = new int[INROW];

				int[] dwin = new int[INROW];
				int[] rwin = new int[INROW];

				for (int k = 0; k < INROW; k++) {
					// System.out.println(String.format("i: %s, j: %s, k: %s", i, j, k));
					if (i <= grid[0].length - INROW && j <= grid.length - INROW) {
						drwin[k] = grid[i + k][j + k];
					}
					if (i >= INROW && j <= grid.length - INROW) {
						dlwin[k] = grid[i - k][j + k];
					}

					if (i <= grid[0].length - INROW)
						rwin[k] = grid[i + k][j];
					if (j <= grid.length - INROW)
						dwin[k] = grid[i][j + k];
				}

				if (Arrays.equals(dlwin, winArray) || Arrays.equals(drwin, winArray) || Arrays.equals(dwin, winArray)
						|| Arrays.equals(rwin, winArray))
					return true;
			}
		}
		return false;
	}
}
