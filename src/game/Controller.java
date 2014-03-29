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
	
	/**
	K�t f�ggv�ny van, amit a Controller run() f�ggv�nye automatikusan h�v
	Az ellens�gek l�ptet�se �s a torony l�v�se
	Ellens�gek l�ptet�se: megh�v�dik a map (Map) moveEnemies() f�ggv�nye
	Torony l�v�se: megh�v�dik a map (Map) shootingTowers() f�ggv�nye,
				   ami a meg�lt ellens�gek sz�m�val t�r vissza
				   hogy bemutassuk, ki�ratjuk hogyan n� a j�t�kos m�gi�ja,
				   ha meg�l egy ellens�get
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