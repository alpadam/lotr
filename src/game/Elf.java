package game;

/**
 * 
 * Az Enemy oszt�lyb�l lesz�rmaz� oszt�ly, amely az Elf faj� ellenfeleket reprezent�lja.
 *
 */
public class Elf extends Enemy {
	
	public Elf() {
		health = 30;
		trappedValue = 0;
		
		enemyView = new ElfView(this);
	}
	
	/**
	 * 
	 * A duplik�l� f�ggv�ny, amit a tornyok speci�lis l�ved�kei okoznak.
	 * Felezi az �let�t, �s egy szint�n felezett erej� elfet hoz m�g l�tre, amit visszaad visszat�r�si �rt�kk�nt.
	 *
	 */
	@Override
	public Elf duplicate() {
		Elf elf = new Elf();
		int newhealth = health / 2;
		if (newhealth > 0)	//az elf a felez�l�ved�kt�l nem hal meg, 1 �leter�pontja mindenk�pp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az �j elf sem fog halva sz�letni
			elf.setHealth(newhealth);
		else
			elf.setHealth(1);
		elf.setCurrentRoad(currentRoad);	//az �j elf az aktu�lis �tra ker�l
		currentRoad.addEnemy(elf);
		return elf;
	}
	
}

