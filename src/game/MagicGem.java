package game;

public class MagicGem {
	
	private Type type; 
	
	public static int id = 1;		// csak a szkeleton miatt
	public int gem_id;
	
	public MagicGem(Type type) {
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
	
	public String getGemTypeName(){
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
}
