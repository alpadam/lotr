package game;

/**
 * 
 * @author László Ádám
 *
 */
public class Dwarf extends Enemy {
	
	public Dwarf() {
		health = 60;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("A Dwarf#"+ enemy_id + " ellenség létrejött!");
	}

	@Override
	public Dwarf duplicate() {
		Dwarf dwarf = new Dwarf();
		int newhealth = health/2;
		this.health = health/2;
		dwarf.setHealth(newhealth);
		dwarf.setCurrentRoad(currentRoad);
		currentRoad.addEnemy(dwarf);
		return dwarf;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Dwarf.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}