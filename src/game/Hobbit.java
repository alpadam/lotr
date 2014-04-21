package game;

public class Hobbit extends Enemy {
	
	public Hobbit() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("A Hobbit#"+ enemy_id + " ellenség létrejött!");
	}
	
	@Override
	public boolean move() {
		
		currentRoad.removeEnemy(this);
		currentRoad = currentRoad.getNext(Map.RIGHT);
		currentRoad.addEnemy(this);
		
		System.out.println("Ellenség#" + enemy_id  + " lépett Road#" + currentRoad.road_id + "-ra/re " + 
				"Történt végsõ útra lépés: " + currentRoad.isFinal());
		
		return currentRoad.isFinal();
	}
	
	@Override
	public Hobbit duplicate() {
		Hobbit hobbit = new Hobbit();
		int newhealth = health/2;
		this.health = health/2;
		hobbit.setHealth(newhealth);
		hobbit.setCurrentRoad(currentRoad);
		currentRoad.addEnemy(hobbit);
		return hobbit;
	}
	
	@Override
	public String toString() {
		return "\t"+"Enemy#"+ enemy_id + "\t Hobbit.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}
	
}
