package game;


import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame implements Runnable, ActionListener  {
	
	//ha true, akkor menüben vagyunk
	private boolean menu;
	
	//Játék
	private Controller controller;
	
	// Menü
	private JPanel menuPanel;
	
	public Application() {
		
		menu = true;
		
		menuPanel = new MenuPanel(this);
		//controller = new Controller();
		
		this.setContentPane(menuPanel);
		
	}
	
	@Override
	public void run() {
		this.setTitle("LordOfTheRings");
		this.setSize(900, 600);
		setLocation(230, 40);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setMenu(){
		
		menu = true;
		
		Container container = this.getContentPane();
		container.removeAll();
		container.add(menuPanel);
		container.validate();
		container.repaint();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*String event = e.getActionCommand();
		
		if (event.equals("Új játék")) {
			
			new Thread(controller).start();
			menu = false;
			
			Container container = this.getContentPane();
			container.removeAll();
			container.add(controller);
			container.validate();
			container.repaint();
		}else if(event.equals("Kilépés")){
			System.exit(0);
		}else if(event.equals("Ranglista")){
			
		}*/
		
	}
	
	
	public static void main(String[] args)  {
		
		new Thread (new Application()).start();
		
	}

}
