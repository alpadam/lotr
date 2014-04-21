package game;

import java.util.ArrayList;
import java.util.List;

public class Road extends Block {

	private List<Enemy> enemies;
	private boolean isTrap;
	private boolean isFinal;
	private boolean isFirst;
	private MagicGem gem;
	private Road nextRoad;
	private Road nextRoad2;
	
	
	public static int id = 1;		// csak a szkeleton miatt
	public int road_id;
	
	public Road() {
		
		isTrap = false;
		isFinal = false;
		isFinal = false;
		gem = null;
		nextRoad2 = null;
		
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
		enemies.add(enemy);
	}
	
	public void setEnemies(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public void placeGem(MagicGem magicGem){
		if (gem != null) {
			System.out.println("Var�zsk� elhelyez�se sikertelen Road#" + road_id + "-n.");
			return;
		} else {
			System.out.println("Var�zsk� Road#" + road_id + " akad�llyal ell�tott �ton elhelyezve.");
			gem = magicGem;
		}
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
	
	public void setFirst(){
		isFirst = true;
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
	
	public boolean isFirst(){
		return isFirst;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
	
	public Road getNext(boolean right){
		if(nextRoad2 == null || right == true)
			return nextRoad;
		else
			return nextRoad2;
	}
	
	public void setNext(Road road){
		nextRoad = road;
	}
	
	public void setNext2(Road road){
		nextRoad2 = road;
	}
	
	public Type getGemType(){
		if (gem == null)
			return null;
		return gem.getType();
	}
	
	public List<Enemy> getEnemies(){
		return enemies;
	}
	
	@Override
	public String toString() {
		try {
			if(nextRoad == null){
				return "road" + road_id + " v�gs� �t";
			}else if(nextRoad2 == null){
				return "road" + road_id + "szomszedai: " + nextRoad.road_id  + ", " + "null";
			}else{
				return "road" + road_id + "szomszedai: " + nextRoad.road_id  + ", " + nextRoad2.road_id;
			}
			
		} catch(NullPointerException e) {
			return null;
		}
			
	}
	
	public int getRoadID(){
		return road_id;
	}
	
}
