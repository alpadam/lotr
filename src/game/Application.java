package game;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame implements Runnable  {
	
	private static final long serialVersionUID = 1L;

	//J�t�k
	private Controller controller;
	
	// Men�
	private JPanel menuPanel;
	
	//El�sz�r a men� t�lt�dik be
	public Application() {
		menuPanel = new MenuPanel(this);
		this.add(menuPanel);
	}
	
	@Override
	public void run() {
		this.setTitle("LordOfTheRings");
		this.setSize(1000, 575);
		this.setLocation(230, 40);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	//Ez a f�ggv�ny t�lti be a Controllert a Men� helyett
	//A Controller egy �j sz�lon indul
	public void changeToGame() {
		Container container = this.getContentPane();
		container.removeAll();
		
		controller = new Controller(this);
		container.add(controller);
		container.validate();
		
		new Thread(controller).start();
	}
	
	//Ez a f�ggv�ny t�lti be a Men�t a Controller helyett
	public void changeToMenu() {
		Container container = this.getContentPane();
		container.removeAll();

		container.add(menuPanel);
		container.validate();
		menuPanel.repaint();
	}
	
	
	//Elind�tjuk az alkalmaz�st
	public static void main(String[] args)  {
		new Thread (new Application()).start();
	}

}
