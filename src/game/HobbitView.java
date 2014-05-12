package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HobbitView implements EnemyView {
	static private BufferedImage bf;
	
	private Hobbit hobbit;		//Tárolja a hobbitot, amit ki kell rajzolnia

	public HobbitView(Hobbit hobbit) {
		this.hobbit = hobbit;
		
		try {
			bf = ImageIO.read(new File("hobbitimage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bf, hobbit.getCurrentRoad().getX(), hobbit.getCurrentRoad().getY(), Block.blockSize, Block.blockSize, null);
	}
}
