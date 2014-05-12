package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TowerView extends BlockView {
	
	private Tower tower;		//Tároljuk a tornyot, amit ki kell rajzolnunk
	
	public TowerView(Tower t) {
		tower = t;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(Map.FOG){
			g.setColor(Color.WHITE);		//Amennyiben a toronyra köd ereszkedik, fehér lesz; kör alakú
		}else{
			g.setColor(Color.GRAY);			//Egyébként szürke; kör alakú
		}
		
		int x = tower.getX();
		int y = tower.getY();
		
		//A mágikus kövek a torony belsejébe rajzolódnak, mint egy kisebb centrikus kör
		g.fillOval(x, y, Block.blockSize-1, Block.blockSize-1);
		
		Type gemType = tower.getGemType();
		
		if (gemType == Type.DAMAGE_INCREASER) {		//A sebzés növelõ kõ piros színû
			g.setColor(Color.RED);				
		} else if (gemType == Type.RANGE_EXPANDER) {		//A hatótáv növelõ kõ narancs színû
			g.setColor(Color.ORANGE);
		} else if (gemType == Type.SHOOTING_INCREASER) {		//A tüzelést gyorsító kõ kék színû
			g.setColor(Color.BLUE);
		}
		
		g.fillOval(x + Block.blockSize/4, y + Block.blockSize/4, Block.blockSize/2, Block.blockSize/2);
	}
	
	public void drawShooting(Graphics g){
		if(Map.FOG) {
			g.setColor(Color.RED);		//A lövést jelzõ felirat köd - azaz fehér torony - esetén pirosan villan fel
		} else {
			g.setColor(Color.WHITE);	//Egyébként fehéren - ez látszik jobban a szürke tornyon
		}
			
			//Onnan tudjuk, hogy a torony lõtt, hogy felvillan egy kis "AIM!" felirat
			g.setFont(new Font("default", Font.BOLD, 12));
			g.drawString("AIM!", tower.getX()+Block.blockSize/4, tower.getY()+Block.blockSize/4);
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
}
