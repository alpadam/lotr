package game;

/**
 * 
 * Az ellenfeleket reprezentáló absztakrt osztály, a többi faj ennek a leszármazottja.
 *
 */
public abstract class Enemy {
	protected int health; 	//ellenfél aktuális élete
	protected Road currentRoad; 	//az az útelem, melyen az ellenfél tartózkodik
	protected int trappedValue;		//megmutatja, hogy az ellenfél mennyi idõt tölt még akadályon, ha egyáltalán akadályon van
	
	public static int id = 1;		//ez a változó tartja számon a már kiosztott ellenfélazonosítók számát
	public int enemy_id;			//az ellenfél egyéni azonosítója
	
	public EnemyView enemyView;
	
	public abstract Enemy duplicate();
	
	/**
	 * 
	 * Az ellenfelek mozgását megvalósító függvény.
	 * Visszaétéri értéke mutatja, hogy az ellenfél eljutott-e a Végzet Hegyére.
	 *
	 */
	public boolean move() {
		if (!currentRoad.isFinal()) {
			if (trappedValue == 0) {	//amennyiben nincs akadályon az ellenfél, vagy már kijutott belõle
				
				currentRoad.removeEnemy(this);		//ellép az aktuális útról
				currentRoad = currentRoad.getNext(Map.RIGHT);		//lekérjük a következõ utat
				
				if (currentRoad != null) {
					currentRoad.addEnemy(this);		//a kövektezõ útra ér
					
					if (currentRoad.isTrap()) {		//amennyiben akadályra lépett, akkor a megfelelõ változó változik
						trappedValue++;
						
						if (currentRoad.getGemType() != null) {	//ha az akadályon erõsítõ varázskõ van, akkor tovább marad az akadályon, így tovább nõ az érték
							trappedValue++;
						}
					}
				}
			} else {
				trappedValue--;		//ha akadálon volt, akkor próbál kiszabadulni belõle, a megfelelõ érték egyel csökken
				
				System.out.println("Ellenség#" + this.enemy_id  + " csapdába esett Road#" + currentRoad.road_id + "-n ");
				return false;		//mivel ilyenkor nem mozgott tovább, biztosan nem érte el a Végzet Hegyét
			}
		}
		
		return currentRoad.isFinal();
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setCurrentRoad(Road road){
		currentRoad = road;
	}
	
	/**
	 * 
	 * Az ellenfelet sebzõ függvény, ezt az ellenférle lövõ tornyok fogják hívni. 
	 * Feladata még az is, hogy jelzi, az ellenfél meghalt-e.
	 *
	 */
	public boolean damage(int damage){
		health -= damage;
		if(health <= 0)
			return true;
		else
			return false;
	}
	
	public int getTrappedValue(){
		return trappedValue;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getEnemyID(){
		return enemy_id;
	}
	
	public Road getCurrentRoad(){
		return currentRoad;
	}
	
}
