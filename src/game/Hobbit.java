package game;

/**
 * 
 * Az Enemy oszt�lyb�l lesz�rmaz� oszt�ly, amely a Hobbit faj� ellenfeleket reprezent�lja.
 *
 */
public class Hobbit extends Enemy {
	
	public Hobbit() {
		health = 50;
		trappedValue = 0;
		
		enemyView = new HobbitView(this);
	}
	
	/**
	 * 
	 * A hobbitnak kiv�teles �gyess�ge miatt nem �llj�k �tj�t az akad�lyok, ez�rt a mozg�s f�ggv�nyt fel�ldefini�ljuk.
	 *
	 */
	@Override
	public boolean move() {
		if (!currentRoad.isFinal()) {
			currentRoad.removeEnemy(this);
			currentRoad = currentRoad.getNext(Map.RIGHT);
			currentRoad.addEnemy(this);		//egyszer�en tov�bbl�p, nem �rdeklik az akad�lyok
		}
		
		return currentRoad.isFinal();
	}
	
	/**
	 * 
	 * A duplik�l� f�ggv�ny, amit a tornyok speci�lis l�ved�kei okoznak.
	 * Felezi az �let�t, �s egy szint�n felezett erej� hobbitot hoz m�g l�tre, amit visszaad visszat�r�si �rt�kk�nt.
	 *
	 */
	@Override
	public Hobbit duplicate() {
		Hobbit hobbit = new Hobbit();
		int newhealth = health / 2;
		if (newhealth > 0)	//a hobbit a felez�l�ved�kt�l nem hal meg, 1 �leter�pontja mindenk�pp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az �j hobbit sem fog halva sz�letni
			hobbit.setHealth(newhealth);
		else
			hobbit.setHealth(1);
		hobbit.setCurrentRoad(currentRoad);		//az �j hobbit az aktu�lis �tra ker�l
		currentRoad.addEnemy(hobbit);
		return hobbit;
	}
	
}
