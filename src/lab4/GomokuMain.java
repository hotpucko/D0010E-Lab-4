package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		int port = -1;
		
		try {
			if(args.length > 0)
				port = Integer.parseInt(args[0]);
		}
		catch (Exception e)
		{
			System.out.println("Argument did not match the format: enter an integer");
		}
		
		
		
		GomokuClient client = new GomokuClient( port == - 1 ? 4000 : port);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);

		GomokuClient client2 = new GomokuClient(4001);
		GomokuGameState gameState2 = new GomokuGameState(client2);
		GomokuGUI gui2 = new GomokuGUI(gameState2, client2);

	}

}
