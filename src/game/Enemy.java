package game;

public abstract class Enemy {
	
	protected int health;
	protected Road currentRoad;

	public abstract boolean move();
	
	public abstract Enemy duplicate();
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setCurrentRoad(Road road){
		currentRoad = road;
	}
	
	public boolean damage(int damage){
		health -= damage;
		if(health <= 0)
			return true;
		else
			return false;
	}
	
}
