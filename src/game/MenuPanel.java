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
	
	private JButton newGameButton;		//�j j�t�k ind�t�sa
	private JButton exitButton;			//Kil�p�s
	//private JButton resultsButton;		<--Eredm�nylista - m�g nincs meg�rva
	
	
	public MenuPanel(Application application) {
		app = application;
		
		newGameButton = new JButton("�j j�t�k");
		newGameButton.addActionListener(this);
		exitButton = new JButton("Kil�p�s");
		exitButton.addActionListener(this);
		//resultsButton = new JButton("Ranglista");
		//resultsButton.addActionListener(this);
		
		this.add(newGameButton);
		this.add(exitButton);
		//this.add(resultsButton);
		
		
		for (Component c : this.getComponents()) {		//Gombok bet�t�pusa
		    c.setFont(font);
		}
		
	}
	
	
	//H�tt�rk�p
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponents(g);
		g.drawImage(new ImageIcon("ring.jpg").getImage(), 0, 0, 1000, 600, null);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String event = e.getActionCommand();
		
		if (event.equals("�j j�t�k")) {
			
			app.changeToGame();		//�j j�t�k gomb megnyom�sa eset�n megh�v�dik a men�t a p�ly�ra lecser�l� f�ggv�ny
			
		}else if(event.equals("Kil�p�s")){
			System.exit(0);
		}else if(event.equals("Ranglista")){
			
		}

	}
	
	
}
