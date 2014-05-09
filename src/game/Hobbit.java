package game;

import java.awt.Graphics;

/**
 * 
 * Az Enemy osztályból leszármazó osztály, amely a Hobbit fajú ellenfeleket reprezentálja.
 *
 */
public class Hobbit extends Enemy {
	
	public Hobbit() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		enemyView = new HobbitView(this);
		
		System.out.println("A Hobbit#"+ enemy_id + " ellenség létrejött!");
	}
	
	/**
	 * 
	 * A hobbitnak kivételes ügyessége miatt nem állják útját az akadályok, ezért a mozgás függvényt felüldefiniáljuk.
	 *
	 */
	@Override
	public boolean move() {
		
		currentRoad.removeEnemy(this);
		currentRoad = currentRoad.getNext(Map.RIGHT);
		currentRoad.addEnemy(this);		//egyszerûen továbblép, nem érdeklik az akadályok
		
		System.out.println("Ellenség#" + enemy_id  + " lépett Road#" + currentRoad.road_id + "-ra/re " + 
				"Történt végsõ útra lépés: " + currentRoad.isFinal());
		
		return currentRoad.isFinal();
	}
	
	/**
	 * 
	 * A duplikáló függvény, amit a tornyok speciális lövedékei okoznak.
	 * Felezi az életét, és egy szintén felezett erejû hobbitot hoz még létre, amit visszaad visszatérési értékként.
	 *
	 */
	@Override
	public Hobbit duplicate() {
		Hobbit hobbit = new Hobbit();
		int newhealth = health / 2;
		if (newhealth > 0)	//a hobbit a felezõlövedéktõl nem hal meg, 1 életerõpontja mindenképp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az új hobbit sem fog halva születni
			hobbit.setHealth(newhealth);
		else
			hobbit.setHealth(1);
		hobbit.setCurrentRoad(currentRoad);		//az új hobbit az aktuális útra kerül
		currentRoad.addEnemy(hobbit);
		return hobbit;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellenség#"+ enemy_id + "\t Hobbit.class" + "\t" + "Életerõ:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}
	
}
