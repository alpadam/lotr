package game;

import java.util.ArrayList;
import java.util.List;


public class Player {
	
	private List<MagicGem> inventory;
	private int magic;
	
	/**
	 Két tesztesethez szükséges inicializálás
	 case 1 : kõ elhelyezése toronyba
	 		  a játékos több kõ közül választhat
	 case 2 : kõ elhelyezése akadályba
	 		  csak egy fajta kõ van
	 testNumber csak a szkeleton miatt van
	 */
	public Player(int testNumber) {
		this.magic = 100;							
		inventory = new ArrayList<MagicGem>();
		
	}
	


	public void addGem(MagicGem magicGem) {
		inventory.add(magicGem);
	}
		
	public MagicGem getGem(Type type) {
			
		for (MagicGem g : inventory) {
			if (g.getType() == type)
				return g;
		}
		
		return null;
	}
	
	public int getMagic() {							
		return magic;
	}
	
	public void substractMagic(int magic) {		
		this.magic = this.magic-magic;
	}
	
	public void addMagic(int killedEnemies) {
		this.magic += killedEnemies * Controller.killedEnemyReward;
	}
	
	public List<MagicGem> getInventory() {
		return inventory;
	}
	

}