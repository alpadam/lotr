package game;

public abstract class Enemy {
	
	protected int health;
	protected Road currentRoad;
	protected int trappedValue;
	
	public static int id = 1;		// megk�l�nb�ztetni az ellens�geket
	public int enemy_id;			// Enemy szinten k�l�nb�ztetj�k meg, nem t�pus szinten

	
	public abstract Enemy duplicate();
	
	public boolean move(){
		
		if (trappedValue == 0) {
			
			currentRoad.removeEnemy(this);
			currentRoad = currentRoad.getNext(Map.RIGHT);
			
			if(currentRoad != null){
				
				currentRoad.addEnemy(this);
				
				if(currentRoad.isTrap()){
					
					trappedValue++;
					
					if(currentRoad.getGemType() != null){
						trappedValue++;
					}
				}
			}
			
			System.out.println("Ellens�g#" + this.enemy_id  + " l�pett Road#" + currentRoad.road_id + "-ra/re " + 
					"T�rt�nt v�gs� �tra l�p�s: " + currentRoad.isFinal());
			
			return currentRoad.isFinal();
			
		}else{
			trappedValue--;
			
			System.out.println("Ellens�g#" + this.enemy_id  + " csapd�ba esett Road#" + currentRoad.road_id + "-n ");
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
