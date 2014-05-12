package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TowerView extends BlockView {
	
	private Tower tower;		//T�roljuk a tornyot, amit ki kell rajzolnunk
	
	public TowerView(Tower t) {
		tower = t;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(Map.FOG){
			g.setColor(Color.WHITE);		//Amennyiben a toronyra k�d ereszkedik, feh�r lesz; k�r alak�
		}else{
			g.setColor(Color.GRAY);			//Egy�bk�nt sz�rke; k�r alak�
		}
		
		int x = tower.getX();
		int y = tower.getY();
		
		//A m�gikus k�vek a torony belsej�be rajzol�dnak, mint egy kisebb centrikus k�r
		g.fillOval(x, y, Block.blockSize-1, Block.blockSize-1);
		
		Type gemType = tower.getGemType();
		
		if (gemType == Type.DAMAGE_INCREASER) {		//A sebz�s n�vel� k� piros sz�n�
			g.setColor(Color.RED);				
		} else if (gemType == Type.RANGE_EXPANDER) {		//A hat�t�v n�vel� k� narancs sz�n�
			g.setColor(Color.ORANGE);
		} else if (gemType == Type.SHOOTING_INCREASER) {		//A t�zel�st gyors�t� k� k�k sz�n�
			g.setColor(Color.BLUE);
		}
		
		g.fillOval(x + Block.blockSize/4, y + Block.blockSize/4, Block.blockSize/2, Block.blockSize/2);
	}
	
	public void drawShooting(Graphics g){
		if(Map.FOG) {
			g.setColor(Color.RED);		//A l�v�st jelz� felirat k�d - azaz feh�r torony - eset�n pirosan villan fel
		} else {
			g.setColor(Color.WHITE);	//Egy�bk�nt feh�ren - ez l�tszik jobban a sz�rke tornyon
		}
			
			//Onnan tudjuk, hogy a torony l�tt, hogy felvillan egy kis "AIM!" felirat
			g.setFont(new Font("default", Font.BOLD, 12));
			g.drawString("AIM!", tower.getX()+Block.blockSize/4, tower.getY()+Block.blockSize/4);
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
}
