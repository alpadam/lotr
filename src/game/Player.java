package game;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Az osztály a játékos karakterét, Szarumánt reprezentálja, a megfelelõ változóival, eszköztárával és függvényeivel.
 *
 */
public class Player {
	
	private List<MagicGem> inventory;	//a játékos eszköztára, azaz a tulajdonában lévõ varázskövek
	private int magic;		//a játékos aktuális varázsereje
	
	public Player() {
		this.magic = 100;							
		inventory = new ArrayList<MagicGem>();
		
	}
	
	/**
	 * 
	 * Egy követ a játékos eszköztárába rak.
	 *
	 */
	public void addGem(MagicGem magicGem) {
		inventory.add(magicGem);
	}
	
	/**
	 * 
	 * Adott típusú követ kivesz a játékos eszköztárából, és visszaadja.
	 *
	 */
	public MagicGem getGem(Type type) {
		for (MagicGem g : inventory) {
			if (g.getType() == type){
				inventory.remove(g);
				return g;
			}
				
		}
		return null;
	}
	
	/**
	 * 
	 * Vásárlások után adott értékkel csökkenti a játékos varázserejét. 
	 * A vásárlófüggvények feladata annak vizsgálata, hogy van-e elég varázsereje a játékosnak, emiatt nem vizsgálja, hogy lemegy-e nulla alá.
	 *
	 */
	public void substractMagic(int magic) {		
		this.magic -= magic;
	}
	
	/**
	 * 
	 * A játékos által éppen megölt ellenfelekkel arányosan növeli a játékos varázserejét.
	 *
	 */
	public void addMagic(int killedEnemies) {
		this.magic += killedEnemies * Controller.killedEnemyReward;
	}
	
	public List<MagicGem> getInventory() {
		return inventory;
	}
	
	public int getMagic() {							
		return magic;
	}
	
	public int getNumberOfDamageIncreasers(){
		int number=0;
		for (int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getType().equals(Type.DAMAGE_INCREASER))
				number++;
			
		}
		
		return number;
	}
}