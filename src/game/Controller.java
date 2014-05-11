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
 * A játékot vezérlõ osztály, tartalamzza a pályát és a játékost, ezen felül minden vezérlésért felelõs függvényt.
 * A felhasználó ezen keresztül kommunikál a mappal, de a felhasználón kívüli mûködést (léptetést) is ez a vezérlõ osztál irányítja. 
 *
 */
public class Controller extends JPanel implements Runnable, MouseListener, ActionListener { 
	
	Application app;
	
	private static int towerPrice = 20;			//torony ára statikus változó, így könnyen finomhangolható
	private static int trapPrice = 10;			//akadály ára
	private static int gemPrice = 10;			//varázskõ ára
	public static int killedEnemyReward = 10;	//megölt ellenfelekért járó bónusz mágia
	
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
		
		timer = new Timer(750, this);
		timer.setInitialDelay(8000);
		
		this.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		gemBuying = new JLabel("Varázskõ vásárlás:");
		gemPlacing = new JLabel("Varázskõ lerakás:");
		
		buyGem = new JButton("Vásárol");
		rangeExpander = new JRadioButton("Hatótáv növelõ kõ");
		damageIncreaser = new JRadioButton("Sebzés növelõ kõ");
		shootingIncreaser = new JRadioButton("Tüzelést gyorsító kõ");
		movementDecreaser = new JRadioButton("Mozgás lassító kõ");
		
		placeRangeExpander = new JRadioButton("Hatótáv növelõ kõ lerak");
		placeDamageIncreaser = new JRadioButton("Sebzés növelõ kõ lerak");
		placeShootingIncreaser = new JRadioButton("Tüzelést gyorsító kõ lerak");
		placeMovementDecreaser = new JRadioButton("Mozgás lassító kõ lerak");
		
		duplikacio = new JLabel();
		ellensegszam = new JLabel();
		magic = new JLabel();
		rangeExpanders = new JLabel();
		damageIncreasers = new JLabel();
		shootingIncreasers = new JLabel();
		movementDecreasers = new JLabel();
		
		removeGem = new JRadioButton("Kõ kivétel");
		
		buyGem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	if(buyGem.isEnabled()){
		    		if(rangeExpander.isSelected()){
		    			System.out.println("RANGEEXPANDER VÁSÁR");
		    			buyGem(Type.RANGE_EXPANDER);	
		    		} else if(damageIncreaser.isSelected()){
		    			System.out.println("damageIncreaser VÁSÁR");
		    			buyGem(Type.DAMAGE_INCREASER);
		    		} else if(shootingIncreaser.isSelected()){
		    			System.out.println("SHOOTING VÁSÁR");
		    			buyGem(Type.SHOOTING_INCREASER);
		    		} else if(movementDecreaser.isSelected()){
		    			System.out.println("MOVEMENT VÁSÁR");
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
		//placeRangeExpander.setSelected(true);
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
	 * Torony építését végzõ függvényt. Értesíti a map-ot a torony építésének szándékáról.
	 *
	 */
	public boolean buildTower (int x, int y) {
		if (player.getMagic() >= towerPrice) {			//van-e elég mágiája a játékosnak
			boolean built = map.createTower(x, y);
			if (built) {
				player.substractMagic(towerPrice);			//levonjuk a megfelelõ mágiát
				magic.setText("Varázserõ: " + player.getMagic());
				Graphics g = image.getGraphics();
				map.getMap()[y][x].blockView.draw(g);
				g.dispose();
			    repaint();
				return true;
				
			} else {
				System.out.println("Torony építése sikertelen.");
				return false;
			}
		} else {
			System.out.println("Torony építése sikertelen, nincs elég mágia");
			return false;
		}
	}

	/**
	 * 
	 * Akadály építését végzõ függvényt. Értesíti a map-ot az akadály építésének szándékáról.
	 *
	 */
	public boolean buildTrap(int x, int y) {
		if (player.getMagic() >= trapPrice) {	//van-e elég mágiája a játékosnak
			boolean built = map.createTrap(x, y);
			if (built) {
				player.substractMagic(trapPrice);			//levonjuk a megfelelõ mágiát
				magic.setText("Varázserõ: " + player.getMagic());
				Graphics g = image.getGraphics();
				//map.refreshRoads()? 
				map.getMap()[y][x].blockView.draw(g);
				g.dispose();
			    repaint();
				return true;
				
			} else {
				System.out.println("Akadály építése sikertelen.");
				return false;
			}
		}
		else {
			System.out.println("Akadály építése sikertelen.");
			return false;
		}
	}
	
	/**
	 * 
	 * Adott fajhoz tartozó ellenfél létrehozása.
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

		sumOfEnemies++;	//eddigi ellenfelek számát növeljük
	}
	
	/**
	 * 
	 * Kõ behelyezése építménybe, legyen szó toronyról vagy akadályról.
	 * Értesíti a map-ot a kõ behelyezésének szándékáról.
	 *
	 */
	public void placeGem(Type type, int x, int y) {
		MagicGem gem = player.getGem(type);	//lekérjük az adott típusú követ a játékostól
		
		if (gem != null) {	//csak akkor tudunk mit kezdeni, ha tényleg volt ilyen köve a játékosnak
			boolean placed = map.placeGem(gem, x, y);
			if (!placed)
				player.addGem(gem); 	//ha nem sikerült beraknunk a követ, akkor azt a játékos visszakapja
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
	 * Kõ kivétele toronyból, továbbadja a kérést a map felé.
	 *
	 */
	public void removeGem(int x, int y) {
		MagicGem gem = map.removeGem(x, y);

		if (gem != null) {
			player.addGem(gem);	//ha sikerrel vettük ki a követ, akkor azt odaadja a játékosnak
			Graphics g = image.getGraphics();
			map.getMap()[y][x].blockView.draw(g);
			g.dispose();
		    repaint();
		}
		
		refreshGemLabels();
	}

	/**
	 * 
	 * Varázskõ vásárlása a játékos karakter számára. 
	 *
	 */
	public void buyGem(Type type) {
		if (player.getMagic() >= gemPrice) {	//csak akkor tudunk vásárolni, ha van rá mágiánk
			MagicGem gem = new MagicGem(type);
			player.addGem(gem);		//a játékos eszköztárához kerül a kõ
			player.substractMagic(gemPrice);	//a megfelelõ összeg levonásra kerül a mágiából
			magic.setText("Varázserõ: " + player.getMagic());
			System.out.println(type + " varázskõ megvásárolva.");
		}
		else {
			System.out.println("Nem sikerült megvásárolni a varázskövet.");
		}
		
		refreshGemLabels();
	}

	public void refreshGemLabels() {
		rangeExpanders.setText("Hatótáv növelõ kövek: " + player.getNumberOfGemTypes(Type.RANGE_EXPANDER));
		damageIncreasers.setText("Sebzés növelõ kövek: " + player.getNumberOfGemTypes(Type.DAMAGE_INCREASER));
		shootingIncreasers.setText("Tüzelés gyorsító kövek: " + player.getNumberOfGemTypes(Type.SHOOTING_INCREASER));
		movementDecreasers.setText("Mozgás lassító kövek: " + player.getNumberOfGemTypes(Type.MOVEMENT_DECREASER));
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
			JOptionPane.showMessageDialog(this, "Gratulálunk, nyert!");
		}
		
		app.changeToMenu();
	}

	/**
	 * 
	 * A folyamatos futásért felelõs függvény. 
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
			map.initMap("defaultMap.txt");		//map inicializálása
			
			Graphics g = image.getGraphics();
			map.mapView.draw(g);
			g.dispose();
		    repaint();
		    
		    magic.setText("Varázserõ: " + player.getMagic());
		    refreshGemLabels();
		    
			timer.start();
			
		} catch (IOException e) {
			System.out.println("Hiba a pályaolvasásnál!");
		}
	}
	
	
	//IDE RAKD VISSZA

	
	
	static boolean gem_temp = false;
	
	@Override
	public void mouseClicked(MouseEvent event) {
		int clickX = event.getX();
		int clickY = event.getY();
		
		int indexX = clickX / Block.blockSize;
		int indexY = clickY / Block.blockSize;
		
		/*System.out.println(event.getX());
		System.out.println(event.getY());
		System.out.println(indexX);
		System.out.println(indexY);*/
		
		Block[][] m = map.getMap();
		
		System.out.println(m[indexY][indexX].isRoad());
		
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
		
		fogOn = (((new Random().nextInt()) % 20) + 20) % 20 ; 
		fogOff++;
		
		ellensegszam.setText("Ellenségek száma: " + sumOfEnemies);
		
		if ((roundCicle <= 5) || ((roundCicle > 20) && (roundCicle <= 25)) || ((roundCicle > 30) && (roundCicle <= 35)) || ((roundCicle > 40) && (roundCicle <= 45))) {
			
			int random = (((new Random().nextInt()) % 4) + 4) % 4 ; // 0,1,2,3 számok generálása
																	// negatív számokat ki kellett szedni belõle, ezért van az az eltolás
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
			System.out.println("LEERESZKEDETT A KÖD");
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
				
				
				//if (!Map.FOG)
					//towers.get(i).blockView.draw(g);
				//else
					//((TowerView)towers.get(i).blockView).fogOn(g);
			    //repaint();
			   // g.dispose();
			}
			
			
			int duplicateRandom = (((new Random().nextInt()) % 10) + 10) % 10 ; 
			if(duplicateRandom == 3) {
				Map.DUPLICATE = true;
			} else {
				Map.DUPLICATE = false;
			}
			
			int killedEnemies = map.shootingTowers();
			player.addMagic(killedEnemies);
			magic.setText("Varázserõ: " + player.getMagic());
			
		}
		
		
		
		gameOver = map.moveEnemies();
		
		map.refreshRoads(g);
		g.dispose();
		repaint();
		
		if (gameOver) {
			this.endGame();
		}
		
	}
	
	public Map getMap() {
		return map;
	}
	
}