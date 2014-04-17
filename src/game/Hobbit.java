package game;

public class Hobbit extends Enemy {
	
	public Hobbit() {
		health = 50;
	}
	
	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
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
