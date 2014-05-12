package game;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;


	private static Font font = new Font("Matura MT Script Capitals", 0, 18);
	
	
	private Application app;
	
	private JButton newGameButton;		//Új játék indítása
	private JButton exitButton;			//Kilépés
	//private JButton resultsButton;		<--Eredménylista - még nincs megírva
	
	
	public MenuPanel(Application application) {
		app = application;
		
		newGameButton = new JButton("Új játék");
		newGameButton.addActionListener(this);
		exitButton = new JButton("Kilépés");
		exitButton.addActionListener(this);
		//resultsButton = new JButton("Ranglista");
		//resultsButton.addActionListener(this);
		
		this.add(newGameButton);
		this.add(exitButton);
		//this.add(resultsButton);
		
		
		for (Component c : this.getComponents()) {		//Gombok betûtípusa
		    c.setFont(font);
		}
		
	}
	
	
	//Háttérkép
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponents(g);
		g.drawImage(new ImageIcon("ring.jpg").getImage(), 0, 0, 1000, 600, null);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();
		
		if (event.equals("Új játék")) {
			
			app.changeToGame();		//Új játék gomb megnyomása esetén meghívódik a menüt a pályára lecserélõ függvény
			
		}else if(event.equals("Kilépés")){
			System.exit(0);
		}else if(event.equals("Ranglista")){
			
		}

	}
	
	
}
