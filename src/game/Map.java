package game;

import java.util.ArrayList;
import java.util.List;

public class Map {
	
	private List<Tower> towers;
	private List<Road> firstRoads;
	private List<Road> roads;
	private List<Block> map;		
	private List<Enemy> enemies;
	
	private Road finalRoad;
	
	public static int ciklus = 0;	// IDEIGLENES --> SZKELETONHOZ KELL
	
	
	public Map(int testNumber) {
		
		enemies = new ArrayList<Enemy>();
		map = new ArrayList<Block>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		firstRoads = new ArrayList<Road>();
		
		switch (testNumber) {
		/**
		K� elhelyez�se toronyba
		Kell: egy torony a towers list�ba
		*/
		case 1: 
			towers.add(new Tower());
			break;
		
		/**
		K� elhelyez�se akad�lyba
		Kell: egy �t a roads list�ba
		*/
		case 2:
			roads.add(new Road());
			break;
			
		/**
		Torony�p�t�s
		Kell: egy blokk a mapre
		*/	
		case 4:
			map.add(new Block());
			break;
		
		/**
		Akad�ly�p�t�s
		Kell: egy �t a roads list�ba
		(A mapre is fel k�ne helyezni, a pontos implement�ci�val nem foglalkoztunk.)
		*/	
		case 5:
			roads.add(new Road());
			break;
		
		/**
		Ellens�g l�trehoz�sa
		Kell: k�t �t (startpont) a firstRoads list�ba
		(A mapre is fel k�ne helyezni, a roadsba is bele k�ne rakni, a pontos implement�ci�val nem foglalkoztunk.)
		*/
		case 6:
			firstRoads.add(new Road());
			firstRoads.add(new Road());
			break;
		
		/**
		Var�zsk� kiv�tele toronyb�l
		Kell: egy torony a towers list�ba
		(Speci�lis konstruktor, csak a skeleton erej�ig lett l�trehozva: alapb�l van a toronyban egy k�.)
		*/
		case 7:
			towers.add(new Tower(true));
			break;
		
		/**
		Ellens�g l�ptet�s
		Kell: h�rom �t a roads list�ba, egy dwarf az els� �tra
		(road2: speci�lis konstruktor, csak a skeleton erej�ig lett l�trehozva: alapb�l van az �ton csapda)
		*/
		case 8:
			Road road1 = new Road();
			Road road2 = new Road(true);
			Road road3 = new Road();
			road3.setFinal();
			road1.setNext(road2);
			road2.setNext(road3);
			roads.add(road1);
			roads.add(road2);
			roads.add(road3);
			
			Dwarf dwarf = new Dwarf();
			dwarf.setRoad(road1);
			enemies.add(dwarf);
			road1.setEnemies(enemies);
			
			break;
		
		/**
		L�v�s toronnyal
		Kell: egy �t a roads list�ba, egy ellens�g az enemies list�ba, egy torony a towers list�ba
		(Az �tra felrakjuk az enemies tagjait, ami jelen esetben csak egy dwarfot jelent.)
		*/
		case 9:
			Road road = new Road();
			enemies.add(new Dwarf());
			road.setEnemies(enemies);
			roads.add(road);
			towers.add(new Tower());
			break;

		default:
			break;
		}
		
	}

	/**
	Torony�p�t�s
	Nem a Controller run() f�ggv�ny�ben h�vjuk meg, mert ezt a f�ggv�nyt 
	k�zvetlen�l a user fogja h�vni
	 */
	public void createTower(){							
		System.out.println("Map --> createTower()");		
		boolean isTower = map.get(0).isTower();
		System.out.println("Torony-e?:" + isTower);
		Tower tower = new Tower();
		System.out.println("<<create>> tower ");
		towers.add(tower);
		System.out.println("Map --> towers.add(tower)");
		map.remove(0);
		System.out.println("block removed");
	}
	
	public void createTrap(){
		System.out.println("Map --> createTrap()");
		
		Road r = roads.get(0);
		
		System.out.println("Road-e?: " + r.isRoad());
		
		System.out.println("Trap-e?: " + r.isTrap());
		
		r.setTrap();
		
		System.out.println("Trap-e?: " + r.isTrap());
	}
	
	public int shootingTowers() {
		
		System.out.println("Map --> shootingTowers()");
		
		int killedEnemies = 0;

		System.out.println("Halt-e meg valaki a l�v�sek sor�n?: " + towers.get(0).shoot(roads) ); 
		
		killedEnemies++;
		
		refreshEnemies();
		
		return killedEnemies;
	}

	public boolean moveEnemies() {
		
		System.out.println("Map --> moveEnemies()");
		
		boolean isFinal = false;
		
		Dwarf dwarf = (Dwarf) enemies.get(0);
		System.out.println("Dwarf's trappedValue:" + dwarf.getTrappedValue());
		
		isFinal = dwarf.move();
		isFinal = dwarf.move();
		isFinal = dwarf.move();
		
		System.out.println("El�rt�k-e az utols� road-ot?: " + isFinal);
		
		return isFinal;
	}
	
	public void placeGem(MagicGem magicGem, boolean tmp) {
		/*
		 * a tmp v�ltoz�ra csak a szkeletonban van sz�ks�g, ezzel jelezz�k, hogy melyik teszteset h�v�dik meg:
		 * a k� elhelyez�se toronyba (=false), vagy a k� elhelyez�se akad�lyba (=true)
		 */ 	
		System.out.println("Map --> placeGem()");
			
		if (tmp) {
			roads.get(0).isRoad();
			roads.get(0).isTrap();
			roads.get(0).getGemType();
			roads.get(0).placeGem(magicGem);
				
		} else {
			towers.get(0).isRoad();
			towers.get(0).isTower();
			towers.get(0).getGemType();
			towers.get(0).placeGem(magicGem);
		}
	}
		
	public MagicGem removeGem() {
		System.out.println("Map -->removeGem()");
		
		towers.get(0).isTower();
		MagicGem gem = towers.get(0).removeGem();
			
		System.out.println("<-- gem");
		return gem;
	}
	
	public void initEnemy(Enemy enemy) {
		
		System.out.println("Map --> initEnemy()");
		
		Road road = firstRoads.get(ciklus);
		
		enemies.add(enemy);
		road.addEnemy(enemy);
		
		enemy.setCurrentRoad(road);
		
		ciklus++;
	}
	
	public void refreshEnemies() {
		System.out.println("Controller --> refreshEnemies()");
	}
	
	public void createMap() {
	}
	
	public void initMap() {
	}
}