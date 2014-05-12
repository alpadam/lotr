package game;

/**
 * 
 * Az oszt�ly a p�lya elemi �p�t�egys�ge. Ebb�l sz�rmazik le a t�bbi p�lyaelem, az �t �s a torony.
 * �nmag�ban az �p�thet� ter�letet jel�li.
 *
 */
public class Block {
	
	public static int blockSize = 50;	//�ll�that� n�zet, ekkora n�gyzetet fed le a blokk
	
	protected int x;	//A n�gyzet bal f�ls� sark�nak x koordin�t�ja
	protected int y;	//A n�gyzet bal f�ls� sark�nak y koordin�t�ja
	
	public BlockView blockView;		//View architekt�ra r�sze, ez az oszt�ly felel a kirajzol�s�rt
	
	public Block() {

		blockView = new BlockView(this);
		
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