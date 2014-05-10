package game;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame implements Runnable  {
	
	//ha true, akkor men�ben vagyunk
	private boolean menu;
	
	//J�t�k
	private Controller controller;
	
	// Men�
	private JPanel menuPanel;
	
	public Application() {
		
		menu = true;
		
		menuPanel = new MenuPanel(this);
		
		this.add(menuPanel);
	}
	
	@Override
	public void run() {
		this.setTitle("LordOfTheRings");
		this.setSize(900, 600);
		this.setLocation(230, 40);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void changeToGame(){
		
		Container container = this.getContentPane();
		container.removeAll();
		
		controller = new Controller(this);
		container.add(controller);
		container.validate();
		
		new Thread(controller).start();
	}
	
	public void changeToMenu(){
		
		Container container = this.getContentPane();
		container.removeAll();

		container.add(menuPanel);
		container.validate();
		menuPanel.repaint();
		
	}
	
	
	public static void main(String[] args)  {
		
		new Thread (new Application()).start();

	}

}
