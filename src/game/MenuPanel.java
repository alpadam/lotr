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
	
	private static Font font = new Font("Matura MT Script Capitals", 0, 18);
	
	
	private Application app;
	
	private JButton newGameButton;
	private JButton exitButton;
	private JButton resultsButton;
	
	
	public MenuPanel(Application application) {
		app = application;
		
		newGameButton = new JButton("Új játék");
		newGameButton.addActionListener(this);
		exitButton = new JButton("Kilépés");
		exitButton.addActionListener(this);
		resultsButton = new JButton("Ranglista");
		resultsButton.addActionListener(this);
		
		this.add(newGameButton);
		this.add(exitButton);
		this.add(resultsButton);
		
		for (Component c : this.getComponents()) {
		    c.setFont(font);
		}
		
		
		
		
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponents(g);
		g.drawImage(new ImageIcon("ring.jpg").getImage(), 0, 0, 900, 600, null);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
