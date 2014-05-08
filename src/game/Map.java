package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * 
 * A p�ly�t reprezent�l� oszt�ly, tartalmazza a t�rk�pet, a rajta l�v� ellenfeleket, az �p�tm�nyeket. 
 * Felel�s a p�ly�val kapcsolatos feladatok kezel�s��rt, mint p�ld�ul a felhaszn�l� �ltal k�rt �p�tkez�s, var�zsk�elhelyez�s, vagy pedig az ellenfelek l�ptet�se, toronyok l�v�sei.
 *
 */
public class Map {
	
	private Block[][] map;	      	//maga a t�rk�p
	
	private List<Tower> towers;		//fel�p�tett tornyok
	private List<Road> roads;		//utak
	private List<Enemy> enemies;	//m�g �l� ellenfelek
	
	private HashMap<Tower, List<Road>> towerRoads;		//a tornyok �ltal l�tott utak, tornyokhoz rendelve
	
	public static boolean RIGHT = false;
	public static boolean DUPLICATE = false;	//�ppen duplik�l� l�ved�ket l�nek-e a tornyok
	public static boolean FOG = false;		//van-e �ppen k�d a p�ly�n
	
	private Road firstRoad;		//a kezd��t, ahova az ellenfelek bel�pnek
	private Road finalRoad;		//a V�gzet Hegye, az ellenfelek �tic�lja

	
	public Map() {
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
	 * Torony �p�t�se megadott block-ra.
	 * Visszat�r�si �rt�ke mutatja, hogy siker�lt-e tornyot �p�teni.
	 *
	 */
	public boolean createTower (int blockId) {
		List<Integer> tempCoordinate = blockIdToCoordinate(blockId);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		if (!map[first][second].isRoad()) {	//�tra nem akarunk tornyot �p�teni
			int new_id = map[first][second].block_id;
			map[first][second] = new Tower();		//l�trehozunk egy tornyot az adott mez�n
			Tower tower = (Tower) map[first][second];
			tower.block_id = new_id;	//fontos, hogy a blokkok azonos�t�ja konzisztens maradjon
			towers.add(tower);		//a tornyot a list�nkhoz vessz�k
			setHashMap(blockId);	//be kell �ll�tanunk a torony �ltal l�tott utakat
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * Akad�ly �p�t�se megadott �tra.
	 *
	 */
	public void createTrap (int roadId) {
		for (int i = 0; i < roads.size(); i++) {
			if (roads.get(i).road_id == roadId) {
				roads.get(i).setTrap();		//be�ll�tjuk, hogy fel�p�lt az �tra az akad�ly
			}
		}
	}
	
	/**
	 * 
	 * BlockID alapj�n koordin�tap�rt kisz�mol� f�ggv�ny.
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
	 * Amennyiben k�d sz�llt a p�ly�ra, a tornyok l�tk�re m�dosul.
	 * A f�ggv�ny feladata, hogy ilyen esetben kisz�molja, hogy a torony mely utakat fogja l�tni.
	 *
	 */
	private List<Road> fogSight(Tower tower) {
		List<Road> tempRoads = new ArrayList<Road>();
		int radius = tower.getRadius() - 1;
		List<Integer> tempCoordinate = blockIdToCoordinate(tower.block_id);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		//algoritmus a torony �ltal l�tott utak felder�t�s�re
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
	 * F�ggv�ny, mely megmondja, hogy az adott blokkon l�v� torony mely utakat l�tja, �s ezekkel az utakkal friss�ti az utakat a tornyokhoz rendel� hashmapet.
	 *
	 */
	private void setHashMap(int blockId) {
		List<Road> tempRoads = new ArrayList<Road>();
		List<Integer> tempCoordinate = blockIdToCoordinate(blockId);
		Integer first = tempCoordinate.get(0);
		Integer second = tempCoordinate.get(1);
		
		Tower tempTower = (Tower) map[first][second];
		int radius = tempTower.getRadius();		//a torony l�t�t�vols�g�t le kell k�rn�nk, mert lehet, hogy l�t�sn�vel� var�zsk� van benne
		
		//algoritmus a torony �ltal l�tott utak felder�t�s�re
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
	 * A f�ggv�ny feladata, hogy v�gigmenjen a tornyokon, �s mindegyikkel t�zeljen egyet. 
	 * Figyeli, hogy van-e k�d, vagy �rv�nyben van-e a duplik�l� l�ved�k hat�sa.
	 * Emellett megmondja, hogy h�ny ellenfelet �ltek meg a tornyok, �s ezzel vissza is t�r.
	 *
	 */
	public int shootingTowers() {
		System.out.println("L�v�sek:");	
		
		int killedEnemies = 0;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			
			List<Road> roads;
			if (!FOG) {
				roads = towerRoads.get(tempTower);	//megn�zz�k, hogy melyik utakat l�tja a torony
			} else {
				roads = this.fogSight(tempTower);	//van k�d, ilyekor m�s utakat l�t a torony		
			}
			
			if (!DUPLICATE) {
				boolean isDied = tempTower.shoot(roads);
				if (isDied)
					killedEnemies++;
			} else {
				Enemy newEnemy = tempTower.duplicateShoot(roads);
				if(newEnemy != null){
					enemies.add(newEnemy);	//ha t�rt�nt duplik�l�s, akkor friss�ten�nk kell az ellenf�llist�t
				}
			}
		}

		if(killedEnemies > 0) {
			refreshEnemies();		//ha halt meg ellenf�l, akkor fross�ten�nk kell az ellenf�llist�t
		}
		
		return killedEnemies;
	}

	/**
	 * 
	 * A f�ggv�ny feladata, hogy minden ellenf�l mozgat�s�t elv�gezze.
	 * Visszat�r�si �rt�ke akkor igaz, ha az ellenfelek el�rt�k a V�gzet Hegy�t.
	 *
	 */
	public boolean moveEnemies() {
		boolean isFinal = false;
		
		System.out.println("L�p�sek:");
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy tempEnemy = enemies.get(i);
			isFinal = tempEnemy.move();
			
			if(isFinal) {
				return true;
			}
			
			if (tempEnemy.getClass() == Elf.class) {		//az elfek k�l�nlegess�ge a f�rges�g�k, �k kett�t is l�phetnek
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
	 * A f�ggv�ny feladata, hogy adott �p�tm�nybe helyezzen egy megadott var�zsk�vet.
	 * A visszat�r�si r�t�k jelzi, hogy siker�lt-e berakni a k�vet.
	 *
	 */
	public boolean placeGem(MagicGem magicGem, int id, boolean tmp) {
		/*
		 * a tmp v�ltoz�ra csak a prototpusban van sz�ks�g, ezzel jelezz�k, hogy melyik teszteset h�v�dik meg:
		 * a k� elhelyez�se toronyba (=false), vagy a k� elhelyez�se akad�lyba (=true)
		 */ 	
		boolean placed = false;
		
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

					if (magicGem.getType() == Type.RANGE_EXPANDER) {	//ha l�t�t�vols�got n�vel� k�r�l van sz�, akkor friss�ten�nk kell a torony �ltal l�tott utak list�j�t
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
	}
	
	/**
	 * 
	 * A f�ggv�ny feladata, hogy adott �p�tm�nyb�l kivegye a benne l�pv� var�zsk�vet, �s azt visszat�r�si �rt�kk�nt adja vissza.
	 *
	 */
	public MagicGem removeGem(int id) {
		MagicGem gem = null;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			if (tempTower.tower_id == id) {
				gem = tempTower.removeGem();
				
				if (gem != null && gem.getType() == Type.RANGE_EXPANDER) {	//ha l�t�t�vols�got n�vel� k�vet vett�nk ki, akkor a torony �ltal l�tott utak list�j�t is modos�tanunk kell
					towerRoads.remove(tempTower);	
					setHashMap(tempTower.block_id);
				}
				break;
			}
		}
		
		return gem;
	}
	
	/**
	 * 
	 * Adott ellens�g j�t�kba ker�l�s�hez sz�ks�ges tev�kenys�geket elv�gz� f�ggv�ny.
	 *
	 */
	public void initEnemy(Enemy enemy) {
		enemies.add(enemy);
		firstRoad.addEnemy(enemy);
		enemy.setCurrentRoad(firstRoad);
	}
	
	/**
	 * 
	 * A halott ellenfeleket kiveszi a list�b�l, friss�tve azt.
	 *
	 */
	public void refreshEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getHealth() <= 0) {
				enemies.remove(i);
			}
		}	
	}
	
	/**
	 * 
	 * Adott fileb�l inicializ�lja a p�ly�t.
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
		
		//El�sz�r Block[][] map felt�lt�se f�jlb�l
		
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
		
		map = new Block[lines.size()][];
		for (int i = 0; i < lines.size(); i++) {
			s = lines.get(i);
			map[i] = new Block[s.length()];
			for (int j = 0; j < s.length(); j++) {
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
			}
		}	
		
		//Majd a List<Road> roads felt�lt�se a Block[][] map-b�l
		
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