package game;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A Block-b�l lesz�rmaz� oszt�ly, az utak, amiken az ellenfelek k�zlekednek.
 * Az oszt�ly fel van k�sz�tve arra, hogy akad�lyt �p�ts�nk r�.
 * Tartalmazza az utak �s az akad�lyak m�k�d�s�hez sz�ks�ges f�ggv�nyeket, v�ltoz�kat.
 *
 */
public class Road extends Block {
	private List<Enemy> enemies;	//az �ton tart�zkod� ellenfelek
	private boolean isTrap;		//van-e akad�ly az �ton
	private boolean isFinal;	//ez az ellenfelek �ltal el�rhet� v�gs� �t-e, azaz a V�gzet Hegye
	private boolean isFirst; 	//kezd�poz�ci�-e az �t az ellenfelek sz�m�ra
	private MagicGem gem;		//ha akad�ly van telep�tve az �tra, akkor var�zsk�vet is rakhatunk bele
	private Road nextRoad;		//a k�vetkez� �telem, amerre folytat�dik az �tvonal
	private Road nextRoad2;		//alapvet�en az �rt�ke null, kiv�ve, ha van el�gaz�s, ilyenkor ez a m�sik lehet�s�g, amerre az �tvonal tarthat
	
	public Road() {
		isTrap = false;
		isFinal = false;
		isFinal = false;
		gem = null;
		nextRoad2 = null;
		
		blockView = new RoadView(this);
		
		enemies = new ArrayList<Enemy>();
		
	}
	
	/**
	 * 
	 * Ellenf�l az �tra ker�l (r�l�p, vagy duplik�l�dik egy m�r rajta l�v�).
	 *
	 */
	public void addEnemy(Enemy enemy){
		enemies.add(enemy);
	}
	
	/**
	 * 
	 * T�bb ellenf�l az �tra ker�l.
	 *
	 */
	public void setEnemies(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	/**
	 * 
	 * Var�zsk� behelyez�se az akad�lyba, amennyiben van akad�ly az �ton.
	 * A visszat�r�si r�t�k jelzi, hogy siker�lt-e berakni a k�vet.
	 *
	 */
	public boolean placeGem(MagicGem magicGem){
		if (gem != null && !isTrap) {
			return false;
		} else {
			gem = magicGem;
		}
		
		return true;
	}
	
	/**
	 * 
	 * Ellenf�l elt�vol�t�sa az �tr�l (meghalt vagy tov�bbhalad).
	 *
	 */
	public void removeEnemy(Enemy enemy){
		enemies.remove(enemy);
	}
	
	public MagicGem removeGem(){
		MagicGem g = null;
		
		if (gem != null) {
			try {
				g = (MagicGem) gem.clone();	//kl�nozzuk az aktu�lis var�zsk�vet, felk�sz�l�nk az esetleges kiv�telre
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gem = null;
		
		return g;
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
	 * �tvonal folytat�s�nak meghat�roz�sa. 
	 * Ha csak egy ir�nyba lehet tov�bbhaladni, akkor a nextRoadot adja vissza, egy�bk�nt a neki megadott boolean alapj�n v�alszt
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
	 * Var�zsk� t�pus�nak meghat�roz�sa.
	 *
	 */
	public Type getGemType(){
		if (gem == null)
			return null;
		return gem.getType();
	}
	
	/**
	 * 
	 * �ton l�v� ellenfelek lek�r�se.
	 *
	 */
	public List<Enemy> getEnemies(){
		return enemies;
	}
	
}
