package game;

import java.util.ArrayList;
import java.util.List;


public class Player {
	
	private List<MagicGem> inventory;
	private int magic;
	
	/**
	 K�t tesztesethez sz�ks�ges inicializ�l�s
	 case 1 : k� elhelyez�se toronyba
	 		  a j�t�kos t�bb k� k�z�l v�laszthat
	 case 2 : k� elhelyez�se akad�lyba
	 		  csak egy fajta k� van
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