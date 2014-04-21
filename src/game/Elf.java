package game;

/**
 * 
 * Az Enemy osztályból leszármazó osztály, amely az Elf fajú ellenfeleket reprezentálja.
 *
 */
public class Elf extends Enemy {
	
	public Elf() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		System.out.println("Az Elf#"+ enemy_id + " ellenség létrejött!");
	}
	
	/**
	 * 
	 * A duplikáló függvény, amit a tornyok speciális lövedékei okoznak.
	 * Felezi az életét, és egy szintén felezett erejû elfet hoz még létre, amit visszaad visszatérési értékként.
	 *
	 */
	@Override
	public Elf duplicate() {
		Elf elf = new Elf();
		int newhealth = health / 2;
		if (newhealth > 0)	//az elf a felezõlövedéktõl nem hal meg, 1 életerõpontja mindenképp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az új elf sem fog halva születni
			elf.setHealth(newhealth);
		else
			elf.setHealth(1);
		elf.setCurrentRoad(currentRoad);	//az új elf az aktuális útra kerül
		currentRoad.addEnemy(elf);
		return elf;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Elf.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}
}

