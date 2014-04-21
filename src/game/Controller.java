package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {

	private static int towerPrice = 20;
	private static int trapPrice = 10;
	private static int gemPrice = 5;
	public static int killedEnemyReward = 10;

	private Map map;
	private Player player;
	private int killedEnemies;
	private int sumOfEnemies;

	public Controller(int testNumber) {

		map = new Map(testNumber);
		player = new Player(testNumber);

		sumOfEnemies = 0;
	}

	public Map getMap() {
		return map;
	}

	public void buildTower(int blockId) {
		if (player.getMagic() >= towerPrice) {
			map.createTower(blockId);
			player.substractMagic(towerPrice);
			System.out.println("Torony " + "Block#" + blockId + " helyen létrehozva.");
		}
		else {
			System.out.println("Torony építése sikertelen.");
		}
	}

	public void buildTrap(int roadId) {
		if (player.getMagic() >= trapPrice) {
			map.createTrap(roadId);
			player.substractMagic(trapPrice);
			System.out.println("Akadály Road#" + roadId + " helyen létrehozva.");
		}
		else {
			System.out.println("Akadály építése sikertelen.");
		}
	}

	public void createEnemy(Class<? extends Enemy> c) {

		if (c == Elf.class) {
			
			Elf elf = new Elf();
			map.initEnemy(elf);
			
		} else if (c == Hobbit.class) {

			Hobbit hobbit = new Hobbit();					//Lehet nem a legszebb megoldás, 
			map.initEnemy(hobbit);							//de így láttam egyszerûnek. :D - Zsoca
			
		} else if (c == Human.class) {

			Human human = new Human();
			map.initEnemy(human);
			
		} else if (c == Dwarf.class) {

			Dwarf dwarf = new Dwarf();
			map.initEnemy(dwarf);

		}

		sumOfEnemies++;

	}

	public void placeGem(Type type, int id, boolean tmp) {
		/* this.placeGem ??!?!?!?!?
		 * a tmp változóra csak a szkeletonban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
		 */ 
		MagicGem gem = player.getGem(type);
		
		if (gem != null) {
			map.placeGem(gem, id, tmp);
			if (tmp) {
				System.out.println("Gem elhelyezve" + id + " helyre");
			} else {
				switch (gem.getType()) {

				case RANGE_EXPANDER:
					map.placeGem(gem, id, false);
					System.out.println("RangeExpander varázskõ Torony#" + id + "-ban/ben elhelyezve.");
					break;
				case DAMAGE_INCREASER:
					this.placeGem(Type.DAMAGE_INCREASER, id, false);
					System.out.println("DamageIncreaser varázskõ Torony#" + id + "-ban/ben elhelyezve.");
					break;
				case SHOOTING_INCREASER:
					this.placeGem(Type.SHOOTING_INCREASER, id, false);
					System.out.println("ShootingIncreaser varázskõ Torony#" + id + "-ban/ben elhelyezve.");
					break;

				default:
					System.out.println("Nincs ilyen gem típus!");
					break;
				}
			}
		}
		else {
			
		}
	}

	public void removeGem(int id) {

		MagicGem gem = map.removeGem(id);

		if (gem != null) {
			player.addGem(gem);
			System.out.println(id + " építménybõl varázskõ kivéve.");
		}
		else {
			System.out.println("Nem sikerült varázskövet kivenni " + id + " építménybõl");
		}
	}

	public void buyGem(Type type) {
		if (player.getMagic() >= gemPrice) {
			MagicGem gem = new MagicGem(type);
			player.addGem(gem);
			player.substractMagic(gemPrice);
			System.out.println(type + " varázskõ megvásárolva.");
		}
		else {
			System.out.println("Nem sikerült megvásárolni a varázskövet.");
		}
	}

	public void newGame() {
	}

	public void run() throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		while (!line.equals("exit")) {

			line = in.readLine();

			if (line.equals("teszt")) {

				try {
					System.out.println("teszt");

					System.out.println("Pálya fájl: '<név>':");
					String mapFile = "esetek/" + in.readLine() + ".txt";
					map.initMap(mapFile); // MEG KELL MÉG ÍRNI

					System.out.println("Parancs fájl: '<név>':");
					String commandFile = "esetek/" + in.readLine() + ".txt";
					BufferedReader fileReader = new BufferedReader(
							new FileReader(commandFile));

					while (true) {
						String row = fileReader.readLine();
						if (row == null) {
							fileReader.close();
							break;
						}
						parancskezeles(row);
					}

				} catch (IOException io) {

					System.out.println("Rossz fájlnév!");

				}

			} else if (line.equals("game")) {

				System.out.println("game");

				System.out.println("Pálya betöltése: defaultMap.txt-bõl");
				map.initMap("defaultMap.txt"); // MEG KELL MÉG ÍRNI

				while (true) {
					String row2 = in.readLine();
					if (row2 == null)
						break;

					parancskezeles(row2);
				}

			} else {
				System.out.println("'teszt' illetve 'game' közül választhat!");
			}

		}

	}

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
					map.moveEnemies();
					break;
				}

				switch (commandSplit[1]) {
				case "JOBB":
					Map.RIGHT = true;
					System.out.println("jobbra mozgás");
					map.moveEnemies();
					break;

				case "BAL":
					Map.RIGHT = false;
					System.out.println("balra mozgás");
					map.moveEnemies();
					break;

				default:
					System.out.println("nincs ilyen paraméter!");
					break;
				}

				 map.moveEnemies();

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

					this.buildTrap(id);
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

					this.buildTower(hol);
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
						this.placeGem(Type.RANGE_EXPANDER, toronyazonosito, false);
						System.out.println("Range Expander gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "damageIncreaser":
						this.placeGem(Type.DAMAGE_INCREASER, toronyazonosito, false);
						System.out.println("damage Increaser gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "shootingIncreaser":
						this.placeGem(Type.SHOOTING_INCREASER, toronyazonosito, false);
						System.out.println("Shooting Increaser gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;

					default:
						System.out.println("nincs ilyen gem típus!");
						break;

					}

				} catch (NumberFormatException e) {
					System.out.println("nem jó azonosító");
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

					this.placeGem(Type.MOVEMENT_DECREASER, roadazonosito, true);
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

					this.removeGem(toronyazonosito);

					System.out.println("Gem kivéve a  " + toronyazonosito
							+ " toronyból");
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

				System.out.println("Inventory:");
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
					System.out.println("\t" +"Road#"+ r.getRoadID() + "\t" + "helye: Block#" + r.getBlockID() + "\t"
					+ "Akadály-e:"+(r.isTrap() ? "Igen" : "Nem")+ "\t "+ " Ellenségek:");
					for (int j = 0; j < r.getEnemies().size(); j++) {
						System.out.println("\t\t\t\t\t\t\t\t" +"Enemy#"+ r.getEnemies().get(j).getEnemyID());
						
					}
					
				}
				
				break;

			case "listMap":

				System.out.println("Pálya:");
				Block[][] tempBlock = map.getMap();
				for (int i = 0; i < tempBlock.length; i++) {
					if(i != 0) //txt-be íráskor legyen itt enter, hogy pályaként nézzen ki
						System.out.println("\n");
					for (int j = 0; j < tempBlock[i].length; j++) {
						System.out.println("Block#" + tempBlock[i][j].getBlockID() + " ");
					}
				}
				
				break;

			case "help":

				System.out.println("Parancsok:\n");
				System.out
						.println("createEnemy <enemy típus>\t\t\t\t Ellenség létrehozás\n"
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

				System.out.println("Exit");
				break;
			default:
				System.out.println("Nincs ilyen parancs!");
				break;
			}
		}
	}

}