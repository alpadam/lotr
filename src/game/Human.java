package game;

/**
 * 
 * Az Enemy osztályból leszármazó osztály, amely a Humán fajú ellenfeleket reprezentálja.
 *
 */
public class Human extends Enemy {
	
	public Human() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		enemyView = new HumanView(this);
		
		System.out.println("A Human#"+ enemy_id + " ellenség létrejött!");
	}
	
	/**
	 * 
	 * A Humán különleges képessége, hogy a tornyok mindig csak az alapvetõ sebzésükkel hatnak rá, a varázskövek sebzéserõsítõ hatása nem érvényesül.
	 * Emiatt definiáljuk felül az eredeti függvényt.
	 *
	 */
	@Override
	public boolean damage(int damage) {
		health -= Tower.simpleDamage;
		if(health <= 0)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * A duplikáló függvény, amit a tornyok speciális lövedékei okoznak.
	 * Felezi az életét, és egy szintén felezett erejû humánt hoz még létre, amit visszaad visszatérési értékként.
	 *
	 */
	@Override
	public Human duplicate() {
		Human human = new Human();
		int newhealth = health / 2;
		if (newhealth > 0)	//a humán a felezõlövedéktõl nem hal meg, 1 életerõpontja mindenképp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az új humán sem fog halva születni
			human.setHealth(newhealth);
		else
			human.setHealth(1);
		human.setCurrentRoad(currentRoad);	//az új humán az aktuális útra kerül
		currentRoad.addEnemy(human);	
		return human;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Human.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}
