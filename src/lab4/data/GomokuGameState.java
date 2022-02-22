package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	// Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState = NOT_STARTED;
	private GomokuClient client;

	private String message = " Welcome to Gomoku";

	/**
	 * The constructor
	 * 
	 * @param gc
	 *            The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
		this.message = "unchanged gamestate";
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return this.message;
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */

	public void move(int x, int y) {

		int s = gameGrid.getLocation(x, y);

		switch (this.currentState) {

		case NOT_STARTED:
			return;
		case OTHER_TURN:
			message = "The other player is waiting to make their move";
			break;
		case FINISHED:
			message = "The game is over, go outside";
			break;
		case MY_TURN:
			if (s == GameGrid.EMPTY) {

				gameGrid.move(x, y, GameGrid.ME);
				currentState = OTHER_TURN;
				client.sendMoveMessage(x, y);
				this.message = "Opponents turn";

				if (gameGrid.isWinner(GameGrid.ME) == true) {

					this.currentState = FINISHED;
					message = "You won!";
				}

			}
			setChanged();
			notifyObservers();
		}

	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {

		if (this.currentState != NOT_STARTED) {
			gameGrid.clearGrid();
			currentState = OTHER_TURN;
			message = "New game started, waiting on opposing player to make their move";
			client.sendNewGameMessage();
			setChanged();
			notifyObservers();
		}

	}

	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame() {

		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "New game started, please make an opening play";
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		currentState = FINISHED;
		message = "Opponent disconnected";
		setChanged();
		notifyObservers();

	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {

		gameGrid.clearGrid();
		currentState = FINISHED;
		message = "disconnected successfully";
		client.disconnect();
		setChanged();
		notifyObservers();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x
	 *            The x coordinate of the move
	 * @param y
	 *            The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {

		gameGrid.move(x, y, GameGrid.OTHER);
		this.currentState = MY_TURN;
		this.message = "your turn";
		if (gameGrid.isWinner(GameGrid.OTHER) == true) {
			message = "You lost >:(";
		}
		setChanged();
		notifyObservers();

	}

	public void update(Observable o, Object arg) {

		switch (client.getConnectionStatus()) {
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();

	}
}