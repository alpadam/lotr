package game;

/**
 * 
 * Az osztály a pálya elemi építõegysége. Ebbõl származik le a többi pályaelem, az út és a torony.
 * Önmagában az építhetõ területet jelöli.
 *
 */
public class Block {
	
	public static int blockSize = 50;	//Állítható nézet, ekkora négyzetet fed le a blokk
	
	protected int x;	//A négyzet bal fölsõ sarkának x koordinátája
	protected int y;	//A négyzet bal fölsõ sarkának y koordinátája
	
	public BlockView blockView;		//View architektúra része, ez az osztály felel a kirajzolásért
	
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