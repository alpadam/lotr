package game;

public abstract class Enemy {
	
	protected int health;
	protected Road currentRoad;
	protected int trappedValue;
	
	
	public static int id = 1;		// megkülönböztetni az ellenségeket - Zsoca
	public int enemy_id;			// Enemy szinten különböztetjük meg, nem típus szinten

	
	
	public abstract Enemy duplicate();
	
	public boolean move(){
		
		if (trappedValue == 0) {
			currentRoad.removeEnemy(this);
			
			currentRoad = currentRoad.getNext(Map.RIGHT);
			currentRoad.addEnemy(this);
			
			if(currentRoad.isTrap()){
				
				trappedValue++;
				
				if(currentRoad.getGemType() != null){
					trappedValue++;
				}
			}
			
			return currentRoad.isFinal();
			
		}else{
			trappedValue--;
			return false;
		}
	}
	
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
	
	public int getTrappedValue(){
		return trappedValue;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getEnemyID(){
		return enemy_id;
	}
	
	public Road getCurrentRoad(){
		return currentRoad;
	}
	
}
