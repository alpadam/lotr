package game;

import java.awt.Color;
import java.awt.Graphics;

public class RoadView implements View {
	
	private Road road;
	
	public RoadView(Road road) {
		this.road = road;
	}

	@Override
	public void draw(Graphics g) {
		
		if(road.isFinal()){
			g.setColor(Color.CYAN);
		}else{
			g.setColor(Color.YELLOW);
		}
		
		
		g.fillRect(road.getX(), road.getY(), Block.blockSize, Block.blockSize);
	}

}
