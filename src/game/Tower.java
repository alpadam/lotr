package game;

import java.util.List;

import javax.swing.text.html.BlockView;

public class Tower extends Block {

	public static int simpleDamage = 10;
	
	private MagicGem gem;
	private int damage;
	
	public static int id = 1;		// csak a szkeleton miatt
	public int tower_id;
	
	public Tower() {
		tower_id += id;
		block_id-=1;
		id++;
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
	
	public MagicGem getGem(){
		return gem;
	}
	
	public boolean shoot(List<Road> roads){
		
		boolean died = false;
		
		for(int i = 0; i < roads.size(); i++){

			Road tempRoad = roads.get(i);
			List<Enemy> enemies = tempRoad.getEnemies();
			
			System.out.println("Enemy size elõtt");
			
			if(enemies.size() > 0){
				
				System.out.println("Enemy size után");
				
				Enemy tempEnemy = enemies.get(0);				//ezt még módosítani kell egy véletlenszerû algoritmusra
				
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
	
	public int getTowerID(){
		return tower_id;
	}
}
