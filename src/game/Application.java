package game;


import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Application {
	
	//ha true, akkor men�ben vagyunk
	private boolean menu;
	
	//J�t�k
	private Controller controller;
	
	// Men�
	private JPanel menuPanel;
	private JButton newGameButton;
	private JButton exitButton;
	private JButton resultsButton;
	
	
	public static void main(String[] args)  {
		
		Controller controller = new Controller();
		try {
			controller.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
