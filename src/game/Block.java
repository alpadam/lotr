package game;

public class Block {
	
	public static int id = 1;		// csak a szkeleton miatt
	public int block_id;
	
	public Block() {
		block_id += id;
		id++;
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