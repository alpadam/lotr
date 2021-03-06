package game;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 
 * A p�ly�t reprezent�l� oszt�ly, tartalmazza a t�rk�pet, a rajta l�v� ellenfeleket, az �p�tm�nyeket. 
 * Felel�s a p�ly�val kapcsolatos feladatok kezel�s��rt, mint p�ld�ul a felhaszn�l� �ltal k�rt �p�tkez�s, var�zsk�elhelyez�s, vagy pedig az ellenfelek l�ptet�se, toronyok l�v�sei.
 *
 */
public class Map {
	public MapView mapView;
	
	private Block[][] map;	      	//maga a t�rk�p
	
	private List<Tower> towers;		//fel�p�tett tornyok
	private List<Road> roads;		//utak
	private List<Enemy> enemies;	//m�g �l� ellenfelek
	
	private HashMap<Tower, List<Road>> towerRoads;		//a tornyok �ltal l�tott utak, tornyokhoz rendelve
	
	public static boolean RIGHT = false;
	public static boolean DUPLICATE = false;	//�ppen duplik�l� l�ved�ket l�nek-e a tornyok
	public static boolean FOG = false;		//van-e �ppen k�d a p�ly�n
	
	public Road firstRoad;		//a kezd��t, ahova az ellenfelek bel�pnek
	@SuppressWarnings("unused")
	private Road finalRoad;		//a V�gzet Hegye, az ellenfelek �tic�lja
	
	public Map() {
		mapView = new MapView(this);
		
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		
		towerRoads = new HashMap<Tower, List<Road>>();
	}
	

	/**
	 * 
	 * �jrarajzolja az utakat.
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
	 * Torony �p�t�se megadott koordin�t�kra.
	 * Visszat�r�si �rt�ke mutatja, hogy siker�lt-e tornyot �p�teni.
	 *
	 */
	public boolean createTower (int x, int y) {
		
		if (!map[y][x].isRoad() && !map[y][x].isTower()) {	//�tra nem akarunk tornyot �p�teni
			int tempX = map[y][x].getX();
			int tempY = map[y][x].getY();
			map[y][x] = new Tower();		//l�trehozunk egy tornyot az adott mez�n
			Tower tower = (Tower) map[y][x];
			tower.setX(tempX);
			tower.setY(tempY);
			towers.add(tower);		//a tornyot a list�nkhoz vessz�k
			
			setHashMap(x, y);	//be kell �ll�tanunk a torony �ltal l�tott utakat
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * Akad�ly �p�t�se megadott koordin�t�kra.
	 *
	 */
	public boolean createTrap (int x, int y) {
		if (map[y][x].isRoad() && !((Road)map[y][x]).isTrap()) {	//csak �tra �p�thet�nk, oda, ami m�g nem akad�ly
			((Road)map[y][x]).setTrap();
			return true;
		}
		return false;
	}

	
	/**
	 * 
	 * Amennyiben k�d sz�llt a p�ly�ra, a tornyok l�tk�re m�dosul.
	 * A f�ggv�ny feladata, hogy ilyen esetben kisz�molja, hogy a torony mely utakat fogja l�tni.
	 *
	 */
	private List<Road> fogSight(int x, int y) {
		
		List<Road> tempRoads = new ArrayList<Road>();
		
		if (map[y][x].isTower()) {

			Tower tempTower = (Tower) map[y][x];
			int radius = tempTower.getRadius() - 1;		//a torony l�t�t�vols�g�t le kell k�rn�nk, mert lehet, hogy l�t�sn�vel� var�zsk� van benne
			
			//algoritmus a torony �ltal l�tott utak felder�t�s�re
			for (int i = y-radius; i < y+radius+1; i++) {
				for (int j = x-radius; j < x+radius+1; j++) {
					if (i >= 0 && i < map.length) {
						if (j >= 0 && j < map[i].length) {
							if(map[i][j].isRoad()){
								tempRoads.add((Road) map[i][j]);
							}
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
	
	private void setHashMap(int x, int y) {
		if (map[y][x].isTower()) {
			List<Road> tempRoads = new ArrayList<Road>();

			Tower tempTower = (Tower) map[y][x];
			int radius = tempTower.getRadius();		//a torony l�t�t�vols�g�t le kell k�rn�nk, mert lehet, hogy l�t�sn�vel� var�zsk� van benne
			
			//algoritmus a torony �ltal l�tott utak felder�t�s�re
			for (int i = y-radius; i < y+radius+1; i++) {
				for (int j = x-radius; j < x+radius+1; j++) {
					if (i >= 0 && i < map.length) {
						if (j >= 0 && j < map[i].length) {
							if(map[i][j].isRoad()){
								tempRoads.add((Road) map[i][j]);
							}
						}
					}
				}
			}
			
			towerRoads.put(tempTower, tempRoads);
		}
	}
	
	/**
	 * 
	 * A f�ggv�ny feladata, hogy v�gigmenjen a tornyokon, �s mindegyikkel t�zeljen egyet. 
	 * Figyeli, hogy van-e k�d, vagy �rv�nyben van-e a duplik�l� l�ved�k hat�sa.
	 * Emellett megmondja, hogy h�ny ellenfelet �ltek meg a tornyok, �s ezzel vissza is t�r.
	 *
	 */
	public int shootingTowers() {
		int killedEnemies = 0;
		
		for (int i = 0; i < towers.size(); i++) {
			Tower tempTower = towers.get(i);
			
			List<Road> tempRoads;
			if (!FOG) {
				tempRoads = towerRoads.get(tempTower);	//megn�zz�k, hogy melyik utakat l�tja a torony
			} else {
				int indexX = tempTower.getX() / Block.blockSize;
				int indexY = tempTower.getY() / Block.blockSize;
				tempRoads = this.fogSight(indexX, indexY);	//van k�d, ilyekor m�s utakat l�t a torony		
			}
			
			for (int j = 0; j < tempRoads.size(); j++) {			//a controllerben csak akkor h�v�dik meg a l�v�st kirajzol�
				if(tempRoads.get(j).getEnemies().size() > 0) { 		//f�ggv�ny, ha vannak ellens�gek a torony l�t�t�vols�g�n bel�l
					tempTower.iSeeThem = true;
				} 
			}
			
			if (!DUPLICATE) {
				boolean isDied = tempTower.shoot(tempRoads);
				if (isDied)
					killedEnemies++;
			} else {
				Enemy newEnemy = tempTower.duplicateShoot(tempRoads);
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
		
		if (new Random().nextBoolean()) {			// v�letlenszer� az ir�ny
			RIGHT = true;
		} else {
			RIGHT = false;
		}
		
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
	public boolean placeGem(MagicGem magicGem, int x, int y) {
	
		boolean placed = false;
		
		if (map[y][x].isRoad() && magicGem.getType() == Type.MOVEMENT_DECREASER) {
			Road tempRoad = ((Road)map[y][x]);
			if (tempRoad.isTrap()) 
				placed = tempRoad.placeGem(magicGem);
		} else if (map[y][x].isTower() && magicGem.getType() != Type.MOVEMENT_DECREASER) {
			if (magicGem.getType() == Type.RANGE_EXPANDER) {	//ha l�t�t�vols�got n�vel� k�r�l van sz�, akkor friss�ten�nk kell a torony �ltal l�tott utak list�j�t
				placed = ((Tower)map[y][x]).placeGem(magicGem);
				towerRoads.remove(map[y][x]);
				setHashMap(x, y);
			} else {
				placed = ((Tower)map[y][x]).placeGem(magicGem);
			}
		}
		
		return placed;
	}
	
	
	/**
	 * 
	 * A f�ggv�ny feladata, hogy adott koordin�t�n l�v� �p�tm�nyb�l kivegye a benne l�pv� var�zsk�vet, �s azt visszat�r�si �rt�kk�nt adja vissza.
	 *
	 */
	public MagicGem removeGem(int x, int y) {
		MagicGem gem = null;
		
		if (map[y][x].isTower()) {
			Tower tempTower = (Tower)map[y][x];
			gem = tempTower.removeGem();
			
			if (gem != null && gem.getType() == Type.RANGE_EXPANDER) {	//ha l�t�t�vols�got n�vel� k�vet vett�nk ki, akkor a torony �ltal l�tott utak list�j�t is modos�tanunk kell
				towerRoads.remove(tempTower);	
				setHashMap(x, y);
			}
		} else if (map[y][x].isRoad()) {
			Road tempRoad = (Road)map[y][x];
			if (tempRoad.getGemType() != null) {
				gem = tempRoad.removeGem();
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
			if (enemies.get(i).getHealth() <= 0) {
				enemies.remove(i);
				Controller.sumOfEnemies = enemies.size();
			}
		}	
	}
	
	/**
	 * 
	 * Adott fileb�l inicializ�lja a p�ly�t.
	 * @throws IOException 
	 *
	 */
	public void initMap(String path) throws IOException {
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		roads = new ArrayList<Road>();
		
		towerRoads = new HashMap<Tower, List<Road>>();
		
		//El�sz�r Block[][] map felt�lt�se f�jlb�l
		
		Vector<String> lines;
		String s;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			lines = new Vector<String>();
			
			while ((s = br.readLine()) != null) {
				lines.add(s);
			}
		} finally {
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
							} else if (map[i][j+1].isRoad() && !map[i+1][j].isRoad()) { 
								tempRoad.setNext((Road) map[i][j+1]);
							} else if (map[i][j+1].isRoad() && map[i+1][j].isRoad()) {
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