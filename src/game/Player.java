package game;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Az oszt�ly a j�t�kos karakter�t, Szarum�nt reprezent�lja, a megfelel� v�ltoz�ival, eszk�zt�r�val �s f�ggv�nyeivel.
 *
 */
public class Player {
	
	private List<MagicGem> inventory;	//a j�t�kos eszk�zt�ra, azaz a tulajdon�ban l�v� var�zsk�vek
	private int magic;		//a j�t�kos aktu�lis var�zsereje
	
	public Player() {
		this.magic = 100;							
		inventory = new ArrayList<MagicGem>();
		
	}
	
	/**
	 * 
	 * Egy k�vet a j�t�kos eszk�zt�r�ba rak.
	 *
	 */
	public void addGem(MagicGem magicGem) {
		inventory.add(magicGem);
	}
	
	/**
	 * 
	 * Adott t�pus� k�vet kivesz a j�t�kos eszk�zt�r�b�l, �s visszaadja.
	 *
	 */
	public MagicGem getGem(Type type) {
		for (MagicGem g : inventory) {
			if (g.getType() == type){
				inventory.remove(g);
				return g;
			}
				
		}
		return null;
	}
	
	/**
	 * 
	 * V�s�rl�sok ut�n adott �rt�kkel cs�kkenti a j�t�kos var�zserej�t. 
	 * A v�s�rl�f�ggv�nyek feladata annak vizsg�lata, hogy van-e el�g var�zsereje a j�t�kosnak, emiatt nem vizsg�lja, hogy lemegy-e nulla al�.
	 *
	 */
	public void substractMagic(int magic) {		
		this.magic -= magic;
	}
	
	/**
	 * 
	 * A j�t�kos �ltal �ppen meg�lt ellenfelekkel ar�nyosan n�veli a j�t�kos var�zserej�t.
	 *
	 */
	public void addMagic(int killedEnemies) {
		this.magic += killedEnemies * Controller.killedEnemyReward;
	}
	
	public List<MagicGem> getInventory() {
		return inventory;
	}
	
	public int getMagic() {							
		return magic;
	}
	
	public int getNumberOfDamageIncreasers(){
		int number=0;
		for (int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getType().equals(Type.DAMAGE_INCREASER))
				number++;
			
		}
		
		return number;
	}
}