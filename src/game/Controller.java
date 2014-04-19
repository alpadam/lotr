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

	public void buildTower() {
		System.out.println("Controller --> buildTower() ");
		System.out.println("Varazsero:" + player.getMagic());

		// map.createTower();

		player.substractMagic(towerPrice);
		System.out.println("Player --> substractMagic(towerPrice)");
		System.out.println("Varazsero:" + player.getMagic());
	}

	public void buildTrap() {

		System.out.println("Controller --> buildTrap()");
		System.out.println("Varazsero:" + player.getMagic());

		// map.createTrap();

		player.substractMagic(trapPrice);
		System.out.println("Varazsero:" + player.getMagic());

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

	public void placeGem(Type type, boolean tmp) {
		/**
		 * a tmp változóra csak a szkeletonban van szükség, ezzel jelezzük, hogy
		 * melyik teszteset hívódik meg: a kõ elhelyezése toronyba (=false),
		 * vagy a kõ elhelyezése akadályba (=true)
		 */

		System.out.println("Controller --> placeGem(" + type + ") ");
		MagicGem gem = player.getGem(type);

		map.placeGem(gem, tmp);
	}

	public void removeGem() {

		System.out.println("Controller --> removeGem()");

		MagicGem gem = map.removeGem();

		if (gem != null)
			player.addGem(gem);
	}

	public void buyGem(Type type) {

		MagicGem gem = new MagicGem(type);

		player.addGem(gem);

		player.substractMagic(gemPrice);
		System.out.println(gem.getGemTypeName() + " varázskõ megvásárolva!");
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

					System.out.println("Pálya fájl: '<név>.txt':");
					String mapFile = in.readLine();
					map.initMap(mapFile); // MEG KELL MÉG ÍRNI

					System.out.println("Parancs fájl: '<név>.txt':");
					String commandFile = in.readLine();
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
					System.out.println("véletlenszerû lépés");
					break;
				}

				switch (commandSplit[1]) {
				case "JOBB":
					Map.RIGHT = true;
					System.out.println("jobbra mozgás");
					break;

				case "BAL":
					Map.RIGHT = false;
					System.out.println("balra mozgás");
					break;

				default:
					System.out.println("nincs ilyen paraméter!");
					break;
				}

				// map.moveEnemies();

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
						Map.DUPLICATE = false;
						Map.FOG = true;
						System.out.println("Köd on, duplicate: off");

					} else if (commandSplit[1].equals("OFF")
							&& commandSplit[2].equals("ON")) {
						Map.DUPLICATE = false;
						Map.FOG = true;
						System.out.println("Köd off, duplicate: on");
					} else if (commandSplit[1].equals("ON")
							&& commandSplit[2].equals("ON")) {
						Map.DUPLICATE = true;
						Map.FOG = true;
						System.out.println("Köd on, duplicate: on");

					} else {
						System.out.println("Nem megfelelõ argumnetum!");
					}

					// map.shootingTowers();
				}

				break;

			case "buildTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs elég argumentum!");
					break;
				}
				try {
					int id = Integer.parseInt(commandSplit[1]);

					// this.buildTrap(id);

					System.out.println("Csapda létrejött a " + id
							+ " azonosítójú útra");
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

					// this.buildTower(hol);

					System.out.println("Torony létrejött a " + hol + " helyre");
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
						// this.placeGem(Type.RANGE_EXPANDER, toronyazonosito);
						System.out.println("Range Expander gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "damageIncreaser":
						// this.placeGem(Type.DAMAGE_INCREASER,
						// toronyazonosito);
						System.out.println("damage Increaser gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "shootingIncreaser":
						// this.placeGem(Type.SHOOTING_INCREASER,
						// toronyazonosito);
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

					// this.PlaceGem(Type.MOVEMENT_DECREASER);

					System.out.println("Gem elhelyezve" + roadazonosito
							+ " helyre");
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
					System.out.println("nincs ilyen gem típus!");
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

					// this.removeGem(toronyazonosito);

					System.out.println("Gem kivéve a  " + toronyazonosito
							+ " toronyból");
				} catch (NumberFormatException e) {
					System.out.println("Nem jó azonsító!");
				}
				break;

			case "simulate":

				// map.moveEnemies(true);
				// this.shootingTowers(false,false);
				System.out.println("simulálás - lépés, lövés");

				break;

			case "listInventory":

				System.out.println("Inventory:");
				for (int i = 0; i < player.getInventory().size(); i++) {
					MagicGem tempGem = player.getInventory().get(i);
					System.out.println("Varázskõ#" + tempGem.getGemID() + "\t" + tempGem.getGemTypeName());
					
				}

				break;

			case "listEnemies":

				System.out.println("Az Enemy lista:");
				for (int i = 0; i < map.getEnemies().size(); i++) {
					
					Enemy e = map.getEnemies().get(i);
					System.out.println("\t"+"Enemy#"+ e.getEnemyID() + "\t"
							+ e.getClass().toString() + "\t" + "Életerõ:"+ e.getHealth()
							+"\t" + "Road:" + "Road#" + e.getCurrentRoad().getRoadID());
				}
				break;

			case "listTowers":

				System.out.println("Torony lista:");
				for (int i = 0; i < map.getTowers().size(); i++) {
					Tower t = map.getTowers().get(i);
					System.out.println("Tower#" + t.getTowerID() + "\t" + "gem: MÉG MEG KELL ÍRNI" );
					
				}
				break;

			case "listRoads":

				System.out.println("Road lista:");
				for (int i = 0; i < map.getRoads().size(); i++) {
					Road r = map.getRoads().get(i);
					System.out.println("\t" +"Road#"+ r.getRoadID() + "\t" + "helye: ??? MEG KELL ÍRNI!" + "\t"
					+ "Akadály-e:"+(r.isTrap() ? "Igen" : "Nem")+ "\t "+ " Ellenségek:");
					for (int j = 0; j < r.getEnemies().size(); j++) {
						System.out.println("\t\t\t\t\t\t\t\t" +"Enemy#"+ r.getEnemies().get(j).getEnemyID());
						
					}
					
				}
				
				break;

			case "listMap":

				System.out.println("Pálya:");
				// map.listMap();
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