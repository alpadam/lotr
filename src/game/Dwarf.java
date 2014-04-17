package game;

/**
 * 
 * @author László Ádám
 *
 */
public class Dwarf extends Enemy {
	
	public Dwarf() {
		health = 50;
		trappedValue = 0;
	}

	@Override
	public Dwarf duplicate() {
		Dwarf dwarf = new Dwarf();
		int newhealth = health/2;
		dwarf.setHealth(newhealth);
		dwarf.setCurrentRoad(currentRoad);
		return dwarf;
	}

}
