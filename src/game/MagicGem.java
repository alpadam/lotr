package game;

/**
 * 
 * A var�zsk�veket reprezent�l� oszt�ly. Alapvet�en a k� t�pus�t t�rolja, amit nlh�ny k�nyelmi funkci�val eg�sz�t ki.
 *
 */
public class MagicGem {
	private Type type; 		//a var�zsk� t�pusa
	
	public MagicGem (Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
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
