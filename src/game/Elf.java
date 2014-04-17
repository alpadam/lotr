package game;

public class Elf extends Enemy {
	
	public Elf() {
		health = 50;
		trappedValue = 0;
	}

	@Override
	public Elf duplicate() {
		Elf elf = new Elf();
		int newhealth = health/2;
		elf.setHealth(newhealth);
		elf.setCurrentRoad(currentRoad);
		return elf;
	}
	
}

