package game;


import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Application {
	
	//ha true, akkor menüben vagyunk
	private boolean menu;
	
	//Játék
	private Controller controller;
	
	// Menü
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
