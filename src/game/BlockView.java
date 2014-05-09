package game;

import java.awt.Color;
import java.awt.Graphics;

public class BlockView implements View {
	
	private Block block;
	
	public BlockView(Block block) {
		this.block = block;
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		
		g.setColor(Color.GREEN);
		g.fillRect(block.getX(), block.getY(), Block.blockSize, Block.blockSize);
		

	}
	

}
