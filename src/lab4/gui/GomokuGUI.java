package lab4.gui;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/**
 * GomokuGUI is the class for generating and handling the general Gomoku GUI
 * 
 * @author Stefan Jonsson4
 *
 */
public class GomokuGUI implements Observer {

	private GomokuClient client;
	private GomokuGameState gamestate;

	// swing & awt variables
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	private Label messageLabel;

	private ConnectionWindow connectionWindow;

	/**
	 * The constructor
	 * 
	 * @param g
	 *            The game state that the GUI will visualize
	 * @param c
	 *            The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c) {
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		// initialize variables
		connectButton = new JButton("connect");
		newGameButton = new JButton("new game");
		disconnectButton = new JButton("disconnect");

		// declare & initialize listeners
		ActionListener connectListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectionWindow = new ConnectionWindow(c);
			}
		};

		ActionListener newGameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.newGame();
			}
		};

		ActionListener disconnectListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.disconnect();
			}
		};

		connectButton.addActionListener(connectListener);
		newGameButton.addActionListener(newGameListener);
		disconnectButton.addActionListener(disconnectListener);

		messageLabel = new Label("Waiting for connection.");

		// initialize panel
		GamePanel panel = new GamePanel(g.getGameGrid());
		panel.setVisible(true);
		// add listener
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int[] clickedPosition = panel.getGridPosition(e.getX(), e.getY());
				g.move(clickedPosition[0], clickedPosition[1]);
			}
		};
		panel.addMouseListener(mouseAdapter);

		// initialize frame
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// handle UI structure
		Box verticalBox = Box.createVerticalBox();

		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(connectButton);
		buttonBox.add(newGameButton);
		buttonBox.add(disconnectButton);

		verticalBox.add(panel);
		verticalBox.add(buttonBox);
		verticalBox.add(messageLabel);

		frame.add(verticalBox);
		frame.pack();
		frame.setMinimumSize(frame.getSize());

		newGameButton.setEnabled(false);
		disconnectButton.setEnabled(false);

	}

	public void update(Observable arg0, Object arg1) {

		// Update the buttons if the connection status has changed
		if (arg0 == client) {
			if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			} else {
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}

		// Update the status text if the gamestate has changed
		if (arg0 == gamestate) {
			messageLabel.setText(gamestate.getMessageString());
		}

	}

}