package game;

import java.awt.Color;
import java.awt.Graphics;

public class TowerView extends BlockView {
	
	private Tower tower;
	
	public TowerView(Tower t) {
		tower = t;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(Map.FOG){
			g.setColor(Color.WHITE);
		}else{
			g.setColor(Color.GRAY);
		}
		
		
		g.fillOval(tower.getX(), tower.getY(), Block.blockSize-1, Block.blockSize-1);
		
		Type gemType = tower.getGemType();
		
		if (gemType == Type.DAMAGE_INCREASER) {
			g.setColor(Color.RED);
		} else if (gemType == Type.RANGE_EXPANDER) {
			g.setColor(Color.ORANGE);
		} else if (gemType == Type.SHOOTING_INCREASER) {
			g.setColor(Color.BLUE);
		}
		
		g.fillOval(tower.getX()+Block.blockSize/4, tower.getY()+Block.blockSize/4, Block.blockSize/2, Block.blockSize/2);
	}
	
	public void drawShooting(Graphics g){
			g.setColor(Color.RED);
			g.drawString("AIM!", tower.getX()+Block.blockSize/4, tower.getY()+Block.blockSize/4);
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	/*public void fogOn(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(tower.getX(), tower.getY(), Block.blockSize-1, Block.blockSize-1);
		
		Type gemType = tower.getGemType();
		
		if (gemType == Type.DAMAGE_INCREASER) {
			g.setColor(Color.RED);
		} else if (gemType == Type.RANGE_EXPANDER) {
			g.setColor(Color.ORANGE);
		} else if (gemType == Type.SHOOTING_INCREASER) {
			g.setColor(Color.BLUE);
		}
		g.fillOval(tower.getX()+Block.blockSize/4, tower.getY()+Block.blockSize/4, Block.blockSize/2, Block.blockSize/2);
	}*/
	
}
