package game;

public class Elf extends Enemy {
	
	int trappedValue;
	
	public Elf() {
		health = 50;
	}
	
	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
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
