package game;

/**
 * 
 * Az Enemy oszt�lyb�l lesz�rmaz� oszt�ly, amely a Hum�n faj� ellenfeleket reprezent�lja.
 *
 */
public class Human extends Enemy {
	
	public Human() {
		health = 50;
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		enemyView = new HumanView(this);
		
		System.out.println("A Human#"+ enemy_id + " ellens�g l�trej�tt!");
	}
	
	/**
	 * 
	 * A Hum�n k�l�nleges k�pess�ge, hogy a tornyok mindig csak az alapvet� sebz�s�kkel hatnak r�, a var�zsk�vek sebz�ser�s�t� hat�sa nem �rv�nyes�l.
	 * Emiatt defini�ljuk fel�l az eredeti f�ggv�nyt.
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
	 * A duplik�l� f�ggv�ny, amit a tornyok speci�lis l�ved�kei okoznak.
	 * Felezi az �let�t, �s egy szint�n felezett erej� hum�nt hoz m�g l�tre, amit visszaad visszat�r�si �rt�kk�nt.
	 *
	 */
	@Override
	public Human duplicate() {
		Human human = new Human();
		int newhealth = health / 2;
		if (newhealth > 0)	//a hum�n a felez�l�ved�kt�l nem hal meg, 1 �leter�pontja mindenk�pp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az �j hum�n sem fog halva sz�letni
			human.setHealth(newhealth);
		else
			human.setHealth(1);
		human.setCurrentRoad(currentRoad);	//az �j hum�n az aktu�lis �tra ker�l
		currentRoad.addEnemy(human);	
		return human;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellens�g#"+ enemy_id + "\t Human.class" + "\t" + "�leter�:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}
