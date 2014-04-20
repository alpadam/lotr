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
	
	public MagicGem getGem(){
		return gem;
	}
	
	public boolean shoot(List<Road> roads){
		
		boolean died = false;
		
		for(int i = 0; i < roads.size(); i++){

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();
			
			if(enemies.size() > 0){
				
				Enemy tempEnemy = enemies.get(0);						//ezt m�g m�dos�tani kell egy v�letlenszer� algoritmusra
				
				died = tempEnemy.damage(damage);

				if(died){
					tempRoad.removeEnemy(tempEnemy);
				}
				return died;
			}
		}

		return died;
	}
	
	public Enemy duplicateShoot(List<Road> roads){
		
		for(int i = 0; i < roads.size(); i++){

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();
			
			if(enemies.size() > 0){
				
				Enemy tempEnemy = enemies.get(0);						//ezt m�g m�dos�tani kell egy v�letlenszer� algoritmusra
				try {
					Enemy newEnemy = tempEnemy.getClass().newInstance();
					Road currentRoad = tempEnemy.getCurrentRoad();
					currentRoad.addEnemy(newEnemy);
					newEnemy.setCurrentRoad(currentRoad);
					return newEnemy;
				} catch (InstantiationException e) {
					System.out.println("Duplicate hiba");
				} catch (IllegalAccessException e) {
					System.out.println("Duplicate hiba");
				}
			}
		}
		
		return null;
		
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
}
