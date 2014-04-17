package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
	
	private List<Tower> towers;
	private List<Road> roads;
	private List<Block> map;		
	private List<Enemy> enemies;
	
	private HashMap<Tower, List<Road>> towerRoads;
	
	public static boolean RIGHT = false;
	
	private Road finalRoad;
	private Road firstRoad;

	
	
	
	public Map(int testNumber) {
		
		enemies = new ArrayList<Enemy>();
		map = new ArrayList<Block>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		
		towerRoads = new HashMap<Tower, List<Road>>();
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
		
		int killedEnemies = 0;

		for (int i = 0; i < towers.size(); i++) {
			
			Tower tempTower = towers.get(i);
	
			List<Road> roads = towerRoads.get(tempTower);
			boolean isDied = tempTower.shoot(roads);
			
			if(isDied)
				killedEnemies++;
		}
		
		if(killedEnemies > 0) {
			refreshEnemies();
		}
		
		return killedEnemies;
	}

	public boolean moveEnemies() {
		
		boolean isFinal = false;
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy tempEnemy = enemies.get(i);
			isFinal = tempEnemy.move();
			
			if(isFinal) {
				return true;
			}
			
			if(tempEnemy.getClass() == Elf.class) {
				isFinal = tempEnemy.move();
				if(isFinal) {
					return true;
				}
			}
		}
		
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
		
		enemies.add(enemy);
		firstRoad.addEnemy(enemy);
		
		enemy.setCurrentRoad(firstRoad);
	}
	
	public void refreshEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getHealth() < 0) {
				enemies.remove(i);
			}
		}	
	}
	
	public void createMap() {
	}
	
	public void initMap() {
	}
}