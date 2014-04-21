package game;

/**
 * 
 * @author L�szl� �d�m
 *
 */
public class Dwarf extends Enemy {
	
	public Dwarf() {
		health = 60;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("A Dwarf#"+ enemy_id + " ellens�g l�trej�tt!");
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
		return "\t"+"Ellens�g#"+ enemy_id + "\t Dwarf.class" + "\t" + "�leter�:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}
