package game;

import java.util.List;

public class Tower extends Block {

	public static int simpleDamage = 10;
	
	private MagicGem gem;
	private int damage;
	
	
	public Tower() {
		gem = null;
		damage = simpleDamage;
	}
	
	public void placeGem(MagicGem magicGem){
		
		if(magicGem.getType() == Type.DAMAGE_INCREASER){
			damage = 20;
		}
		
		gem = magicGem;
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
				
				Enemy tempEnemy = enemies.get(0);				//ezt m�g m�dos�tani kell egy v�letlenszer� algoritmusra
				died = tempEnemy.damage(damage);
				
				if(died){
					tempRoad.removeEnemy(tempEnemy);
				}
			}
		}

		return died;
	}
	
	@Override
	public boolean isTower() {		
		return true;
	}
}