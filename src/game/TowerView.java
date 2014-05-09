package game;

import java.awt.Color;
import java.awt.Graphics;

public class TowerView implements View {
	
	private Tower tower;
	
	public TowerView(Tower t) {
		tower = t;
	}
	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval(tower.getX(), tower.getY(), Block.blockSize-1, Block.blockSize-1);
	}
	
	public void drawShooting(Graphics g){
		
	}

}
