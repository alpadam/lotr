package game;

import java.awt.Graphics;

public class MapView implements View {
	private Map map;
	
	public MapView(Map map) {
		this.map = map;
	}

	@Override
	public void draw(Graphics g) {
		Block[][] blocks = map.getMap();
		
		for (int i = 0; i < blocks.length; i++){
			for (int j = 0; j < blocks[i].length; j++){
				
				Block tempBlock = blocks[i][j];
				tempBlock.blockView.draw(g);
			}
		}
	}
}
