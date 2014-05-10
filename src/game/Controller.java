package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	
	private Map map;
	private Player player;
	private boolean gameOver = false;
	
	
	private Timer timer;
	

	public Controller(Application app) {
		
		this.app = app;
		map = new Map();
		player = new Player();
		
		timer = new Timer(600,this);
		//timer.setInitialDelay(5000);
	}

	public Map getMap() {
		return map;
	}

	/**
	 * 
	 * Torony építését végzõ függvényt. Értesíti a map-ot a torony építésének szándékáról.
	 *
	 */
	/*public void buildTower (int blockId) {
		if ((player.getMagic() >= towerPrice) && (blockId < Block.b_id)) {	//van-e elég mágiája a játékosnak
			boolean built = map.createTower(blockId);
			if (built) {
				player.substractMagic(towerPrice);	//levonjuk a megfelelõ mágiát
				System.out.println("Torony " + "Block#" + blockId + " helyen létrehozva.");
			} else {
				System.out.println("Torony építése sikertelen.");
			}
		}
		else {
			System.out.println("Torony építése sikertelen.");
		}
	}*/
	
	public boolean buildTower (int x, int y) {
		if (player.getMagic() >= towerPrice) {			//van-e elég mágiája a játékosnak
			boolean built = map.createTower(x, y);
			if (built) {
				player.substractMagic(towerPrice);			//levonjuk a megfelelõ mágiát
				map.getMap()[y][x].blockView.draw(this.getGraphics());
				return true;
				
			} else {
				System.out.println("Torony építése sikertelen.");
				return false;
			}
		}
		else {
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
				map.getMap()[y][x].blockView.draw(this.getGraphics());
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
	/*public void placeGem(Type type, int id, boolean tmp) {
		/* 
		 * a tmp változóra csak a prototípusban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
		 */ 
		/*MagicGem gem = player.getGem(type);	//lekérjük az adott típusú követ a játékostól
		
		if (gem != null) {	//csak akkor tudunk mit kezdeni, ha tényleg volt ilyen köve a játékosnak
			boolean placed = map.placeGem(gem, id, tmp);
			if (!placed)
				player.addGem(gem); 	//ha nem sikerült beraknunk a követ, akkor azt a játékos visszakapja
		}
		else {
			if (tmp)
				System.out.println("Varázskõ elhelyezése sikertelen Road#" + id + "-n.");
			else
				System.out.println("Varázskõ elhelyezése sikertelen Torony#" + id + "-n.");
		}
	}*/
	
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
			else
				map.getMap()[y][x].blockView.draw(this.getGraphics());
		}
	}

	/**
	 * 
	 * Kõ kivétele toronyból, továbbadja a kérést a map felé.
	 *
	 */
	/*public void removeGem(int id) {
		MagicGem gem = map.removeGem(id);

		if (gem != null) {
			player.addGem(gem);	//ha sikerrel vettük ki a követ, akkor azt aodaadja a játékosnak
			System.out.println("Torony#" + id + " építménybõl varázskõ kivéve.");
		}
		else {
			System.out.println("Nem sikerült varázskövet kivenni Torony#" + id + " építménybõl");
		}
	}*/
	
	/**
	 * 
	 * Kõ kivétele toronyból, továbbadja a kérést a map felé.
	 *
	 */
	public void removeGem(int x, int y) {
		MagicGem gem = map.removeGem(x, y);

		if (gem != null) {
			player.addGem(gem);	//ha sikerrel vettük ki a követ, akkor azt odaadja a játékosnak
			map.getMap()[y][x].blockView.draw(this.getGraphics());
		}
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
			System.out.println(type + " varázskõ megvásárolva.");
		}
		else {
			System.out.println("Nem sikerült megvásárolni a varázskövet.");
		}
	}

	public void newGame() {
		map = new Map();
		player = new Player();
		gameOver = false;
		
		Block.b_id = 1;
		Road.r_id = 1;
		Tower.t_id = 1;
		MagicGem.id = 1;
		Enemy.id = 1;

		sumOfEnemies = 0;
	}
	
	
	public void endGame(){
		
		if(gameOver) {
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
		this.addMouseListener(this);
		
		
		try {
			map.initMap("defaultMap.txt");		//map inicializálása
		} catch (IOException e) {
			System.out.println("Hiba a pályaolvasásnál!");
		}
		
		map.mapView.draw(this.getGraphics());
		
		timer.start();
		
	}

		
		
		
		
		
		
		
		
		/*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		
		try{
			
			while (!line.equals("exit")) {
				line = in.readLine();
				
				newGame();							// kinullázzuk az eddigieket
	
				if (line.equals("teszt")) {		//elõlre definiált tesztesetet akarunk futtatni
					try {
						System.out.println("Pálya fájl: '<név>'.txt:");
						String mapFile =in.readLine();
						map = new Map();
						player = new Player();
						map.initMap(mapFile);	//map inicializálása
	
						System.out.println("Parancs fájl: '<név>'.txt:");
						String commandFile = in.readLine();
						BufferedReader fileReader = new BufferedReader(
								new FileReader(commandFile));
						
						System.setOut(new PrintStream(new FileOutputStream("output.txt")));
						while (true) {
							String row = fileReader.readLine();
							if (row == null) {
								fileReader.close();
								break;
							}
							parancskezeles(row);
						}
						
						System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
						
						System.out.println("Az eredmény az output.txt-be íródott ki!");
						System.out.println("Válasszon a 'teszt' vagy 'game' üzemmód közül!");
	
					} catch (IOException io) {
						System.out.println("Rossz fájlnév!");
					}
	
				} else if (line.equals("game")) {	//a parancsokat manuálisan adjuk meg, mintha játszanánk
					System.out.println("Parancsok:");
					System.out.println("Pálya betöltése: defaultMap.txt-bõl");	//elõlre definiált a pálya
					map.initMap("defaultMap.txt"); 
					
					System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
					
					while (true) {
						String row2 = in.readLine();
						if (row2 == null) {
							break;
						}
						parancskezeles(row2);
						if (gameOver) {
							System.out.println("A játék véget ért! Sajnos nem nyert!");
							System.out.println("Válasszon a 'teszt' vagy 'game' üzemmód közül!");
							break;
						}
						
					}
	
				} else {
					System.out.println("'teszt' illetve 'game' közül választhat!");
				}
			}
			}catch(IOException e){
				System.out.println("asd");
			}*/

	/**
	 * 
	 * A bemeneti nyelvben specifikált parancsok értelmezéséért felelõs függvény. 
	 *
	 */
	private void parancskezeles(String command) {
		String[] commandSplit = command.split(" ");

		if (commandSplit.length >= 1) { // csak akkor, ha valami szöveget
										// tartalmaz a command

			switch (commandSplit[0]) {

			case "createEnemy":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				switch (commandSplit[1]) {
					case "dwarf":
						this.createEnemy(Dwarf.class);
	
						break;
					case "hobbit":
						 this.createEnemy(Hobbit.class);
	
						break;
					case "human":
						 this.createEnemy(Human.class);
	
						break;
					case "elf":
						this.createEnemy(Elf.class);
	
						break;
					default:
						System.out.println("nincs ilyen elleségtípus!");
						break;
				}

				break;

			case "move":
				if (commandSplit.length == 1) {
					Map.RIGHT = false;
					gameOver = map.moveEnemies();
					break;
				}

				switch (commandSplit[1]) {
					case "JOBB":
						Map.RIGHT = true;
						System.out.println("jobbra mozgás");
						gameOver = map.moveEnemies();
						break;
	
					case "BAL":
						Map.RIGHT = false;
						System.out.println("balra mozgás");
						gameOver = map.moveEnemies();
						break;
	
					default:
						System.out.println("nincs ilyen paraméter!");
						break;
				}

				break;

			case "shoot":
				if (commandSplit.length <= 2) {
					System.out.println("Nincs elég argumentum!");
					break;
				} else {

					if (commandSplit[1].equals("OFF")
							&& commandSplit[2].equals("OFF")) {
						Map.DUPLICATE = false;
						Map.FOG = false;
						System.out.println("Köd off, duplicate: off");

					} else if (commandSplit[1].equals("ON")
							&& commandSplit[2].equals("OFF")) {
						Map.FOG = true;
						Map.DUPLICATE = false;
						System.out.println("Köd on, duplicate: off");

					} else if (commandSplit[1].equals("OFF")
							&& commandSplit[2].equals("ON")) {
						Map.FOG = false;
						Map.DUPLICATE = true;
						System.out.println("Köd off, duplicate: on");
					} else if (commandSplit[1].equals("ON")
							&& commandSplit[2].equals("ON")) {
						Map.DUPLICATE = true;
						Map.FOG = true;
						System.out.println("Köd on, duplicate: on");

					} else {
						System.out.println("Nem megfelelõ argumnetum!");
					}

					 map.shootingTowers();
				}

				break;

			case "buildTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int id = Integer.parseInt(commandSplit[1]);

					//this.buildTrap(id);
				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonsító!");
					break;
				}
				break;

			case "buildTower":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int hol = Integer.parseInt(commandSplit[1]);
					//this.buildTower(hol);
					
				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonsító!");
					break;
				}
				break;

			case "gemToTower":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int toronyazonosito = Integer.parseInt(commandSplit[2]);

					switch (commandSplit[1]) {

					case "rangeExpander":
						//this.placeGem(Type.RANGE_EXPANDER, toronyazonosito, false);
						break;
					case "damageIncreaser":
						//this.placeGem(Type.DAMAGE_INCREASER, toronyazonosito, false);
						break;
					case "shootingIncreaser":
						//this.placeGem(Type.SHOOTING_INCREASER, toronyazonosito, false);
						break;

					default:
						System.out.println("Nincs ilyen gem típus!");
						break;

					}

				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonosító");
					break;
				}

				break;

			case "gemToTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int roadazonosito = Integer.parseInt(commandSplit[1]);

					//this.placeGem(Type.MOVEMENT_DECREASER, roadazonosito, true);
				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonsító!");
				}

				break;

			case "buyGem":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}

				switch (commandSplit[1]) {
				case "rangeExpander":
					 this.buyGem(Type.RANGE_EXPANDER);
					
					break;
				case "damageIncreaser":
					 this.buyGem(Type.DAMAGE_INCREASER);
				
					break;
				case "shootingIncreaser":
					 this.buyGem(Type.SHOOTING_INCREASER);
					
					break;
				case "movementDecreaser":
					 this.buyGem(Type.MOVEMENT_DECREASER);
					
					break;
				default:
					System.out.println("Nincs ilyen gem típus!");
					break;

				}

				break;

			case "removeGem":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int toronyazonosito = Integer.parseInt(commandSplit[1]);
					//this.removeGem(toronyazonosito);
					
				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonsító!");
				}
				break;

			case "simulate":
				System.out.println("Szimulálás: \t");
				map.moveEnemies();							//Lépés
				int killedEnemies = map.shootingTowers();	//Lövés
				
				player.addMagic(killedEnemies);				// megölt ellenfelek után járó extra magic
				break;

			case "listInventory":
				System.out.println("Inventory:  magic-->" + player.getMagic());
				
				for (int i = 0; i < player.getInventory().size(); i++) {
					MagicGem tempGem = player.getInventory().get(i);
					System.out.println("Varázskõ#" + tempGem.getGemID() + "\t" + tempGem);
				}

				break;

			case "listEnemies":
				
				System.out.println("Az Enemy lista:");
				for (int i = 0; i < map.getEnemies().size(); i++) {
					System.out.println(map.getEnemies().get(i));
				}
				break;

			case "listTowers":
				
				System.out.println("Torony lista:");
				for (int i = 0; i < map.getTowers().size(); i++) {
					System.out.println(map.getTowers().get(i));
				}
				break;

			case "listRoads":

				System.out.println("Road lista:");
				for (int i = 0; i < map.getRoads().size(); i++) {
					Road r = map.getRoads().get(i);
					
					System.out.println(r);
					for (int j = 0; j < r.getEnemies().size(); j++) {
						System.out.println("\t\t\t\t\t\t\t\t" +"Ellenség#"+ r.getEnemies().get(j).getEnemyID());
					}
					
				}
				
				break;

			case "listMap":

				System.out.println("Pálya:");
				Block[][] tempBlock = map.getMap();
				for (int i = 0; i < tempBlock.length; i++) {
					if(i != 0) 
						System.out.println("\n");
					for (int j = 0; j < tempBlock[i].length; j++) {
						System.out.println("Block#" + tempBlock[i][j].getBlockID() + " ");
					}
				}
				
				break;

			case "help":

				System.out.println("Parancsok:\n");
				System.out.println("createEnemy <enemy típus>\t\t\t\t Ellenség létrehozás\n"
								+ "move <irány> \t\t\t\t\t\t Ellenségek mozgatása\n"
								+ "shoot <köd ON/OFF> <kettészedõ lövedék ON/OFF> \t\t Lövés toronnyal\n"
								+ "buildTrap <útazonosító> \t\t\t\t Akadály építése a megfelelõ útra\n"
								+ "buildTower <hol> \t\t\t\t\t Torony építése a megfelelõ blokkra\n"
								+ "gemToTower <gem típus> <toronyazonosító> \t\t Torony fejlesztése a megfelelõ gemmel.\n"
								+ "gemToTrap <roadazonosító> \t\t\t\t Varázskõ elhelyezése a megfelelõ akadályba\n"
								+ "buyGem <gem típus> \t\t\t\t\t Megfelelõ varázskõ vásárlása\n"
								+ "removeGem <toronyazonosító> \t\t\t\t Varázskõ kivétele a megfelelõ toronyból\n"
								+ "simulate \t\t\t\t\t\t Szimuláció: Ellenségek léptetése, tornyok lövése.\n"
								+ "listInventory \t\t\t\t\t\t Játékos rendelkezésére álló varázskövek listázása\n"
								+ "listEnemies \t\t\t\t\t\t A pályán lévõ ellenségek kilistázása.\n"
								+ "listTowers \t\t\t\t\t\t A pályán lévõ tornyok kilistázása.\n"
								+ "listRoads \t\t\t\t\t\t A pályán lévõ utak kilistázása.\n"
								+ "listMap \t\t\t\t\t\t A pálya kirajzolása.\n"
								+ "help \t\t\t\t\t\t\t Parancsok kilistázása.\n"
								+ "exit \t\t\t\t\t\t\t Kilépés a programoból.");
				break;

			case "exit":

				System.out.println("Exit...");
				System.exit(0);
				
				break;
				
			default:
				System.out.println("Nincs ilyen parancs!");
				break;
			}
		}
	}
	
	static boolean gem_temp = false;
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
		int clickX = event.getX();
		int clickY = event.getY();
		
		int indexX = clickX / Block.blockSize;
		int indexY = clickY / Block.blockSize;
		
		
		System.out.println(event.getX());
		System.out.println(event.getY());
		System.out.println(indexX);
		System.out.println(indexY);
		
		Block[][] m = map.getMap();
		
		
		System.out.println(m[indexY][indexX].isRoad());
		
		
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
	
	static boolean b = true; //ez csak azért van itt hogy csak egy ellenfelet hozzon létre
	boolean x = true;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		
		System.out.println("RoundCicle: " + roundCicle);
		if ((roundCicle <= 5) || ((roundCicle > 20) && (roundCicle <= 25)) || ((roundCicle > 30) && (roundCicle <= 35)) || ((roundCicle > 40) && (roundCicle <= 45))) {
			
			int random = (((new Random().nextInt()) % 4) + 4) % 4 ; // 0,1,2,3 számok generálása
																	// negatív számokat ki kellett szedni belõle, ezért van az az eltolás
			
			System.out.println(random);
			
			switch(random){
			
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
		
		
		if (gameOver) {
			this.endGame();
		}
		
		if ((sumOfEnemies == 0) && (maxEnemies == 0)) {
			this.endGame();
		}
		
		map.refreshRoads(this.getGraphics());	
		gameOver = map.moveEnemies();
			
		List<Tower> towers= map.getTowers();

		if(sumOfEnemies != 0) {
			for(int i=0; i< towers.size(); i++){
					((TowerView)towers.get(i).blockView).drawShooting(this.getGraphics());
				}
			int killedEnemies = map.shootingTowers();
			player.addMagic(killedEnemies);
		}
		
		this.getGraphics().drawString(new Integer(player.getMagic()).toString(), 400, 340);
		
			
	}
		
}