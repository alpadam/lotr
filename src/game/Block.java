package game;

/**
 * 
 * Az oszt�ly a p�lya elemi �p�t�egys�ge. Ebb�l sz�rmazik le a t�bbi p�lyaelem, az �t �s a torony.
 * �nmag�ban az �p�thet� ter�letet jel�li.
 *
 */
public class Block {
	
	public static int b_id = 1;		//ez a v�ltoz� tartja sz�mon a m�r kiosztott azonos�t�k sz�m�t
	public int block_id;		//a blokk egy�ni azonos�t�ja
	
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