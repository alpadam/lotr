package game;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * 
 * A pályát reprezentáló osztály, tartalmazza a térképet, a rajta lévõ ellenfeleket, az építményeket. 
 * Felelõs a pályával kapcsolatos feladatok kezeléséért, mint például a felhasználó által kért építkezés, varázsköelhelyezés, vagy pedig az ellenfelek léptetése, toronyok lövései.
 *
 */
public class Map {
	
	
	public MapView mapView;
	
	
	private Block[][] map;	      	//maga a térkép
	
	private List<Tower> towers;		//felépített tornyok
	private List<Road> roads;		//utak
	private List<Enemy> enemies;	//még élõ ellenfelek
	
	private HashMap<Tower, List<Road>> towerRoads;		//a tornyok által látott utak, tornyokhoz rendelve
	
	public static boolean RIGHT = false;
	public static boolean DUPLICATE = false;	//éppen duplikáló lövedéket lõnek-e a tornyok
	public static boolean FOG = false;		//van-e éppen köd a pályán
	
	private Road firstRoad;		//a kezdõút, ahova az ellenfelek belépnek
	private Road finalRoad;		//a Végzet Hegye, az ellenfelek úticélja

	
	public Map() {
		
		mapView = new MapView(this);
		
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		Tower.t_id = 1;
		Road.r_id = 1;
		Block.b_id = 1;
		
		towerRoads = new HashMap<Tower, List<Road>>();
	}
	
	/**
	 * 
	 * Újrarajzolja az utakat.
	 *
	 */
	public void refreshRoads(Graphics g) {
		for (int i = 0; i < roads.size(); i++) {
			Road tempRoad = roads.get(i);
			tempRoad.blockView.draw(g);
		}
	}
	
	
	/**
	 * 
	 * Torony építése megadott block-ra.
	 * Visszatérési értéke mutatja, hogy sikerült-e tornyot építeni.
	 *
	 */
	public boolean createTower (int blockId) {
		List<Integer> tempCoordinate = blockIdToCoordinate(blockId);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		if (!map[first][second].isRoad()) {	//útra nem akarunk tornyot építeni
			int new_id = map[first][second].block_id;
			map[first][second] = new Tower();		//létrehozunk egy tornyot az adott mezõn
			Tower tower = (Tower) map[first][second];
			tower.block_id = new_id;	//fontos, hogy a blokkok azonosítója konzisztens maradjon
			towers.add(tower);		//a tornyot a listánkhoz vesszük
			setHashMap(blockId);	//be kell állítanunk a torony által látott utakat
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * Torony építése megadott koordinátákra.
	 * Visszatérési értéke mutatja, hogy sikerült-e tornyot építeni.
	 *
	 */
	public boolean createTower (int x, int y) {
		
		if (!map[y][x].isRoad()) {	//útra nem akarunk tornyot építeni
			
			int tempX = map[y][x].getX();
			int tempY = map[y][x].getY();
			map[y][x] = new Tower();		//létrehozunk egy tornyot az adott mezõn
			Tower tower = (Tower) map[y][x];
			tower.setX(tempX);
			tower.setY(tempY);
			towers.add(tower);		//a tornyot a listánkhoz vesszük
			setHashMap(y,x);	//be kell állítanunk a torony által látott utakat
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * Akadály építése megadott koordinátákra.
	 *
	 */
	public boolean createTrap (int x, int y) {
		if (map[y][x].isRoad() && !((Road)map[y][x]).isTrap()) {	//csak útra építhetünk, oda, ami még nem akadály
			((Road)map[y][x]).setTrap();
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * BlockID alapján koordinátapárt kiszámoló függvény.
	 *
	 */
	private List<Integer> blockIdToCoordinate(int blockId) {
		List<Integer> tempCoordinate = new ArrayList<Integer>();
		if (map != null) {
			int widthId = map[0].length;
			if ((blockId % widthId) == 0) {
				tempCoordinate.add((blockId / widthId) - 1);
				tempCoordinate.add(widthId - 1);
			} else {
				tempCoordinate.add((blockId / widthId));
				tempCoordinate.add((blockId % widthId) - 1);
			}
		} else {
			tempCoordinate.add(-1);
		}
		
		return tempCoordinate;
	}
	
	/**
	 * 
	 * Amennyiben köd szállt a pályára, a tornyok látköre módosul.
	 * A függvény feladata, hogy ilyen esetben kiszámolja, hogy a torony mely utakat fogja látni.
	 *
	 */
	private List<Road> fogSight(Tower tower) {
		List<Road> tempRoads = new ArrayList<Road>();
		int radius = tower.getRadius() - 1;
		List<Integer> tempCoordinate = blockIdToCoordinate(tower.block_id);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		//algoritmus a torony által látott utak felderítésére
		for (int i = first-radius; i < first+radius+1; i++) {
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
		
		return tempRoads;
	}
	
	/**
	 * 
	 * Függvény, mely megmondja, hogy az adott blokkon lévõ torony mely utakat látja, és ezekkel az utakkal frissíti az utakat a tornyokhoz rendelõ hashmapet.
	 *
	 */
	private void setHashMap(int blockId) {
		List<Road> tempRoads = new ArrayList<Road>();
		List<Integer> tempCoordinate = blockIdToCoordinate(blockId);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		Tower tempTower = (Tower) map[first][second];
		int radius = tempTower.getRadius();		//a torony látótávolságát le kell kérnünk, mert lehet, hogy látásnövelõ varázskõ van benne
		
		//algoritmus a torony által látott utak felderítésére
		for (int i = first-radius; i < first+radius+1; i++) {
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
	
	private void setHashMap(int first, int second) {
		
		List<Road> tempRoads = new ArrayList<Road>();
		
		Tower tempTower = (Tower) map[first][second];
		int radius = tempTower.getRadius();		//a torony látótávolságát le kell kérnünk, mert lehet, hogy látásnövelõ varázskõ van benne
		
		//algoritmus a torony által látott utak felderítésére
		for (int i = first-radius; i < first+radius+1; i++) {
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
	
	/**
	 * 
	 * A függvény feladata, hogy végigmenjen a tornyokon, és mindegyikkel tüzeljen egyet. 
	 * Figyeli, hogy van-e köd, vagy érvényben van-e a duplikáló lövedék hatása.
	 * Emellett megmondja, hogy hány ellenfelet öltek meg a tornyok, és ezzel vissza is tér.
	 *
	 */
	public int shootingTowers() {
		System.out.println("Lövések:");	
		
		int killedEnemies = 0;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			
			List<Road> roads;
			if (!FOG) {
				roads = towerRoads.get(tempTower);	//megnézzük, hogy melyik utakat látja a torony
			} else {
				roads = this.fogSight(tempTower);	//van köd, ilyekor más utakat lát a torony		
			}
			
			if (!DUPLICATE) {
				boolean isDied = tempTower.shoot(roads);
				if (isDied)
					killedEnemies++;
			} else {
				Enemy newEnemy = tempTower.duplicateShoot(roads);
				if(newEnemy != null){
					enemies.add(newEnemy);	//ha történt duplikálás, akkor frissítenünk kell az ellenféllistát
				}
			}
		}

		if(killedEnemies > 0) {
			refreshEnemies();		//ha halt meg ellenfél, akkor frossítenünk kell az ellenféllistát
		}
		
		return killedEnemies;
	}

	/**
	 * 
	 * A függvény feladata, hogy minden ellenfél mozgatását elvégezze.
	 * Visszatérési értéke akkor igaz, ha az ellenfelek elérték a Végzet Hegyét.
	 *
	 */
	public boolean moveEnemies(Graphics g) {
		boolean isFinal = false;
		
		System.out.println("Lépések:");
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy tempEnemy = enemies.get(i);
			isFinal = tempEnemy.move();
			
			if(isFinal) {
				return true;
			}
			
			if (tempEnemy.getClass() == Elf.class) {		//az elfek különlegessége a fürgeségük, õk kettõt is léphetnek
				isFinal = tempEnemy.move();
				if (isFinal) {
					return true;
				}
			}
		}
		
		return isFinal;
	}
	
	/**
	 * 
	 * A függvény feladata, hogy adott építménybe helyezzen egy megadott varázskövet.
	 * A visszatérési réték jelzi, hogy sikerült-e berakni a követ.
	 *
	 */
	/*public boolean placeGem(MagicGem magicGem, int id, boolean tmp) {
		/*
		 * a tmp változóra csak a prototpusban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
		 */ 	
		/*boolean placed = false;
		
		if (tmp) {
			for (int i = 0; i < roads.size(); i++) {
				if (roads.get(i).road_id == id) {
					placed = roads.get(i).placeGem(magicGem);
				}
			}
				
		} else {
			for (int i = 0; i < towers.size(); i++) {
				if (towers.get(i).tower_id == id) {
					Tower tempTower = towers.get(i);

					if (magicGem.getType() == Type.RANGE_EXPANDER) {	//ha látótávolságot növelõ kõrõl van szó, akkor frissítenünk kell a torony által látott utak listáját
						placed = tempTower.placeGem(magicGem);
						towerRoads.remove(tempTower);
						setHashMap(tempTower.block_id);
					} else {
						placed = tempTower.placeGem(magicGem);
					}
					
					break;
				}
			}
		}
		
		return placed;
	}*/
	
	/**
	 * 
	 * A függvény feladata, hogy adott építménybe helyezzen egy megadott varázskövet.
	 * A visszatérési réték jelzi, hogy sikerült-e berakni a követ.
	 *
	 */
	public boolean placeGem(MagicGem magicGem, int x, int y) {
		/*
		 * a tmp változóra csak a prototpusban van szükség, ezzel jelezzük, hogy melyik teszteset hívódik meg:
		 * a kõ elhelyezése toronyba (=false), vagy a kõ elhelyezése akadályba (=true)
		 */ 	
		boolean placed = false;
		
		if (map[x][y].isRoad()) {
			placed = ((Road)map[x][y]).placeGem(magicGem);
		} else if (map[x][y].isTower()) {
			if (magicGem.getType() == Type.RANGE_EXPANDER) {	//ha látótávolságot növelõ kõrõl van szó, akkor frissítenünk kell a torony által látott utak listáját
				placed = ((Tower)map[x][y]).placeGem(magicGem);
				towerRoads.remove(map[x][y]);
				setHashMap(map[x][y].block_id);
			} else {
				placed = ((Tower)map[x][y]).placeGem(magicGem);
			}
		}
		
		return placed;
	}
	
	/**
	 * 
	 * A függvény feladata, hogy adott építménybõl kivegye a benne lépvõ varázskövet, és azt visszatérési értékként adja vissza.
	 *
	 */
	/*public MagicGem removeGem(int id) {
		MagicGem gem = null;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			if (tempTower.tower_id == id) {
				gem = tempTower.removeGem();
				
				if (gem != null && gem.getType() == Type.RANGE_EXPANDER) {	//ha látótávolságot növelõ követ vettünk ki, akkor a torony által látott utak listáját is modosítanunk kell
					towerRoads.remove(tempTower);	
					setHashMap(tempTower.block_id);
				}
				break;
			}
		}
		
		return gem;
	}*/
	
	/**
	 * 
	 * A függvény feladata, hogy adott koordinátán lévõ építménybõl kivegye a benne lépvõ varázskövet, és azt visszatérési értékként adja vissza.
	 *
	 */
	public MagicGem removeGem(int x, int y) {
		MagicGem gem = null;
		
		if (map[x][y].isTower()) {
			Tower tempTower = (Tower)map[x][y];
			gem = tempTower.removeGem();
			
			if (gem != null && gem.getType() == Type.RANGE_EXPANDER) {	//ha látótávolságot növelõ követ vettünk ki, akkor a torony által látott utak listáját is modosítanunk kell
				towerRoads.remove(tempTower);	
				setHashMap(tempTower.block_id);
			}
		}
		
		return gem;
	}
	
	/**
	 * 
	 * Adott ellenség játékba kerüléséhez szükséges tevékenységeket elvégzõ függvény.
	 *
	 */
	public void initEnemy(Enemy enemy) {
		enemies.add(enemy);
		firstRoad.addEnemy(enemy);
		enemy.setCurrentRoad(firstRoad);
	}
	
	/**
	 * 
	 * A halott ellenfeleket kiveszi a listából, frissítve azt.
	 *
	 */
	public void refreshEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getHealth() <= 0) {
				enemies.remove(i);
			}
		}	
	}
	
	/**
	 * 
	 * Adott fileból inicializálja a pályát.
	 *
	 */
	public void initMap(String path) throws IOException {
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		Tower.t_id = 1;
		Road.r_id = 1;
		Block.b_id = 1;
		
		towerRoads = new HashMap<Tower, List<Road>>();
		
		//Elõször Block[][] map feltöltése fájlból
		
		Vector<String> lines;
		String s;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(path));
			lines = new Vector<String>();
			
			while((s = br.readLine()) != null) {
				lines.add(s);
			}
		}finally{
			if(br != null)
				br.close();
		}
		
		int tempX = 0;
		int tempY = 0;
		
		map = new Block[lines.size()][];
		for (int i = 0; i < lines.size(); i++,tempY += Block.blockSize) {
			tempX = 0;
			s = lines.get(i);
			map[i] = new Block[s.length()];
			for (int j = 0; j < s.length(); j++ , tempX += Block.blockSize) {
				int value = Character.getNumericValue(s.charAt(j));
				
				if (value == 0) {
					map[i][j] = new Block();
				} else if (value == 1) {
					map[i][j] = new Road();
				} else if (value == 2) {
					Road firstRoad = new Road();
					firstRoad.setFirst();
					map[i][j] = firstRoad;
					this.firstRoad = firstRoad;
				} else if (value == 3) {
					Road finalRoad = new Road();
					finalRoad.setFinal();
					map[i][j] = finalRoad;
					this.finalRoad = finalRoad;
				}
				
				map[i][j].setX(tempX);
				map[i][j].setY(tempY);
			}
		}	
		
		//Majd a List<Road> roads feltöltése a Block[][] map-bõl
		
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