package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DwarfView implements EnemyView {
	static private BufferedImage bf;
	
	private Dwarf dwarf;

	public DwarfView(Dwarf dwarf) {
		this.dwarf = dwarf;
		
		try {
			bf = ImageIO.read(new File("dwarfimage.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bf, dwarf.getCurrentRoad().getX(), dwarf.getCurrentRoad().getY(), Block.blockSize, Block.blockSize, null);
		
	}
}
