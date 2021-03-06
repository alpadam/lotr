package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ElfView implements EnemyView {
	static private BufferedImage bf;
	
	private Elf elf;		//T�rolja az elfet, amit ki kell rajzolnia

	public ElfView(Elf elf) {
		this.elf = elf;
		
		try {
			bf = ImageIO.read(new File("elfimage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bf, elf.getCurrentRoad().getX(), elf.getCurrentRoad().getY(), Block.blockSize, Block.blockSize, null);		
	}
}
