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
		
		return currentRoad.isFinal();
	}

	@Override
	public Hobbit duplicate() {
		Hobbit hobbit = new Hobbit();
		int newhealth = health/2;
		hobbit.setHealth(newhealth);
		hobbit.setCurrentRoad(currentRoad);
		return hobbit;
	}
	
}
