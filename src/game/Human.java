package game;

public class Human extends Enemy {
	
	int trappedValue;
	
	public Human() {
		health = 50;
	}
	
	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
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
		human.setHealth(newhealth);
		human.setCurrentRoad(currentRoad);
		return human;
	}

}
