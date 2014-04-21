package game;

public class Elf extends Enemy {
	
	public Elf() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("Az Elf#"+ enemy_id + " ellenség létrejött!");
	}

	@Override
	public Elf duplicate() {
		Elf elf = new Elf();
		int newhealth = health/2;
		this.health = health/2;
		elf.setHealth(newhealth);
		elf.setCurrentRoad(currentRoad);
		currentRoad.addEnemy(elf);
		return elf;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Elf.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}
}

