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
		
		switch (testNumber) {
		case 1:
			inventory.add(new MagicGem(Type.RANGE_EXPANDER));
			inventory.add(new MagicGem(Type.DAMAGE_INCREASER));
			inventory.add(new MagicGem(Type.SHOOTING_INCREASER));
			break;
			
		case 2:
			inventory.add(new MagicGem(Type.MOVEMENT_DECREASER));
			break;

		default:
			break;
		}
	}
	
	public void addGem(MagicGem magicGem) {
		System.out.println("Player --> addGem()");
		inventory.add(magicGem);
		System.out.println("Inventory tartalma: ");
		for (MagicGem g : inventory) {
			System.out.println(g.getType() + " ");
		}
	}
		
	public MagicGem getGem(Type type) {
		System.out.println("Player --> getGem(" + type + ")");
		System.out.println("<-- gem");
			
		for (MagicGem g : inventory) {
			if (g.getType() == type)
				return g;
		}
		return null;
	}
	
	public int getMagic() {							
		System.out.println("Player --> getMagic()");
		System.out.println("<-- magic");
		
		return magic;
	}
	
	public void substractMagic(int magic) {		
		this.magic = this.magic-magic;
		
	}
	
	public void addMagic(int killedEnemies) {
		System.out.println("Player --> addMagic(killedEnemies)");
		this.magic += killedEnemies * Controller.killedEnemyReward;
	}

}