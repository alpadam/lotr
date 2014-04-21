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
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		
		towerRoads = new HashMap<Tower, List<Road>>();
	}
	
	public void createTower(int blockId){							
		
		List<Integer> tempCoordinate = blockIdToCoordinate(blockId);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		map[first][second] = new Tower();
		Tower tower = (Tower) map[first][second];
		towers.add(tower);
		setHashMap(tempCoordinate);
	}
	
	public void createTrap(int roadId){
		for (int i = 0; i < roads.size(); i++) {
			if (roads.get(i).road_id == roadId) {
				roads.get(i).setTrap();
			}
		}
	}
	
	private List<Integer> blockIdToCoordinate(int blockId) {
		List<Integer> tempCoordinate = new ArrayList<Integer>();
		if(map != null) {
			int widthId = map[0].length;
			if((blockId%widthId) == 0) {
				tempCoordinate.add((blockId/widthId)-1);
				tempCoordinate.add(widthId-1);
			} else {
				tempCoordinate.add((blockId/widthId));
				tempCoordinate.add((blockId%widthId)-1);
			}
		} else {
			tempCoordinate.add(-1);
		}
		
		return tempCoordinate;
	}
	
	private void setHashMap(List<Integer> tempCoordinate) {
		
		List<Road> tempRoads = new ArrayList<Road>();
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		Tower tempTower = (Tower) map[first][second];
		int radius = tempTower.getRadius();
		
		for (int i = first-radius-1; i < first+radius; i++) {
			for (int j = second-radius; j < second+radius+1; j++) {
				if(i >= 0 && i < map.length) {
					if(j >= 0 && j < map[i].length) {
						if(map[i][j].isRoad()){
							tempRoads.add((Road) map[i][j]);
						}
					}
				}
			}
		}
		towerRoads.put(tempTower, tempRoads);
	}
	
	public int shootingTowers() {
		
		System.out.println("Lövések:");	
		
		int killedEnemies = 0;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			
			System.out.println("DUPLICATE: " + DUPLICATE);
			
			if(!DUPLICATE){
				List<Road> roads = towerRoads.get(tempTower);
				boolean isDied = tempTower.shoot(roads);
				if(isDied)
					killedEnemies++;
			}else{
				Enemy newEnemy = tempTower.duplicateShoot(roads);
				if(newEnemy != null){
					enemies.add(newEnemy);
				}
			}
		}

		if(killedEnemies > 0) {
			refreshEnemies();
		}
		
		return killedEnemies;
	}

	public boolean moveEnemies() {
		
		boolean isFinal = false;
		
		System.out.println("Lépések:");
		
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
		 * a tmp változóra csak a szkeletonban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
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
					
					Tower tempTower = towers.get(i);
					if(tempTower.getGem() == null){
						if(magicGem.getType() == Type.RANGE_EXPANDER){
							
							int radius = tempTower.getRadius();
							
							//MEG KELL ÍRNI, MERT FOS!
							/*for (int i = first-radius-1; i < first+radius; i++) {
								for (int j = second-radius; j < second+radius+1; j++) {
									if(i >= 0 && i < map.length) {
										if(j >= 0 && j < map[i].length) {
											if(map[i][j].isRoad()){
												tempRoads.add((Road) map[i][j]);
											}
										}
									}
								}
							}
							towerRoads.put(tempTower, tempRoads);*/
							
							
							
							
							
							
						}
							
						
						towers.get(i).placeGem(magicGem);
						
					}
					
					
					
					
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
	
	public void initMap(String path) throws IOException {
		
		//Block feltöltése fájlból
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		Vector<String> lines = new Vector<String>();
		String s;
		while((s = br.readLine()) != null) {
			lines.add(s);
		}
		br.close();										/// ez még nem jó lezárás...
		
		map = new Block[lines.size()][];
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
					
					Road tempRoad = (Road) map[i][j];
					roads.add(tempRoad);
					
					if (!tempRoad.isFinal()){
						
						if((i == map.length-1) && (j == map[i].length-1)) {
							break;
						}
						
						if ( (i == map.length-1) ){
							if (map[i][j+1].isRoad() ) {
								tempRoad.setNext((Road) map[i][j+1]);
								break;
							} else {
								break;
							}
							
	
						} else if ( (j == map[i].length-1) ) {
							if (map[i+1][j].isRoad()) {
								tempRoad.setNext((Road) map[i+1][j]);
								break;
							} else {
								break;
							}
							
							
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