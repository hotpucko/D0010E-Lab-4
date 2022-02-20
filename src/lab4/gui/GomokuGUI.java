package lab4.gui;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	private Label messageLabel;
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		//initialize variables
		connectButton = new JButton("connect");
		newGameButton = new JButton("new game");
		disconnectButton = new JButton("disconnect");
		messageLabel = new Label("text");
		
		
		
		//initialize panel
		GamePanel panel = new GamePanel(g.getGameGrid());
		panel.setVisible(true);
		//add listener
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int[] clickedPosition = panel.getGridPosition(e.getX(), e.getY());
				g.move(clickedPosition[0], clickedPosition[1]);
				System.out.println(String.format("clicked x: %s, y: %s, gridPos: x: %d, y: %d", e.getX(), e.getY(), clickedPosition[0], clickedPosition[1]));
			}
		};
		panel.addMouseListener(mouseAdapter);
		
		//initialize frame
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		//handle UI structure
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
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}