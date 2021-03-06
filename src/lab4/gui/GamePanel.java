package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 * 
 * @author Stefan Jonsson, Arvid From
 *
 */
public class GamePanel extends JPanel implements Observer {

	// conversion rate from the typographical point to pixels
	private static final float PIXEL_TO_PTS_MULTIPLIER = 1.9f;
	private final int UNIT_SIZE = 15; /* The side size of the grid squares */
	private GameGrid grid;

	/**
	 * The constructor
	 * 
	 * @param grid
	 *            The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid) {
		this.grid = grid;
		this.grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1, grid.getSize() * UNIT_SIZE + 1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates of the panel
	 * 
	 * @param x
	 *            the x coordinates
	 * @param y
	 *            the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) {
		return new int[] { x / this.UNIT_SIZE, y / this.UNIT_SIZE }; // performs whole number division, i.e. 5/2=2
	}

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(g.getFont().deriveFont(UNIT_SIZE * PIXEL_TO_PTS_MULTIPLIER));

		g.setColor(Color.black);
		for (int i = 0; i < grid.getSize(); i++) {
			for (int j = 0; j < grid.getSize(); j++) {
				g.drawRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); // draw grid

				if (grid.getLocation(i, j) == GameGrid.ME) {
					g.drawString("x", i * UNIT_SIZE, (j + 1) * UNIT_SIZE);
				} else if (grid.getLocation(i, j) == GameGrid.OTHER) {
					g.drawString("o", i * UNIT_SIZE, (j + 1) * UNIT_SIZE);
				}
			}
		}
	}

}