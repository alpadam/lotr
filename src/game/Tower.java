package game;

import java.util.List;

/**
 * 
 * A Block-ból leszármazó osztály, a már felépített torony.
 * Tartalmazza a torony mûkdéséhez szükséges fontos változókat és függvényeket.
 *
 */
public class Tower extends Block {
	public static int simpleDamage = 10;	//ez a változó tartja számon azt az értéket, ami egy minden fejlesztés nélküli torony alapsebzése, így az könnyen átírtható
	
	private MagicGem gem;	//a toronyba behelyezett varázskõ
	private int damage; 	//az aktuális sebzés
	private int radius;		//az a távolság, amerre a torony még "ellát"
	
	public static int t_id = 1;		//ez a változó tartja számon a már kiosztott toronyazonosítók számát
	public int tower_id;		//a torony egyéni azonosítója
	
	public Tower() {
		block_id-=1;
		
		tower_id = t_id;
		t_id++;
		
		gem = null;
		damage = simpleDamage;		//így könnyen finomhangolható a játékegyensúly
		radius = 2;		//a torony alapból 2 távolságra "lát el"
		
		blockView = new TowerView(this);
	}
	
	/**
	 * 
	 * Varázskõ behelyezése a toronyba. Figyeli, hogy csak megfelelõ kõ helyezhetõ be, és milyen változásokkal jár ez a torony szempontjából.
	 * Ha már volt kõ a toronyba, akkor sikertelen a behelyezési kísérlet.
	 * A visszatérési réték jelzi, hogy sikerült-e berakni a követ.
	 *
	 */
	public boolean placeGem(MagicGem magicGem){
		if (gem != null && magicGem.getType() == Type.MOVEMENT_DECREASER) {
			System.out.println("Varázskõ elhelyezése sikertelen Torony#" + tower_id + "-n.");
			return false;
		} else {
			gem = magicGem;
			
			switch (magicGem.getType()) { //megvizsgáljuk a kõ típusát
				case RANGE_EXPANDER:
					radius = radius + 1;  //egyel nõ a látott távolság
					System.out.println("RangeExpander varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;
				case DAMAGE_INCREASER:
					damage = simpleDamage + 15; 	//nõ az akutális sebzés
					System.out.println("DamageIncreaser varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;
				case SHOOTING_INCREASER:
					damage = 2 * simpleDamage;		//két lövedéket is lõ
					System.out.println("ShootingIncreaser varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;

				default:
					System.out.println("Nincs ilyen gem típus!");
				break;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * Varázskõ kivétele a toronyból, és visszaadása. Ha nem volt benne kõ, akkor null-lal tér vissza.
	 *
	 */
	public MagicGem removeGem() {
		MagicGem g = null;
		
		if (gem != null) {
			try {
				g = (MagicGem) gem.clone();	//klónozzuk az aktuális varázskövet, felkészülünk az esetleges kivételre
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//mivel kivesszük a követ, ezért a torony viselkedése módosul az eredetire
			if (gem.getType() == Type.DAMAGE_INCREASER) {
				damage = simpleDamage; 
			} else if (gem.getType() == Type.SHOOTING_INCREASER) {
				damage = simpleDamage;
			} else if (gem.getType() == Type.RANGE_EXPANDER) {
				radius--;
			}
		}
		gem = null;
		
		return g;
	}
	
	/**
	 * 
	 * Toronyban lévõ kõ típusának meghatározása.
	 *
	 */
	public Type getGemType(){
		if (gem == null) {
			return null;
		}
		return gem.getType();
	}
	
	/**
	 * 
	 * Lövés függvény, feladata, hogy az általa emgkapott - a torony által "látott" - utakról kiválasszon egy ellenfelet, és arra tüzeljen.
	 *
	 */
	public boolean shoot(List<Road> roads) {
		boolean died = false;	//kell egy visszatérési érték, ami megmondja, hogy a meglõtt ellenfél meghalt-e
		
		for (int i = 0; i < roads.size(); i++) {
			System.out.println(roads.get(i));
			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();		//lekérjük az útról a rajta tartózkodó ellenfeleket
			
			if (enemies.size() > 0) {
				Enemy tempEnemy = enemies.get(0);
				
				died = tempEnemy.damage(damage);		//tudnunk kell, hogy meghalt-e az ellenfél
				
				System.out.println("Torony#" + tower_id + " lõtt Ellenség#" + tempEnemy.enemy_id + "-ra/re!");

				if (died)
					tempRoad.removeEnemy(tempEnemy);		//ha az ellenfél meghalt, akkor meg kell hívnunk a megfelelõ függényt, ami eltünteti
				
				return died;
			}
		}

		return died;
	}
	
	/**
	 * 
	 * A lövés speciális esete, amikor nem sima, hanem duplikáló lövedéket lõ a torony. A visszatérési értéke az újonnan "született" - tehát duplikált - ellenfél.
	 *
	 */
	public Enemy duplicateShoot(List<Road> roads) {
		for (int i = 0; i < roads.size(); i++) {

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();		//lekérjük az útról a rajta tartózkodó ellenfeleket
			
			if (enemies.size() > 0) {
				Enemy tempEnemy = enemies.get(0);
				
				System.out.println("Torony#" + tower_id + " lõtt Ellenség#" + tempEnemy.enemy_id + "-ra/re kettészedõ lövedékkel!");
				Enemy newEnemy = tempEnemy.duplicate();		//duplikálódik az ellenfél
				System.out.println("Új ellenség: " + newEnemy);
				
				return newEnemy;
			}
		}
		
		return null;
		
	}
	
	@Override
	public String toString() {
		return "Tower#" + tower_id + "\t" + "gem: " + gem + " Helye: Block#" + block_id;
	}
	
	@Override
	public boolean isTower() {		
		return true;
	}
	
	public int getTowerID(){
		return tower_id;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public MagicGem getGem(){
		return gem;
	}
}
