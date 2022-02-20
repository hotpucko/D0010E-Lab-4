package lab4.gui;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
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
		
		connectButton = new JButton("connect");
		newGameButton = new JButton("new game");
		disconnectButton = new JButton("disconnect");
		messageLabel = new Label("text");
		
		GamePanel panel = new GamePanel(new GameGrid(5));
		panel.setVisible(true);
		
		JFrame frame = new JFrame();
		
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		
		
		
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