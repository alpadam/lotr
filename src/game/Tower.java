package game;

import java.util.List;

public class Tower extends Block {

	public static int simpleDamage = 10;
	
	private MagicGem gem;
	private int damage;
	private int radius;
	
	public static int t_id = 1;		// csak a szkeleton miatt
	public int tower_id;
	
	public Tower() {
		block_id-=1;
		
		tower_id = t_id;
		t_id++;
		
		gem = null;
		damage = simpleDamage;
		radius = 2;
	}
	
	public void placeGem(MagicGem magicGem){
		
		if (gem != null) {
			System.out.println("Varázskõ elhelyezése sikertelen Torony#" + tower_id + "-n.");
			return;
		} else {
			gem = magicGem;
			
			switch (magicGem.getType()) {
				case RANGE_EXPANDER:
					radius = radius + 1;
					System.out.println("RangeExpander varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;
				case DAMAGE_INCREASER:
					damage = simpleDamage + 15;
					System.out.println("DamageIncreaser varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;
				case SHOOTING_INCREASER:
					damage = 2 * simpleDamage;
					System.out.println("ShootingIncreaser varázskõ Torony#" + tower_id + "-ban/ben elhelyezve.");
				break;

				default:
					System.out.println("Nincs ilyen gem típus!");
				break;
			}
		}
	}
		
	public MagicGem removeGem(){
		
		MagicGem g = null;
		try {
			g = (MagicGem) gem.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (gem != null) {
			
			//g = new MagicGem(gem.getType());
			
			if(gem.getType() == Type.DAMAGE_INCREASER){
				damage = simpleDamage;
			}
			else if (gem.getType() == Type.SHOOTING_INCREASER){
				damage = simpleDamage;
			}else if (gem.getType() == Type.RANGE_EXPANDER){
				radius--;
			}
		}
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
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public MagicGem getGem(){
		return gem;
	}
}
