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
		
	}

	/**
	Toronyépítés
	Nem a Controller run() függvényében hívjuk meg, mert ezt a függvényt 
	közvetlenül a user fogja hívni
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

		System.out.println("Halt-e meg valaki a lövések során?: " + towers.get(0).shoot(roads) ); 
		
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
		
		System.out.println("Elérték-e az utolsó road-ot?: " + isFinal);
		
		return isFinal;
	}
	
	public void placeGem(MagicGem magicGem, boolean tmp) {
		/*
		 * a tmp változóra csak a szkeletonban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
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