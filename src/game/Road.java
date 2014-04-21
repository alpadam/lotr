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
	
	
	public static int r_id = 1;		// csak a szkeleton miatt
	public int road_id;
	
	public Road() {
		isTrap = false;
		isFinal = false;
		isFinal = false;
		gem = null;
		nextRoad2 = null;
		
		enemies = new ArrayList<Enemy>();
		
		road_id = r_id;
		r_id++;
	}
	
	public void addEnemy(Enemy enemy){
		enemies.add(enemy);
	}
	
	public void setEnemies(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public void placeGem(MagicGem magicGem){
		if (gem != null) {
			System.out.println("Varázskõ elhelyezése sikertelen Road#" + road_id + "-n.");
			return;
		} else {
			System.out.println("Varázskõ Road#" + road_id + " akadállyal ellátott úton elhelyezve.");
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
		
			return "\t" +"Road#"+ road_id + "\t" + "helye: Block#" + block_id + "\t"
			+ "Akadály-e:"+(isTrap ? "Igen" : "Nem")+ "\t "+ " Ellenségek:";
	}
	
	public int getRoadID(){
		return road_id;
	}
	
}
