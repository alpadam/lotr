package game;

/**
 * 
 * A varázsköveket reprezentáló osztály. Alapvetõen a kõ típusát tárolja, amit nlhány kényelmi funkcióval egészít ki.
 *
 */
public class MagicGem {
	private Type type; 		//a varázskõ típusa
	
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
