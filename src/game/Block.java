package game;

/**
 * 
 * Az osztály a pálya elemi építõegysége. Ebbõl származik le a többi pályaelem, az út és a torony.
 * Önmagában az építhetõ területet jelöli.
 *
 */
public class Block {
	
	public static int b_id = 1;		//ez a változó tartja számon a már kiosztott azonosítók számát
	public int block_id;		//a blokk egyéni azonosítója
	
	public Block() {
		block_id = b_id;
		b_id++;
	}
	
	public int getBlockID() {
		return block_id;
	}
	
	public boolean isRoad(){
		return false;
	}
	
	public boolean isTower(){
		return false;
	}
	
}