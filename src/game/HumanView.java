package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HumanView implements EnemyView {
	static private BufferedImage bf;
	
	private Human human;		//T�rolja az embert, amit ki kell rajzolnia

	public HumanView(Human human) {
		this.human = human;
		
		try {
			bf = ImageIO.read(new File("humanimage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bf, human.getCurrentRoad().getX(), human.getCurrentRoad().getY(), Block.blockSize, Block.blockSize, null);
	}
}
