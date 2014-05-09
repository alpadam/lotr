package game;

/**
 * 
 * Az Enemy oszt�lyb�l lesz�rmaz� oszt�ly, amely a Dwarf (T�rpe) faj� ellenfeleket reprezent�lja.
 *
 */
public class Dwarf extends Enemy {
	
	public Dwarf() {
		health = 60;		//a t�rp�k sz�v�sabbak, mint a t�bbi faj, t�bb �lettel kezdenek
		trappedValue = 0;
		
		enemy_id = id;
		id++;
		
		enemyView = new DwarfView(this);
		
		System.out.println("A Dwarf#"+ enemy_id + " ellens�g l�trej�tt!");
	}
	
	/**
	 * 
	 * A duplik�l� f�ggv�ny, amit a tornyok speci�lis l�ved�kei okoznak.
	 * Felezi az �let�t, �s egy szint�n felezett erej� t�rp�t hoz m�g l�tre, amit visszaad visszat�r�si �rt�kk�nt.
	 *
	 */
	@Override
	public Dwarf duplicate() {
		Dwarf dwarf = new Dwarf();
		int newhealth = health / 2;
		if (newhealth > 0)	//a t�rpe a felez�l�ved�kt�l nem hal meg, 1 �leter�pontja mindenk�pp marad
			this.health = newhealth;
		else
			this.health = 1;
		if (newhealth > 0)	//az �j t�rpe sem fog halva sz�letni
			dwarf.setHealth(newhealth);
		else
			dwarf.setHealth(1);
		dwarf.setHealth(newhealth);
		dwarf.setCurrentRoad(currentRoad);		//az �j t�rpe az aktu�lis �tra ker�l
		currentRoad.addEnemy(dwarf);
		return dwarf;
	}
	
	@Override
	public String toString() {
		return "\t"+"Ellens�g#"+ enemy_id + "\t Dwarf.class" + "\t" + "�leter�:"+ health
				+"\t" + "Road:" + "Road#" + currentRoad.getRoadID();
	}

}