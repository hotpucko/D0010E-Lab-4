package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GomokuClient client = new GomokuClient(4001);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
		
		/*
		GomokuClient client2 = new GomokuClient(4001);
		GomokuGameState gameState2 = new GomokuGameState(client);
		GomokuGUI gui2 = new GomokuGUI(gameState, client);
		*/
	}

}
