package game;

public class Human extends Enemy {
	
	public Human() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("A Human#"+ enemy_id + " ellenség létrejött!");
	}
	
	@Override
	public boolean damage(int damage) {
		health -= Tower.simpleDamage;
		if(health <= 0)
			return true;
		else
			return false;
	}

	@Override
	public Human duplicate() {
		Human human = new Human();
		int newhealth = health/2;
		this.health = health/2;
		human.setHealth(newhealth);
		human.setCurrentRoad(currentRoad);
		currentRoad.addEnemy(human);
		return human;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Human.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}
