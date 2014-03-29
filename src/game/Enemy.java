package game;

public abstract class Enemy {
	
	protected int health;
	protected Road currentRoad;

	public abstract boolean move();
	
	public void setCurrentRoad(Road road){
		
		System.out.println("Enemy --> setCurrentRoad()" + "(road_id: " + road.road_id + ")");
		
		currentRoad = road;
	}
	
	/**
	 Csak a skeleton bemutat�sa miatt lett l�trehozva.
	 */
	public void setRoad(Road road){
		
		currentRoad = road;
		
	}
	
	public boolean damage(int damage){
		System.out.println("Enemy --> damage(damage)");
		return true;
	}
	
}
