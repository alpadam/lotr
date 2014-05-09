package game;

import java.awt.Graphics;

/**
 * 
 * Az Enemy osztályból leszármazó osztály, amely a Dwarf (Törpe) fajú ellenfeleket reprezentálja.
 *
 */
public class Dwarf extends Enemy {
	
	public Dwarf() {
		health = 60;		//a törpék szívósabbak, mint a többi faj, több élettel kezdenek
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		enemyView = new DwarfView(this);
		
		System.out.println("A Dwarf#"+ enemy_id + " ellenség létrejött!");
	}
	
	/**
	 * 
	 * A duplikáló függvény, amit a tornyok speciális lövedékei okoznak.
	 * Felezi az életét, és egy szintén felezett erejû törpöt hoz még létre, amit visszaad visszatérési értékként.
	 *
	 */
	@Override
	public Dwarf duplicate() {
		Dwarf dwarf = new Dwarf();
		int newhealth = health / 2;
		if (newhealth > 0)	//a törpe a felezõlövedéktõl nem hal meg, 1 életerõpontja mindenképp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az új törpe sem fog halva születni
			dwarf.setHealth(newhealth);
		else
			dwarf.setHealth(1);
		dwarf.setHealth(newhealth);
		dwarf.setCurrentRoad(currentRoad);		//az új törpe az aktuális útra kerül
		currentRoad.addEnemy(dwarf);
		return dwarf;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Dwarf.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}