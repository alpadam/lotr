package game;

/**
 * 
 * A var�zsk�veket reprezent�l� oszt�ly. Alapvet�en a k� t�pus�t t�rolja, amit nlh�ny k�nyelmi funkci�val eg�sz�t ki.
 *
 */
public class MagicGem {
	
	private Type type; 		//a var�zsk� t�pusa
	
	public static int id = 1;		//ez a v�ltoz� tartja sz�mon a m�r kiosztott var�zsk�azonos�t�k sz�m�t
	public int gem_id;		//a var�zsk� egy�ni azonos�t�ja
	
	public MagicGem (Type type) {
		this.type = type;
		gem_id = id;
		id++;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getGemID(){
		return gem_id;
	}
	
	@Override
	public String toString() {
		switch (type){
		case DAMAGE_INCREASER:
			return "Gem.DamageIncreaser";
		case MOVEMENT_DECREASER:
			return "Gem.movementDecreaser";
		case RANGE_EXPANDER:
			return "Gem.rangeExpander";
		case SHOOTING_INCREASER:
			return "Gem.shootingIncreaser";
		}
		
		return null;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this;
	}
	
}
