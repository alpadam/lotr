package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class RoadView implements View {
	
	private Road road;
	
	public RoadView(Road road) {
		this.road = road;
	}

	@Override
	public void draw(Graphics g) {
		
		if (road.isFinal()) {
			g.setColor(Color.CYAN);
		} else {
			g.setColor(Color.YELLOW);
		}
		
		g.fillRect(road.getX(), road.getY(), Block.blockSize, Block.blockSize);
		
		if (road.isTrap()) {
			g.setColor(Color.RED);
			g.drawLine(road.getX(), road.getY(), road.getX()+Block.blockSize-1, road.getY()+Block.blockSize-1);
			g.drawLine(road.getX()+Block.blockSize-1, road.getY(), road.getX(), road.getY()+Block.blockSize-1);
		} 
		
		List<Enemy> enemies = road.getEnemies();
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).enemyView.draw(g);;
		}
	}

}
