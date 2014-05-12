package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class RoadView extends BlockView {
	
	private Road road;		//T�roljuk az utat, amit ki kell rajzolnunk
	
	public RoadView(Road road) {
		this.road = road;
	}

	@Override
	public void draw(Graphics g) {
		
		if (road.isFinal()) {
			g.setColor(Color.CYAN);		//A v�gs� �t sz�ne ci�nk�k
		} else {
			g.setColor(Color.YELLOW);	//Az utak sz�ne s�rga		
		}
		
		
		int x = road.getX();
		int y = road.getY();
		
		
		g.fillRect(x, y, Block.blockSize, Block.blockSize);
		
		if (road.isTrap()) {
			if (road.getGemType() == Type.MOVEMENT_DECREASER){
				g.setColor(Color.BLACK);		//Amennyiben a csapd�ban van k�, a csapda fekete sz�n�; X alak�
			}else{
				g.setColor(Color.RED);			//Egy�bk�nt piros; X alak�
			}
				
			g.drawLine(x, y, x + Block.blockSize-1, y + Block.blockSize-1);
			g.drawLine(x + Block.blockSize-1, y, x, y + Block.blockSize-1);
		} 
		
		List<Enemy> enemies = road.getEnemies();
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).enemyView.draw(g);
		}
	}

}
