package game;

import java.util.ArrayList;
import java.util.List;

public class Road extends Block {

	private List<Enemy> enemies;
	private boolean isTrap;
	private boolean isFinal;
	private MagicGem gem;
	private Road nextRoad;
	private Road nextRoad2;
	
	
	public static int id = 1;		// csak a szkeleton miatt
	public int road_id;
	
	public Road() {
		
		isTrap = false;
		
		enemies = new ArrayList<Enemy>();
		road_id = id;
		id++;
	}
	
	public Road(boolean isTrap) {
		
		this.isTrap = isTrap;
		
		enemies = new ArrayList<Enemy>();
		road_id = id;
		id++;
	}
	
	public void addEnemy(Enemy enemy){
		
		System.out.println("Road" + road_id + " --> addEnemy(enemy)");
		
		enemies.add(enemy);
	}
	
	public void setEnemies(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public void placeGem(MagicGem magicGem){
		System.out.println("Road --> placeGem(gem) ");
		
		gem = magicGem;
	}
	
	public void removeEnemy(Enemy enemy){
		enemies.remove(enemy);
	}
	
	public MagicGem removeGem(){
		return gem;
	}
	
	public void setFinal(){
		isFinal = true;
	}
	public void setTrap(){
		isTrap = true;
	}
	
	public boolean isRoad(){
		return true;
	}
	
	public boolean isTrap(){
		return isTrap;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
	
	public Road getNext(){
		System.out.println("Road" + road_id + " --> getNext()");
		return nextRoad;
	}
	
	public void setNext(Road road){
		nextRoad = road;
	}
	
	public Type getGemType(){
		System.out.println("Road --> getGemType()");
		if (gem == null) {
			System.out.println("<-- null");
			return null;
		}
		System.out.println("<-- type");
		return gem.getType();
	}
	
	public List<Enemy> getEnemies(){
		System.out.println("Road --> getEnemies()");
		return enemies;
	}
	
}
