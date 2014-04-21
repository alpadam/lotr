package game;

import java.util.ArrayList;
import java.util.List;


public class Player {
	
	private List<MagicGem> inventory;
	private int magic;

	
	public Player() {
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
		this.magic -= magic;
	}
	
	public void addMagic(int killedEnemies) {
		this.magic += killedEnemies * Controller.killedEnemyReward;
	}
	
	public List<MagicGem> getInventory() {
		return inventory;
	}

}