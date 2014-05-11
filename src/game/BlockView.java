package game;

import java.awt.Color;
import java.awt.Graphics;

public class BlockView implements View {
	private Block block;
	
	public BlockView() {
	}
	
	public BlockView(Block block) {
		this.block = block;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0,102,0));
		g.fillRect(block.getX(), block.getY(), Block.blockSize, Block.blockSize);
	}
	
}
