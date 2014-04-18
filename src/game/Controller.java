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
	
	public Map getMap(){
		return map;
	}
	
	public void buildTower(){
		System.out.println("Controller --> buildTower() ");		
		System.out.println("Varazsero:" + player.getMagic());
		
		//map.createTower();
		
		player.substractMagic(towerPrice);
		System.out.println("Player --> substractMagic(towerPrice)");
		System.out.println("Varazsero:" + player.getMagic());
	}
	
	public void buildTrap() {
		
		System.out.println("Controller --> buildTrap()");
		System.out.println("Varazsero:" + player.getMagic());
		
		//map.createTrap();
		
		player.substractMagic(trapPrice);
		System.out.println("Varazsero:" + player.getMagic());
		
	}
	
	public void createEnemy() {
		
		System.out.println("Controller --> createEnemy() ");
		
		Dwarf dwarf = new Dwarf();
		System.out.println("Dwarf l�trej�tt!");
		map.initEnemy(dwarf);
		
		sumOfEnemies++;
		
		Elf elf = new Elf();
		map.initEnemy(elf);
		
		sumOfEnemies++;
		
	}
	
	public void placeGem(Type type, boolean tmp) {
		/**
		 * a tmp v�ltoz�ra csak a szkeletonban van sz�ks�g, ezzel jelezz�k, hogy melyik teszteset h�v�dik meg:
		 * a k� elhelyez�se toronyba (=false), vagy a k� elhelyez�se akad�lyba (=true)
		 */ 
			
		System.out.println("Controller --> placeGem(" + type + ") ");
		MagicGem gem = player.getGem(type);
			
		map.placeGem(gem, tmp);
	}
	
	public void removeGem() {
			
		System.out.println("Controller --> removeGem()");
			
		MagicGem gem = map.removeGem();
			
		if (gem!= null)
			player.addGem(gem);
	}
		
	public void buyGem(Type type) {
			
		System.out.println("Controller --> buyGem("+ type +") ");
		System.out.println("Varazsero:" + player.getMagic());
			
		MagicGem gem = new MagicGem(type);
			
		player.addGem(gem);
			
		player.substractMagic(gemPrice);
		System.out.println("Varazsero:" + player.getMagic());
	}
	
	public void newGame() {
	}
	
	public void run() throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		
		while(!line.equals("exit")) {
			
			
			line = in.readLine();
			
			if(line.equals("teszt")){
				
				try{
					System.out.println("teszt");
					
					
					System.out.println("P�lya f�jl: '<n�v>.txt':");
					String mapFile = in.readLine();
					map.initMap(mapFile);										//MEG KELL M�G �RNI
					
					System.out.println("Parancs f�jl: '<n�v>.txt':");
					String commandFile = in.readLine();
					BufferedReader fileReader = new BufferedReader(new FileReader(commandFile));
					
					while(true) {
						String row = fileReader.readLine();
						if(row == null){
							fileReader.close();
							break;
						}
						parancskezeles(row);
					}
						
					}catch(IOException io){
					
						System.out.println("Rossz f�jln�v!");
					
					}
					
				}else if(line.equals("game")){
					
					System.out.println("game");
					
					System.out.println("P�lya bet�lt�se: defaultMap.txt-b�l");
					map.initMap("defaultMap.txt");					//MEG KELL M�G �RNI
					
					while(true) {
						String row2 = in.readLine();
						if(row2 == null) break;
						
						parancskezeles(row2);
					}
					
					
				}else{
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
					// this.createEnemy(Dwarf.class);
					System.out.println("dwarf l�trehoz�s");
					break;
				case "hobbit":
					// this.createEnemy(Hobbit.class);
					System.out.println("hobbit l�trehoz�s");
					break;
				case "human":
					// this.createEnemy(Human.class);
					System.out.println("human l�trehoz�s");
					break;
				case "elf":
					// this.createEnemy(Elf.class);
					System.out.println("elf l�trehoz�s");
					break;
				default:
					System.out.println("nincs ilyen elles�gt�pus!");
					break;

				}

				break;

			case "move":

				switch (commandSplit[1]) {
				case "JOBB":
					Map.RIGHT = true;
					System.out.println("jobbra mozg�s");
					break;

				case "BAL":
					Map.RIGHT = false;
					System.out.println("balra mozg�s");
					break;

				default:
					System.out.println("nincs ilyen param�ter!");
					break;
				}
				
				// map.moveEnemies();
				
				break;

			case "shoot":
				if (commandSplit.length <= 2) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}else{
					
					if(commandSplit[1].equals("OFF") && commandSplit[2].equals("OFF")){
						Map.DUPLICATE = false;
						Map.FOG = false;
						System.out.println("K�d off, duplicate: off");
						
					}else if(commandSplit[1].equals("ON") && commandSplit[2].equals("OFF")){
						Map.DUPLICATE = false;
						Map.FOG = true;
						System.out.println("K�d on, duplicate: off");
						
					}else if(commandSplit[1].equals("OFF") && commandSplit[2].equals("ON")){
						Map.DUPLICATE = false;
						Map.FOG = true;
						System.out.println("K�d off, duplicate: on");
					}else if(commandSplit[1].equals("ON") && commandSplit[2].equals("ON")){
						Map.DUPLICATE = true;
						Map.FOG = true;
						System.out.println("K�d on, duplicate: on");
						
					}else{
						System.out.println("Nem megfelel� argumnetum!");
					}
					
					//map.shootingTowers();
				}
				
				break;

			case "buildTrap":
				if (commandSplit.length == 1) {
					System.out.println("Nincs el�g argumentum!");
					break;
				}
				try {
					int id = Integer.parseInt(commandSplit[1]);

					// this.buildTrap(id);

					System.out.println("Csapda l�trej�tt a " + id
							+ " azonos�t�j� �tra");
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

					// this.buildTower(hol);

					System.out.println("Torony l�trej�tt a " + hol + " helyre");
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
						// this.placeGem(Type.RANGE_EXPANDER, toronyazonosito);
						System.out.println("Range Expander gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "damageIncreaser":
						// this.placeGem(Type.DAMAGE_INCREASER, toronyazonosito);
						System.out.println("damage Increaser gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
					case "shootingIncreaser":
						// this.placeGem(Type.SHOOTING_INCREASER, toronyazonosito);
						System.out.println("Shooting Increaser gem elhelyezve "
								+ toronyazonosito + " toronyba");
						break;
						
					default:
						System.out.println("nincs ilyen gem t�pus!");
						break;

					}

				} catch (NumberFormatException e) {
					System.out.println("nem j� azonos�t�");
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

					// this.PlaceGem(Type.MOVEMENT_DECREASER);

					System.out.println("Gem elhelyezve" + roadazonosito
							+ " helyre");
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
					// this.buyGem(Type.RANGE_EXPANDER);
					System.out.println("Range Expander gem megv�s�rolva!");
					break;
				case "damageIncreaser":
					// this.buyGem(Type.DAMAGE_INCREASER);
					System.out.println("Damage increaser gem megv�s�rolva!");
					break;
				case "shootingIncreaser":
					// this.buyGem(Type.SHOOTING_INCREASER);
					System.out.println(" Shooting Increaser gem megv�s�rolva!");
					break;
				case "movementDecreaser":
					// this.buyGem(Type.MOVEMENT_DECREASER);
					System.out.println("Movement decreaser gem megv�s�rolva!");
					break;
				default:
					System.out.println("nincs ilyen gem t�pus!");
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

					// this.removeGem(toronyazonosito);

					System.out.println("Gem kiv�ve a  " + toronyazonosito
							+ " toronyb�l");
				} catch (NumberFormatException e) {
					System.out.println("Nem j� azons�t�!");
				}
				break;

			case "simulate":

				// map.moveEnemies(true);
				// this.shootingTowers(false,false);
				System.out.println("simul�l�s - l�p�s, l�v�s");

				break;

			case "listInventory":

				System.out.println("listInventory");
				// player

				break;

			case "listEnemies":

				System.out.println("Ellens�gek:");
				// map.listEnemies();
				break;

			case "listTowers":

				System.out.println("Tornyok:");
				// map.listTowers();
				break;

			case "listRoads":

				System.out.println("Utak:");
				// map.listRoads();
				break;

			case "listMap":

				System.out.println("P�lya:");
				// map.listMap();
				break;

			case "help":

				System.out.println("Parancsok:\n");
				System.out
						.println("createEnemy <enemy t�pus>\t\t\t\t Ellens�g l�trehoz�s\n"
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

				System.out.println("Exit");
				break;
			default:
				System.out.println("Nincs ilyen parancs!");
				break;
			}
		}
	}
		

		
	}