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
		
		map.createTower();
		
		player.substractMagic(towerPrice);
		System.out.println("Player --> substractMagic(towerPrice)");
		System.out.println("Varazsero:" + player.getMagic());
	}
	
	public void buildTrap() {
		
		System.out.println("Controller --> buildTrap()");
		System.out.println("Varazsero:" + player.getMagic());
		
		map.createTrap();
		
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
	
	
		private void parancskezeles(String command){
			
			System.out.println(command);
			
			
			
			/*switch (key) {
			case value:
				
				break;

			default:
				break;
			}*/
			
			
		}
		

		
	}