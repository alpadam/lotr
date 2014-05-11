package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.Timer;

/**
 * 
 * A j�t�kot vez�rl� oszt�ly, tartalamzza a p�ly�t �s a j�t�kost, ezen fel�l minden vez�rl�s�rt felel�s f�ggv�nyt.
 * A felhaszn�l� ezen kereszt�l kommunik�l a mappal, de a felhaszn�l�n k�v�li m�k�d�st (l�ptet�st) is ez a vez�rl� oszt�l ir�ny�tja. 
 *
 */
public class Controller extends JPanel implements Runnable, MouseListener, ActionListener { 
	
	Application app;
	
	private static int towerPrice = 20;			//torony �ra statikus v�ltoz�, �gy k�nnyen finomhangolhat�
	private static int trapPrice = 10;			//akad�ly �ra
	private static int gemPrice = 10;			//var�zsk� �ra
	public static int killedEnemyReward = 10;	//meg�lt ellenfelek�rt j�r� b�nusz m�gia
	
	public static int sumOfEnemies = 0;
	private static int maxEnemies = 21;
	private int roundCicle = 0;
	
	private int fogOn = 0; 
	private int fogOff = 0; 
	
	private Map map;
	private Player player;
	private boolean gameOver = false;
	
	public static Timer timer;
	
	private BufferedImage image = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
	
	private JPanel controlPanel;
	
	private JButton buyGem;
	private JRadioButton rangeExpander;
	private JRadioButton damageIncreaser;
	private JRadioButton shootingIncreaser;
	private JRadioButton movementDecreaser;
	
	private JLabel gemBuying;
	private JLabel gemPlacing;
	
	private JRadioButton placeRangeExpander;
	private JRadioButton placeDamageIncreaser;
	private JRadioButton placeShootingIncreaser;
	private JRadioButton placeMovementDecreaser;
	
	private JRadioButton removeGem;
	
	private JLabel duplikacio;
	private JLabel magic;
	private JLabel rangeExpanders;
	private JLabel damageIncreasers;
	private JLabel shootingIncreasers;
	private JLabel movementDecreasers;
	private JLabel ellensegszam;
	
	public Controller(Application app) {
		
		this.app = app;
		map = new Map();
		player = new Player();
		
		timer = new Timer(600, this);
		timer.setInitialDelay(8000);
		
		this.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		gemBuying = new JLabel("Var�zsk� v�s�rl�s:");
		gemPlacing = new JLabel("Var�zsk� lerak�s:");
		
		buyGem = new JButton("V�s�rol");
		rangeExpander = new JRadioButton("Hat�t�v n�vel� k�");
		damageIncreaser = new JRadioButton("Sebz�s n�vel� k�");
		shootingIncreaser = new JRadioButton("T�zel�st gyors�t� k�");
		movementDecreaser = new JRadioButton("Mozg�s lass�t� k�");
		
		placeRangeExpander = new JRadioButton("Hat�t�v n�vel� k� lerak");
		placeDamageIncreaser = new JRadioButton("Sebz�s n�vel� k� lerak");
		placeShootingIncreaser = new JRadioButton("T�zel�st gyors�t� k� lerak");
		placeMovementDecreaser = new JRadioButton("Mozg�s lass�t� k� lerak");
		
		duplikacio = new JLabel();
		ellensegszam = new JLabel();
		magic = new JLabel();
		rangeExpanders = new JLabel();
		damageIncreasers = new JLabel();
		shootingIncreasers = new JLabel();
		movementDecreasers = new JLabel();
		
		removeGem = new JRadioButton("K� kiv�tel");
		
		buyGem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	if(buyGem.isEnabled()){
		    		if(rangeExpander.isSelected()){
		    			buyGem(Type.RANGE_EXPANDER);	
		    		} else if(damageIncreaser.isSelected()){
		    			buyGem(Type.DAMAGE_INCREASER);
		    		} else if(shootingIncreaser.isSelected()){
		    			buyGem(Type.SHOOTING_INCREASER);
		    		} else if(movementDecreaser.isSelected()){
		    			buyGem(Type.MOVEMENT_DECREASER);
		    		}
		    	}
		    }
		});
		
		ButtonGroup gembuy = new ButtonGroup();
		rangeExpander.setSelected(true);
		gembuy.add(rangeExpander);
		gembuy.add(damageIncreaser);
		gembuy.add(shootingIncreaser);
		gembuy.add(movementDecreaser);
		
		ButtonGroup placeGems = new ButtonGroup();
		placeRangeExpander.setSelected(true);
		placeGems.add(placeRangeExpander);
		placeGems.add(placeDamageIncreaser);
		placeGems.add(placeShootingIncreaser);
		placeGems.add(placeMovementDecreaser);
		placeGems.add(removeGem);
		
		controlPanel.add(gemBuying);
		controlPanel.add(rangeExpander);
		controlPanel.add(damageIncreaser);
		controlPanel.add(shootingIncreaser);
		controlPanel.add(movementDecreaser);
		controlPanel.add(buyGem);
		controlPanel.add(gemPlacing);
		controlPanel.add(placeRangeExpander);
		controlPanel.add(placeDamageIncreaser);
		controlPanel.add(placeShootingIncreaser);
		controlPanel.add(placeMovementDecreaser);
		controlPanel.add(removeGem);
		controlPanel.add(new JSeparator());
		controlPanel.add(duplikacio);
		controlPanel.add(ellensegszam);
		controlPanel.add(magic);
		controlPanel.add(rangeExpanders);
		controlPanel.add(damageIncreasers);
		controlPanel.add(shootingIncreasers);
		controlPanel.add(movementDecreasers);

		this.add(controlPanel, BorderLayout.LINE_END);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
	

	/**
	 * 
	 * Torony �p�t�s�t v�gz� f�ggv�nyt. �rtes�ti a map-ot a torony �p�t�s�nek sz�nd�k�r�l.
	 *
	 */
	public boolean buildTower (int x, int y) {
		if (player.getMagic() >= towerPrice) {			//van-e el�g m�gi�ja a j�t�kosnak
			boolean built = map.createTower(x, y);
			if (built) {
				player.substractMagic(towerPrice);			//levonjuk a megfelel� m�gi�t
				magic.setText("Var�zser�: " + player.getMagic());
				Graphics g = image.getGraphics();
				map.getMap()[y][x].blockView.draw(g);
				g.dispose();
			    repaint();
				return true;
				
			} else {
				System.out.println("Torony �p�t�se sikertelen.");
				return false;
			}
		} else {
			System.out.println("Torony �p�t�se sikertelen, nincs el�g m�gia");
			return false;
		}
	}

	/**
	 * 
	 * Akad�ly �p�t�s�t v�gz� f�ggv�nyt. �rtes�ti a map-ot az akad�ly �p�t�s�nek sz�nd�k�r�l.
	 *
	 */
	public boolean buildTrap(int x, int y) {
		if (player.getMagic() >= trapPrice) {	//van-e el�g m�gi�ja a j�t�kosnak
			boolean built = map.createTrap(x, y);
			if (built) {
				player.substractMagic(trapPrice);			//levonjuk a megfelel� m�gi�t
				magic.setText("Var�zser�: " + player.getMagic());
				Graphics g = image.getGraphics();
				//map.refreshRoads()? 
				map.getMap()[y][x].blockView.draw(g);
				g.dispose();
			    repaint();
				return true;
				
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * Adott fajhoz tartoz� ellenf�l l�trehoz�sa.
	 *
	 */
	public void createEnemy(Class<? extends Enemy> c) {
		if (c == Elf.class) {
			map.initEnemy(new Elf());
		} else if (c == Hobbit.class) {
			map.initEnemy(new Hobbit());
		} else if (c == Human.class) {
			map.initEnemy(new Human());
		} else if (c == Dwarf.class) {
			map.initEnemy(new Dwarf());
		}

		sumOfEnemies++;	//eddigi ellenfelek sz�m�t n�velj�k
	}
	
	/**
	 * 
	 * K� behelyez�se �p�tm�nybe, legyen sz� toronyr�l vagy akad�lyr�l.
	 * �rtes�ti a map-ot a k� behelyez�s�nek sz�nd�k�r�l.
	 *
	 */
	public void placeGem(Type type, int x, int y) {
		MagicGem gem = player.getGem(type);	//lek�rj�k az adott t�pus� k�vet a j�t�kost�l
		
		if (gem != null) {	//csak akkor tudunk mit kezdeni, ha t�nyleg volt ilyen k�ve a j�t�kosnak
			boolean placed = map.placeGem(gem, x, y);
			if (!placed)
				player.addGem(gem); 	//ha nem siker�lt beraknunk a k�vet, akkor azt a j�t�kos visszakapja
			else {
				Graphics g = image.getGraphics();
				map.getMap()[y][x].blockView.draw(g);
				g.dispose();
			    repaint();
			}
		}
		
		refreshGemLabels();
	}

	
	/**
	 * 
	 * K� kiv�tele toronyb�l, tov�bbadja a k�r�st a map fel�.
	 *
	 */
	public void removeGem(int x, int y) {
		MagicGem gem = map.removeGem(x, y);

		if (gem != null) {
			player.addGem(gem);	//ha sikerrel vett�k ki a k�vet, akkor azt odaadja a j�t�kosnak
			Graphics g = image.getGraphics();
			map.getMap()[y][x].blockView.draw(g);
			g.dispose();
		    repaint();
		}
		
		refreshGemLabels();
	}

	/**
	 * 
	 * Var�zsk� v�s�rl�sa a j�t�kos karakter sz�m�ra. 
	 *
	 */
	public void buyGem(Type type) {
		if (player.getMagic() >= gemPrice) {	//csak akkor tudunk v�s�rolni, ha van r� m�gi�nk
			MagicGem gem = new MagicGem(type);
			player.addGem(gem);		//a j�t�kos eszk�zt�r�hoz ker�l a k�
			player.substractMagic(gemPrice);	//a megfelel� �sszeg levon�sra ker�l a m�gi�b�l
			magic.setText("Var�zser�: " + player.getMagic());
		}
		
		
		refreshGemLabels();
	}

	public void refreshGemLabels() {
		rangeExpanders.setText("Hat�t�v n�vel� k�vek: " + player.getNumberOfGemTypes(Type.RANGE_EXPANDER));
		damageIncreasers.setText("Sebz�s n�vel� k�vek: " + player.getNumberOfGemTypes(Type.DAMAGE_INCREASER));
		shootingIncreasers.setText("T�zel�s gyors�t� k�vek: " + player.getNumberOfGemTypes(Type.SHOOTING_INCREASER));
		movementDecreasers.setText("Mozg�s lass�t� k�vek: " + player.getNumberOfGemTypes(Type.MOVEMENT_DECREASER));
	}
	
	public void newGame() {
		map = new Map();
		player = new Player();
		gameOver = false;
		sumOfEnemies = 0;
		maxEnemies = 21;
		
		Block.b_id = 1;
		Road.r_id = 1;
		Tower.t_id = 1;
		MagicGem.id = 1;
		Enemy.id = 1;
	}
	
	public void endGame() {
		timer.stop();
		if (gameOver) {
			JOptionPane.showMessageDialog(this, "Sajnos nem nyert!");
		} else {
			JOptionPane.showMessageDialog(this, "Gratul�lunk, nyert!");
		}
		
		app.changeToMenu();
	}

	/**
	 * 
	 * A folyamatos fut�s�rt felel�s f�ggv�ny. 
	 *
	 */
	public void run() {
		map = new Map();
		player = new Player();
		gameOver = false;
		sumOfEnemies = 0;
		maxEnemies = 21;
		this.addMouseListener(this);
		
		try {
			map.initMap("defaultMap.txt");		//map inicializ�l�sa
			
			Graphics g = image.getGraphics();
			map.mapView.draw(g);
			g.dispose();
		    repaint();
		    
		    magic.setText("Var�zser�: " + player.getMagic());
		    refreshGemLabels();
		    
			timer.start();
			
		} catch (IOException e) {
			System.out.println("Hiba a p�lyaolvas�sn�l!");
		}
	}
	
	
	//IDE RAKD VISSZA

	
	
	static boolean gem_temp = false;
	
	@Override
	public void mouseClicked(MouseEvent event) {
		int clickX = event.getX();
		int clickY = event.getY();
		
		if(clickX > 800 || clickY > 550 )	{
			return;
		}
		
		int indexX = clickX / Block.blockSize;
		int indexY = clickY / Block.blockSize;
		
		if (placeRangeExpander.isSelected()){
			this.placeGem(Type.RANGE_EXPANDER, indexX, indexY);
		} else if (placeShootingIncreaser.isSelected()){
			this.placeGem(Type.SHOOTING_INCREASER, indexX, indexY);
		} else if (placeDamageIncreaser.isSelected()){
			this.placeGem(Type.DAMAGE_INCREASER, indexX, indexY);
		} else if (placeMovementDecreaser.isSelected()){
			this.placeGem(Type.MOVEMENT_DECREASER, indexX, indexY);
		} else if (removeGem.isSelected()){
			this.removeGem(indexX, indexY);
		}
		
		this.buildTower(indexX, indexY);

		this.buildTrap(indexX, indexY);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		fogOn = (((new Random().nextInt()) % 25) + 25) % 25; 
		fogOff++;
		
		ellensegszam.setText("Ellens�gek sz�ma: " + sumOfEnemies);
		
		if ((roundCicle <= 5) || ((roundCicle > 20) && (roundCicle <= 25)) || ((roundCicle > 30) && (roundCicle <= 35)) || ((roundCicle > 40) && (roundCicle <= 45))) {
			
			int random = (((new Random().nextInt()) % 4) + 4) % 4 ; // 0,1,2,3 sz�mok gener�l�sa
																	// negat�v sz�mokat ki kellett szedni bel�le, ez�rt van az az eltol�s
			switch (random) {
				case 0: 
					this.createEnemy(Elf.class);
					break;
					
				case 1:
					this.createEnemy(Hobbit.class);
					break;

				case 2: 
					this.createEnemy(Dwarf.class);
					break;

				case 3:
					this.createEnemy(Human.class);
					break;
					
				default: 
					break;
			}
			
			maxEnemies--;
		}
		
		roundCicle++;
		
		
		Graphics g = image.getGraphics();
		map.refreshRoads(g);
		g.dispose();
		repaint();
		
		if ((sumOfEnemies == 0) && (maxEnemies == 0)) {
			this.endGame();
		}
		
			
		List<Tower> towers= map.getTowers();
		
		if(fogOn == 3) {
			Map.FOG = true;
			fogOff = 0;
			for (int i=0; i < towers.size(); i++) {
				g = image.getGraphics();
				((TowerView)towers.get(i).blockView).draw(g);
				g.dispose();
			    repaint();
			}
		}
		
		if(Map.FOG == true && fogOff == 5) {
			Map.FOG = false;
			for (int i=0; i< towers.size(); i++) {
				g = image.getGraphics();
				towers.get(i).blockView.draw(g);
				g.dispose();
			    repaint();
			}
		}
		
		
		if (sumOfEnemies != 0) {
			for (int i=0; i< towers.size(); i++) {
				
				((TowerView)towers.get(i).blockView).drawShooting(this.getGraphics());
				g = image.getGraphics();
				
			}
			
			int duplicateRandom = (((new Random().nextInt()) % 20) + 20) % 20; 
			if(duplicateRandom == 3) {
				Map.DUPLICATE = true;
			} else {
				Map.DUPLICATE = false;
			}
			
			int killedEnemies = map.shootingTowers();
			player.addMagic(killedEnemies);
			magic.setText("Var�zser�: " + player.getMagic());
			
		}
		
		gameOver = map.moveEnemies();
		
		map.refreshRoads(g);
		g.dispose();
		repaint();
		
		if (gameOver) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			this.endGame();
		}
		
	}
	
	
	public Map getMap() {
		return map;
	}
	
}