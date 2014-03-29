package game;

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
		System.out.println("Dwarf létrejött!");
		map.initEnemy(dwarf);
		
		sumOfEnemies++;
		
		Elf elf = new Elf();
		map.initEnemy(elf);
		
		sumOfEnemies++;
		
	}
	
	public void placeGem(Type type, boolean tmp) {
		/**
		 * a tmp változóra csak a szkeletonban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
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
	
	/**
	Két függvény van, amit a Controller run() függvénye automatikusan hív
	Az ellenségek léptetése és a torony lövése
	Ellenségek léptetése: meghívódik a map (Map) moveEnemies() függvénye
	Torony lövése: meghívódik a map (Map) shootingTowers() függvénye,
				   ami a megölt ellenségek számával tér vissza
				   hogy bemutassuk, kiíratjuk hogyan nõ a játékos mágiája,
				   ha megöl egy ellenséget
	testNumber csak a szkeleton miatt van			   
	*/
	public void run(int testNumber){
		
		System.out.println("Controller --> run()");
		
		switch (testNumber) {
			
			case 8:
				map.moveEnemies();
				break;
			case 9:
				System.out.println("Varazsero:" + player.getMagic());
				int killedEnemies = map.shootingTowers();
				System.out.println("killedEnemies: " + killedEnemies);
				player.addMagic(killedEnemies);
				System.out.println("Varazsero:" + player.getMagic());
				break;
			
			default:
				break;
		}
		
		
	}
}