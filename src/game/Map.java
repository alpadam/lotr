package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Map {
	
	private Block[][] map;
	
	private List<Tower> towers;
	private List<Road> roads;
	private List<Enemy> enemies;
	
	private HashMap<Tower, List<Road>> towerRoads;
	
	public static boolean RIGHT = false;
	public static boolean DUPLICATE = false;
	public static boolean FOG = false;
	
	private Road firstRoad;
	private Road finalRoad;
	
	
	
	public Map(int testNumber) {
		
		//map = new Block[][];
		
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		
		towerRoads = new HashMap<Tower, List<Road>>();
	}

	/**
	Torony�p�t�s
	Nem a Controller run() f�ggv�ny�ben h�vjuk meg, mert ezt a f�ggv�nyt 
	k�zvetlen�l a user fogja h�vni
	 */
	public void createTower(int blockId){							
		Tower tower = new Tower();
		towers.add(tower);
		//ide k�ne az, hogy megkeresi a mapban a blockot, kicser�li a towerre, �s felt�lti a tower �ltal l�tott ter�leteket
		
	}
	
	public void createTrap(int roadId){
		for (int i = 0; i < roads.size(); i++) {
			if (roads.get(i).road_id == roadId) {
				roads.get(i).setTrap();
			}
		}
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
	
	public void placeGem(MagicGem magicGem, int id, boolean tmp) {
		/*
		 * a tmp v�ltoz�ra csak a szkeletonban van sz�ks�g, ezzel jelezz�k, hogy melyik teszteset h�v�dik meg:
		 * a k� elhelyez�se toronyba (=false), vagy a k� elhelyez�se akad�lyba (=true)
		 */ 	
		if (tmp) {
			for (int i = 0; i < roads.size(); i++) {
				if (roads.get(i).road_id == id) {
					roads.get(i).placeGem(magicGem);
				}
			}
				
		} else {
			for (int i = 0; i < towers.size(); i++) {
				if (towers.get(i).tower_id == id) {
					towers.get(i).placeGem(magicGem);
				}
			}
		}
	}
		
	public MagicGem removeGem(int id) {
		MagicGem gem = null;
		
		for (int i = 0; i < towers.size(); i++) {
			if (towers.get(i).tower_id == id) {
				gem = towers.get(i).removeGem();
			}
		}
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
	
	public void initMap(String path) throws IOException {
		
		//Block felt�lt�se f�jlb�l
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		Vector<String> lines = new Vector<String>();
		String s;
		while((s = br.readLine()) != null) {
			lines.add(s);
		}
		br.close();										/// ez m�g nem j� lez�r�s...
		
		Block[][] map = new Block[lines.size()][];
		for (int i = 0; i < lines.size(); i++) {
			s = lines.get(i);
			map[i] = new Block[s.length()];
			for (int j = 0; j < s.length(); j++) {
				int value = Character.getNumericValue(s.charAt(j));
				if (value == 0) {
					map[i][j] = new Block();
					System.out.println("0");
				} else if (value == 1) {
					map[i][j] = new Road();
					System.out.println("1");
				} else if (value == 2) {
					Road firstRoad = new Road();
					firstRoad.setFirst();
					map[i][j] = firstRoad;
					this.firstRoad = firstRoad;
					System.out.println("2");
				} else if (value == 3) {
					Road finalRoad = new Road();
					finalRoad.setFinal();
					map[i][j] = finalRoad;
					this.finalRoad = finalRoad;
					System.out.println("3");
				}
			}
		}		
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if(map[i][j].isRoad()) {
					
					System.out.println("road");
					Road tempRoad = (Road) map[i][j];
					roads.add(tempRoad);
					
					if (!tempRoad.isFinal()){
						
						if ( (i == map.length-1) && map[i][j+1].isRoad()){
	
							tempRoad.setNext((Road) map[i][j+1]);
	
						} else if ( (j == map[i].length-1) && map[i+1][j].isRoad()) {
							
							tempRoad.setNext((Road) map[i+1][j]);
							
						} else {
							if(map[i+1][j].isRoad() && !map[i][j+1].isRoad()) { 
								tempRoad.setNext((Road) map[i+1][j]);
							} else if(map[i][j+1].isRoad() && !map[i+1][j].isRoad()) { 
								tempRoad.setNext((Road) map[i][j+1]);
							} else if(map[i][j+1].isRoad() && map[i+1][j].isRoad()) {
								tempRoad.setNext((Road) map[i][j+1]);
								tempRoad.setNext2((Road) map[i+1][j]);
							}
						}
					}
				}
			}
		}
		
		for (int i = 0; i < roads.size(); i++) {
			System.out.println(roads.get(i).toString());
		}
		
		
	}

	
	public Block[][] getMap() {
		return map;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public HashMap<Tower, List<Road>> getTowerRoads() {
		return towerRoads;
	}
}