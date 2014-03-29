package game;

import java.util.List;

public class Tower extends Block {

	public static int simpleDamage;
	
	private MagicGem gem;
	private int damage;
	
	
	public Tower() {
		// TODO Auto-generated constructor stub
	}
	
	public Tower(boolean withGem) {
		gem = new MagicGem(Type.DAMAGE_INCREASER);
	}
	
	public void placeGem(MagicGem magicGem){
		System.out.println("Tower --> placeGem(gem) ");
			
		gem = magicGem;
	}
		
		
	public MagicGem removeGem(){
		System.out.println("Tower --> removeGem() ");
		
		MagicGem g = gem;
		gem = null;
			
		System.out.println("<-- gem");
		return g;
	}
		
	
	public Type getGemType(){
		System.out.println("Tower --> getGemType() ");
		
		if (gem == null) {
			System.out.println("<-- null");
			return null;
		}
		
		System.out.println("<-- type");
		return gem.getType();
		
	}
	
	public boolean shoot(List<Road> roads){
		System.out.println("Tower --> shoot(road)");
		
		Road r = roads.get(0);
		List<Enemy> enemies = r.getEnemies();
		System.out.println("<-- dwarf");
		boolean dead = enemies.get(0).damage(damage);
		System.out.println("Meghalt-e?: " + dead);
		
		
		r.removeEnemy(enemies.get(0));
		
		return dead;
	}
	
	@Override
	public boolean isTower() {
		System.out.println("Tower --> isTower()");
		System.out.println("<-- true");
			
		return true;
	}
}
