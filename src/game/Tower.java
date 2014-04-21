package game;

import java.util.List;

public class Tower extends Block {

	public static int simpleDamage = 10;
	
	private MagicGem gem;
	private int damage;
	private int radius;
	
	public static int id = 1;		// csak a szkeleton miatt
	public int tower_id;
	
	public Tower() {
		tower_id += id;
		block_id-=1;
		id++;
		gem = null;
		damage = simpleDamage;
		radius = 2;
	}
	
	public void placeGem(MagicGem magicGem){
		
		if(gem != null){
			return;
		}else{
			gem = magicGem;
			
			if(magicGem.getType() == Type.DAMAGE_INCREASER){
				damage = 25;
			}else if (magicGem.getType() == Type.SHOOTING_INCREASER) {
				damage = 20;
			}else if (magicGem.getType() == Type.RANGE_EXPANDER) {
				
			}
		}
	}
		
	public MagicGem removeGem(){
		
		MagicGem g = gem;
		gem = null;
			
		return g;
	}
		
	public Type getGemType(){
		
		if (gem == null) {
			return null;
		}
		return gem.getType();
	}
	
	public boolean shoot(List<Road> roads){
		
		boolean died = false;
		
		for(int i = 0; i < roads.size(); i++){

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();
			
			if(enemies.size() > 0){
				
				Enemy tempEnemy = enemies.get(0);						//ezt még módosítani kell egy véletlenszerû algoritmusra
				
				died = tempEnemy.damage(damage);
				
				System.out.println("Torony#" + tower_id + " lõtt Ellenség#" + tempEnemy.enemy_id + "-ra/re!");

				if(died){
					tempRoad.removeEnemy(tempEnemy);
				}
				return died;
			}
			
			
			
		}

		return died;
	}
	
	public Enemy duplicateShoot(List<Road> roads) {
		
		for(int i = 0; i < roads.size(); i++){

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();
			
			if(enemies.size() > 0){
				
				Enemy tempEnemy = enemies.get(0);						//ezt még módosítani kell egy véletlenszerû algoritmusra
				
				System.out.println("Torony#" + tower_id + " lõtt Ellenség#" + tempEnemy.enemy_id + "-ra/re kettészedõ lövedékkel!");
				Enemy newEnemy = tempEnemy.duplicate();
				System.out.println("Új ellenség: " + newEnemy);
				
				return newEnemy;
			}
		}
		
		return null;
		
	}
	
	@Override
	public String toString() {
		return "Tower#" + tower_id + "\t" + "gem: " + gem + " Helye: Block#" + block_id;
	}
	
	@Override
	public boolean isTower() {		
		return true;
	}
	
	public int getTowerID(){
		return tower_id;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public MagicGem getGem(){
		return gem;
	}
}
