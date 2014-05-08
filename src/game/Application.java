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
	
	//ha true, akkor men�ben vagyunk
	private boolean menu;
	
	//J�t�k
	private Controller controller;
	
	// Men�
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
		
		if (event.equals("�j j�t�k")) {
			
			new Thread(controller).start();
			menu = false;
			
			Container container = this.getContentPane();
			container.removeAll();
			container.add(controller);
			container.validate();
			container.repaint();
		}else if(event.equals("Kil�p�s")){
			System.exit(0);
		}else if(event.equals("Ranglista")){
			
		}*/
		
	}
	
	
	public static void main(String[] args)  {
		
		new Thread (new Application()).start();
		
	}

}
