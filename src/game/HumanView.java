package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class HumanView implements EnemyView {
	private ImageIcon imgicon;
	private BufferedImage bf;
	
	private Human human;

	public HumanView(Human human) {
		this.human = human;
		
		try {
			bf = ImageIO.read(new File("humanimage.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bf, human.getCurrentRoad().getX(), human.getCurrentRoad().getY(), Block.blockSize, Block.blockSize, null);
		
	}
}
