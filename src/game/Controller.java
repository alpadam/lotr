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

		map = new Map();
		player = new Player();

		sumOfEnemies = 0;
	}

	public Map getMap() {
		return map;
	}

	public void buildTower(int blockId) {
		if (player.getMagic() >= towerPrice) {
			map.createTower(blockId);
			player.substractMagic(towerPrice);
			System.out.println("Torony " + "Block#" + blockId + " helyen l�trehozva.");
		}
		else {
			System.out.println("Torony �p�t�se sikertelen.");
		}
	}

	public void buildTrap(int roadId) {
		if (player.getMagic() >= trapPrice) {
			map.createTrap(roadId);
			player.substractMagic(trapPrice);
			System.out.println("Akad�ly Road#" + roadId + " helyen l�trehozva.");
		}
		else {
			System.out.println("Akad�ly �p�t�se sikertelen.");
		}
	}

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

		sumOfEnemies++;
	}

	public void placeGem(Type type, int id, boolean tmp) {
		/* 
		 * a tmp v�ltoz�ra csak a szkeletonban van sz�ks�g, ezzel jelezz�k, hogy melyik teszteset h�v�dik meg:
		 * a k� elhelyez�se toronyba (=false), vagy a k� elhelyez�se akad�lyba (=true)
		 */ 
		MagicGem gem = player.getGem(type);
		
		if (gem != null) {
			map.placeGem(gem, id, tmp);
		}
		else {
			if (tmp)
				System.out.println("Var�zsk� elhelyez�se sikertelen Road#" + id + "-n.");
			else
				System.out.println("Var�zsk� elhelyez�se sikertelen Torony#" + id + "-n.");
		}
	}

	public void removeGem(int id) {

		MagicGem gem = map.removeGem(id);

		if (gem != null) {
			player.addGem(gem);
			System.out.println("Torony#" + id + " �p�tm�nyb�l var�zsk� kiv�ve.");
		}
		else {
			System.out.println("Nem siker�lt var�zsk�vet kivenni Torony#" + id + " �p�tm�nyb�l");
		}
	}

	public void buyGem(Type type) {
		if (player.getMagic() >= gemPrice) {
			MagicGem gem = new MagicGem(type);
			player.addGem(gem);
			player.substractMagic(gemPrice);
			System.out.println(type + " var�zsk� megv�s�rolva.");
		}
		else {
			System.out.println("Nem siker�lt megv�s�rolni a var�zsk�vet.");
		}
	}

	public void newGame() {
		
		map = new Map();
		player = new Player();

		sumOfEnemies = 0;
		
	}

	public void run() throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		while (!line.equals("exit")) {

			line = in.readLine();
			
			newGame();							// kinull�zzuk az eddigieket

			if (line.equals("teszt")) {
				
				try {

					System.out.println("P�lya f�jl: '<n�v>':");
					String mapFile = "esetek/" + in.readLine() + ".txt";
					map = new Map();
					player = new Player();
					map.initMap(mapFile);

					System.out.println("Parancs f�jl: '<n�v>':");
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
					System.out.println("Rossz f�jln�v!");
				}

			} else if (line.equals("game")) {

				System.out.println("Parancsok:");
				System.out.println("P�lya bet�lt�se: defaultMap.txt-b�l");
				map.initMap("defaultMap.txt"); 

				while (true) {
					String row2 = in.readLine();
					if (row2 == null)
						break;

					parancskezeles(row2);
				}

			} else {
				System.out.println("'teszt' illetve 'game' k�z�l v�laszthat!");
			}

		}
	}

	private void parancskezeles(String command) {

		String[] commandSplit = command.split(" ");

		if (commandSplit.length >= 1) { // csak akkor, ha valami sz�veget
										// tartalmaz a command

			switch (commandSplit[0]) {

			case "createEnemy":

				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
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
						System.out.println("nincs ilyen elles�gt�pus!");
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
						System.out.println("jobbra mozg�s");
						map.moveEnemies();
						break;
	
					case "BAL":
						Map.RIGHT = false;
						System.out.println("balra mozg�s");
						map.moveEnemies();
						break;
	
					default:
						System.out.println("nincs ilyen param�ter!");
						break;
				}

				break;

			case "shoot":
				if (commandSplit.length <= 2) {
					System.out.println("Nincs el�g argumentum!");
					break;
				} else {

					if (commandSplit[1].equals("OFF")
							&& commandSplit[2].equals("OFF")) {
						Map.DUPLICATE = false;
						Map.FOG = false;
						System.out.println("K�d off, duplicate: off");

					} else if (commandSplit[1].equals("ON")
							&& commandSplit[2].equals("OFF")) {
						Map.FOG = true;
						Map.DUPLICATE = false;
						System.out.println("K�d on, duplicate: off");

					} else if (commandSplit[1].equals("OFF")
							&& commandSplit[2].equals("ON")) {
						Map.FOG = false;
						Map.DUPLICATE = true;
						System.out.println("K�d off, duplicate: on");
					} else if (commandSplit[1].equals("ON")
							&& commandSplit[2].equals("ON")) {
						Map.DUPLICATE = true;
						Map.FOG = true;
						System.out.println("K�d on, duplicate: on");

					} else {
						System.out.println("Nem megfelel� argumnetum!");
					}

					 map.shootingTowers();
				}

				break;

			case "buildTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int id = Integer.parseInt(commandSplit[1]);

					this.buildTrap(id);
				} catch (NumberFormatException e) {
					System.out.println("Nem j� azons�t�!");
					break;
				}
				break;

			case "buildTower":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int hol = Integer.parseInt(commandSplit[1]);
					this.buildTower(hol);
					
				} catch (NumberFormatException e) {
					System.out.println("Nem j� azons�t�!");
					break;
				}
				break;

			case "gemToTower":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int toronyazonosito = Integer.parseInt(commandSplit[2]);

					switch (commandSplit[1]) {

					case "rangeExpander":
						this.placeGem(Type.RANGE_EXPANDER, toronyazonosito, false);
						break;
					case "damageIncreaser":
						this.placeGem(Type.DAMAGE_INCREASER, toronyazonosito, false);
						break;
					case "shootingIncreaser":
						this.placeGem(Type.SHOOTING_INCREASER, toronyazonosito, false);
						break;

					default:
						System.out.println("Nincs ilyen gem t�pus!");
						break;

					}

				} catch (NumberFormatException e) {
					System.out.println("Nem j� azonos�t�");
					break;
				}

				break;

			case "gemToTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int roadazonosito = Integer.parseInt(commandSplit[1]);

					this.placeGem(Type.MOVEMENT_DECREASER, roadazonosito, true);
				} catch (NumberFormatException e) {
					System.out.println("Nem j� azons�t�!");
				}

				break;

			case "buyGem":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
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
					System.out.println("Nincs ilyen gem t�pus!");
					break;

				}

				break;

			case "removeGem":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int toronyazonosito = Integer.parseInt(commandSplit[1]);

					this.removeGem(toronyazonosito);
				} catch (NumberFormatException e) {
					System.out.println("Nem j� azons�t�!");
				}
				break;

			case "simulate":
				
				System.out.println("Szimul�l�s: \t");
				map.moveEnemies();							//L�p�s
				int killedEnemies = map.shootingTowers();	//L�v�s
				
				player.addMagic(killedEnemies);				// meg�lt ellenfelek ut�n j�r� extra magic
				break;

			case "listInventory":

				System.out.println("Inventory:");
				for (int i = 0; i < player.getInventory().size(); i++) {
					MagicGem tempGem = player.getInventory().get(i);
					System.out.println("Var�zsk�#" + tempGem.getGemID() + "\t" + tempGem);
					
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
					+ "Akad�ly-e:"+(r.isTrap() ? "Igen" : "Nem")+ "\t "+ " Ellens�gek:");
					for (int j = 0; j < r.getEnemies().size(); j++) {
						System.out.println("\t\t\t\t\t\t\t\t" +"Enemy#"+ r.getEnemies().get(j).getEnemyID());
						
					}
					
				}
				
				break;

			case "listMap":

				System.out.println("P�lya:");
				Block[][] tempBlock = map.getMap();
				for (int i = 0; i < tempBlock.length; i++) {
					if(i != 0) //txt-be �r�skor legyen itt enter, hogy p�lyak�nt n�zzen ki
						System.out.println("\n");
					for (int j = 0; j < tempBlock[i].length; j++) {
						System.out.println("Block#" + tempBlock[i][j].getBlockID() + " ");
					}
				}
				
				break;

			case "help":

				System.out.println("Parancsok:\n");
				System.out.println("createEnemy <enemy t�pus>\t\t\t\t Ellens�g l�trehoz�s\n"
								+ "move <ir�ny> \t\t\t\t\t\t Ellens�gek mozgat�sa\n"
								+ "shoot <k�d ON/OFF> <kett�szed� l�ved�k ON/OFF> \t\t L�v�s toronnyal\n"
								+ "buildTrap <�tazonos�t�> \t\t\t\t Akad�ly �p�t�se a megfelel� �tra\n"
								+ "buildTower <hol> \t\t\t\t\t Torony �p�t�se a megfelel� blokkra\n"
								+ "gemToTower <gem t�pus> <toronyazonos�t�> \t\t Torony fejleszt�se a megfelel� gemmel.\n"
								+ "gemToTrap <roadazonos�t�> \t\t\t\t Var�zsk� elhelyez�se a megfelel� akad�lyba\n"
								+ "buyGem <gem t�pus> \t\t\t\t\t Megfelel� var�zsk� v�s�rl�sa\n"
								+ "removeGem <toronyazonos�t�> \t\t\t\t Var�zsk� kiv�tele a megfelel� toronyb�l\n"
								+ "simulate \t\t\t\t\t\t Szimul�ci�: Ellens�gek l�ptet�se, tornyok l�v�se.\n"
								+ "listInventory \t\t\t\t\t\t J�t�kos rendelkez�s�re �ll� var�zsk�vek list�z�sa\n"
								+ "listEnemies \t\t\t\t\t\t A p�ly�n l�v� ellens�gek kilist�z�sa.\n"
								+ "listTowers \t\t\t\t\t\t A p�ly�n l�v� tornyok kilist�z�sa.\n"
								+ "listRoads \t\t\t\t\t\t A p�ly�n l�v� utak kilist�z�sa.\n"
								+ "listMap \t\t\t\t\t\t A p�lya kirajzol�sa.\n"
								+ "help \t\t\t\t\t\t\t Parancsok kilist�z�sa.\n"
								+ "exit \t\t\t\t\t\t\t Kil�p�s a programob�l.");
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

}