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
	
	public static int blockSize = 50;
	
	protected int x;
	protected int y;
	
	public BlockView view;
	
	public Block() {
		block_id = b_id;
		b_id++;
		
		view = new BlockView(this);
		
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

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
}