package game;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A Block-ból leszármazó osztály, az utak, amiken az ellenfelek közlekednek.
 * Az osztály fel van készítve arra, hogy akadályt építsünk rá.
 * Tartalmazza az utak és az akadályak mûködéséhez szükséges függvényeket, változókat.
 *
 */
public class Road extends Block {

	private List<Enemy> enemies;	//az úton tartózkodó ellenfelek
	private boolean isTrap;		//van-e akadály az úton
	private boolean isFinal;	//ez az ellenfelek által elérhetõ végsõ út-e, azaz a Végzet Hegye
	private boolean isFirst; 	//kezdõpozíció-e az út az ellenfelek számára
	private MagicGem gem;		//ha akadály van telepítve az útra, akkor varázskövet is rakhatunk bele
	private Road nextRoad;		//a következõ útelem, amerre folytatódik az útvonal
	private Road nextRoad2;		//alapvetõen az értéke null, kivéve, ha van elágazás, ilyenkor ez a másik lehetõség, amerre az útvonal tarthat
	
	public static int r_id = 1;		//ez a változó tartja számon a már kiosztott útazonosítók számát
	public int road_id;		//az út egyéni azonosítója
	
	public Road() {
		isTrap = false;
		isFinal = false;
		isFinal = false;
		gem = null;
		nextRoad2 = null;
		
		blockView = new RoadView(this);
		
		enemies = new ArrayList<Enemy>();
		
		road_id = r_id;
		r_id++;
	}
	
	/**
	 * 
	 * Ellenfél az útra kerül (rálép, vagy duplikálódik egy már rajta lévõ).
	 *
	 */
	public void addEnemy(Enemy enemy){
		enemies.add(enemy);
	}
	
	/**
	 * 
	 * Több ellenfél az útra kerül.
	 *
	 */
	public void setEnemies(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	/**
	 * 
	 * Varázskõ behelyezése az akadályba, amennyiben van akadály az úton.
	 * A visszatérési réték jelzi, hogy sikerült-e berakni a követ.
	 *
	 */
	public boolean placeGem(MagicGem magicGem){
		if (gem != null && !isTrap) {
			System.out.println("Varázskõ elhelyezése sikertelen Road#" + road_id + "-n.");
			return false;
		} else {
			System.out.println("Varázskõ Road#" + road_id + " akadállyal ellátott úton elhelyezve.");
			gem = magicGem;
		}
		
		return true;
	}
	
	/**
	 * 
	 * Ellenfél eltávolítása az útról (meghalt vagy továbbhalad).
	 *
	 */
	public void removeEnemy(Enemy enemy){
		enemies.remove(enemy);
	}
	
	public MagicGem removeGem(){
		return gem;
	}
	
	public void setFinal(){
		isFinal = true;
	}
	
	public void setFirst(){
		isFirst = true;
	}
	
	public void setTrap(){
		isTrap = true;
	}
	
	public boolean isRoad(){
		return true;
	}
	
	public boolean isTrap(){
		return isTrap;
	}
	
	public boolean isFirst(){
		return isFirst;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
	
	/**
	 * 
	 * Útvonal folytatásának meghatározása. 
	 * Ha csak egy irányba lehet továbbhaladni, akkor a nextRoadot adja vissza, egyébként a neki megadott boolean alapján váalszt
	 *
	 */
	public Road getNext(boolean right){
		if(nextRoad2 == null || right == true)
			return nextRoad;
		else
			return nextRoad2;
	}
	
	public void setNext(Road road){
		nextRoad = road;
	}
	
	public void setNext2(Road road){
		nextRoad2 = road;
	}
	
	/**
	 * 
	 * Varázskõ típusának meghatározása.
	 *
	 */
	public Type getGemType(){
		if (gem == null)
			return null;
		return gem.getType();
	}
	
	/**
	 * 
	 * Úton lévõ ellenfelek lekérése.
	 *
	 */
	public List<Enemy> getEnemies(){
		return enemies;
	}
	
	@Override
	public String toString() {
		
			return "\t" +"Road#"+ road_id + "\t" + "helye: Block#" + block_id + "\t"
			+ "Akadály-e:"+(isTrap ? "Igen" : "Nem")+ "\t "+ " Ellenségek:";
	}
	
	public int getRoadID(){
		return road_id;
	}
	
}
